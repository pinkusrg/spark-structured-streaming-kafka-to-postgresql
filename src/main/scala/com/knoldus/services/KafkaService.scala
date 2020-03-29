package com.knoldus.services

import com.knoldus.models.Bands
import com.knoldus.models.Bands._
import com.knoldus.utils.ConfigManager.KafkaConfig
import com.knoldus.utils.Constants._
import com.knoldus.utils.Logging
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{Dataset, SparkSession}

trait KafkaService extends Logging{

  val brokers = KafkaConfig.getValue("broker")
  val sourceTopic = KafkaConfig.getValue("source.topic")
  val groupId = KafkaConfig.getValue("source.group.id")
  val fromBeginning = if (KafkaConfig.getBoolean("source.earliest")) "earliest" else "latest"

  /**
   * Reads dataframe from kafka as dataset of Bands
   * @param spark SparkSession
   * @return      Dataset of Bands
   */
  def readFromKafka(spark: SparkSession): Dataset[Bands] = {
    info(s"Reading from kafka with topic : ${sourceTopic}")
    import spark.implicits._
    spark
      .readStream
      .format(KAFKA_SOURCE)
      .options(kafkaSourceOptions)
      .load()
      .selectExpr("cast(value as string) as value")
      .select(from_json(col("value"), bandsSchema).as[Bands])
  }

  /**
   * Kafka consumer configuration properties
   * @return  Map of String -> String
   */
  def kafkaSourceOptions: Map[String, String] = Map(
    ("kafka.bootstrap.servers", brokers),
    ("group.id", groupId),
    ("startingOffsets", fromBeginning),
    ("subscribe", sourceTopic)
  )
}
