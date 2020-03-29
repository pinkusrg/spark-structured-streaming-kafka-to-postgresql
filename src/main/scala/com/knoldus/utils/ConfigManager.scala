package com.knoldus.utils

import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.JavaConverters._
import scala.util.Try

class ConfigManager(configPath: Option[String] = None) {

  val root_config: Config = ConfigFactory.load()

  protected val config: Config = configPath match {
    case Some(path) => root_config.getConfig(path)
    case None => root_config
  }

  private def tryToOpt[T](f: => T): Option[T] = Try(f).toOption

  def getIntOpt(key: String): Option[Int] = tryToOpt(config.getInt(key))
  def getLongOpt(key: String): Option[Long] = tryToOpt(config.getLong(key))

  def getValue(key: String): String = config.getString(key)
  def getValueOpt(key: String): Option[String] = tryToOpt(getValue(key))

  def getBoolean(key: String): Boolean = config.getBoolean(key)
  def getBooleanOpt(key: String): Option[Boolean] = tryToOpt(getBoolean(key))

  def getList(key: String): List[String] = config.getStringList(key).asScala.toList
  def getListOpt(key: String): Option[List[String]] = tryToOpt(getList(key))

}

object ConfigManager{
  object PostgreSqlConfig extends ConfigManager(Some("technohub.postgresql"))
  object KafkaConfig extends ConfigManager(Some("technohub.kafka"))
  object SparkConfig extends ConfigManager(Some("technohub.spark"))
}
