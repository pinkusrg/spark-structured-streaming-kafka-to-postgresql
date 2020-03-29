import sbt._

object Dependencies {

  def compiledDependencies(deps: ModuleID*): Seq[ModuleID] = deps.map(_ % Compile)

  def testDependencies(deps: ModuleID*): Seq[ModuleID] = deps.map(_ % Test)

  val postgresVersion = "42.2.2"
  val sparkVersion = "2.4.3"
  val  typeSafeConfigVersion = "1.3.4"

  val sparkCore =   "org.apache.spark" %% "spark-core" % sparkVersion
  val sparkSql = "org.apache.spark" %% "spark-sql" % sparkVersion
  val sparSqlKafka = "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion
  val postgreSql = "org.postgresql" % "postgresql" % postgresVersion
  val typeSafeConfig = "com.typesafe" % "config" % typeSafeConfigVersion

  //test dependencies
  val scalaTest = "org.scalatest" %% "scalatest" % "3.0.7"
}
