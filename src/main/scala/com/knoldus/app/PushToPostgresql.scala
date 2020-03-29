package com.knoldus.app

import com.knoldus.models.Bands
import com.knoldus.spark.Job
import org.apache.spark.sql.{Dataset, SparkSession}

object PushToPostgresql extends Job {

  override def run(spark: SparkSession): Unit = {

    //reading from kafka
    val bandsDataset: Dataset[Bands] = readFromKafka(spark)

    //do something with the dataSet here

    //writing to db
    writeToPostgresql(bandsDataset)
  }
}
