package com.knoldus.spark

import com.knoldus.services.{KafkaService, PostgresqlService}
import com.knoldus.utils.ConfigManager.SparkConfig
import com.knoldus.utils.Constants._
import com.knoldus.utils.Logging
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

trait Job extends Logging with KafkaService with PostgresqlService {

  val master: String = SparkConfig.getValueOpt("master").getOrElse { noConfigFound("master"); SPARK_MASTER }
  val appName: String = SparkConfig.getValueOpt("app.name").getOrElse { noConfigFound("app.name"); APP_NAME }
  val logLevel: String = SparkConfig.getValueOpt("log.level").getOrElse { noConfigFound("log.level"); ERROR_LOG_LEVEL }

  def main(args: Array[String]): Unit = {
    run(createSparkSession())
  }

  def createSparkSession(config: Map[String, String] = Map.empty): SparkSession = {
    val spark = SparkSession.builder()
      .config(new SparkConf().setAll(config))
      .appName(appName)
      .master(master)
      .getOrCreate()

    spark.sparkContext.setLogLevel(logLevel)
    spark
  }

  def run(spark: SparkSession): Unit
}
