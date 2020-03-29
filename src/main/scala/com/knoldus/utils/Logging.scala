package com.knoldus.utils

import com.knoldus.utils.ConfigManager.{PostgreSqlConfig}
import org.slf4j.{Logger, LoggerFactory}

trait Logging {
  self =>

  import Logging._

  protected val logger: Logger = LoggerFactory.getLogger(self.getClass)
  private val logLevels = List("debug", "info", "warn", "error")
  private val logLevel = PostgreSqlConfig.getValueOpt("log.level.app").getOrElse("info").toLowerCase.trim
  private val logLevelId = logLevels.indexOf(logLevel)

  protected def debug(message: String): Unit = log(DEBUG, logger.debug, message)

  protected def debug(message: String, exception: Throwable): Unit = logger.debug(message, exception)

  protected def info(message: String): Unit = log(INFO, logger.info, message)

  protected def info(message: String, exception: Throwable): Unit = logExc(INFO, logger.info, message, exception)

  protected def warn(message: String): Unit = log(WARN, logger.warn, message)

  protected def warn(message: String, exception: Throwable): Unit = logExc(WARN, logger.warn, message, exception)

  protected def error(message: String): Unit = log(ERROR, logger.error, message)

  private def log(level: Value, logFunc: String => Unit, message: String): Unit = if (level.id >= logLevelId) logFunc(message)

  protected def error(message: String, exception: Throwable): Unit = logExc(ERROR, logger.error, message, exception)

  private def logExc(level: Value, logFunc: (String, Throwable) => Unit, message: String, exp: Throwable): Unit =
    if (level.id >= logLevelId) logFunc(message, exp)

  protected def noConfigFound(configName: String): Unit =
    warn(s"no value found for configuration $configName, Setting default value for this property")
}

object Logging extends Enumeration {
  type LogLevel = Value
  val DEBUG, INFO, WARN, ERROR = Value
}
