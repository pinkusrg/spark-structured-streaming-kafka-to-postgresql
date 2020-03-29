import Dependencies._

name := "spark-structured-streaming-kafka-to-postgresql"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies ++= compiledDependencies(
  sparSqlKafka,
  sparkCore,
  sparkSql,
  postgreSql,
  typeSafeConfig
)++ testDependencies(
  scalaTest
)