package com.knoldus.services

import com.knoldus.models.Bands
import com.knoldus.utils.ConfigManager.PostgreSqlConfig
import com.knoldus.utils.{Constants, Logging}
import org.apache.spark.sql.{Dataset, SaveMode}

trait PostgresqlService extends Logging {

  lazy val dataSource: String = Constants.POSTGRESQL_FORMAT

  /**
   * Writes dataset to postgresql
   *
   * @param dataset Dataset to write
   * @param mode    SaveMode, default is set as Append
   */
  def writeToPostgresql(dataset: Dataset[Bands], mode: SaveMode = SaveMode.Append) = {
    info("Writing dataset to postgresql.")
    dataset.writeStream
      .foreachBatch { (batch: Dataset[Bands], _: Long) =>
        batch.write
          .format(dataSource)
          .options(postgresqlSinkOptions)
          .mode(mode)
          .save()
      }
      .start()
      .awaitTermination()
  }

  /**
   * Spark-PostgreSQL connection properties.
   *
   * @return Map of String -> String
   */
  def postgresqlSinkOptions: Map[String, String] = Map(
    "dbtable" -> PostgreSqlConfig.getValue("dbtable"), // table
    "user" -> PostgreSqlConfig.getValue("user"), // Database username
    "password" -> PostgreSqlConfig.getValue("password"), // Password
    "driver" -> PostgreSqlConfig.getValue("driver"),
    "url" -> PostgreSqlConfig.getValue("url")
  )
}
