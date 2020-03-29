package com.knoldus.models

import org.apache.spark.sql.Encoders
import org.apache.spark.sql.types.StructType

case class Bands(id: Int,
                 name: String,
                 hometown: String,
                 year: String)

object Bands {
  val bandsSchema: StructType = Encoders.product[Bands].schema
}