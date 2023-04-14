package com.example.webflux.mdc.logging

import org.slf4j.LoggerFactory
import org.slf4j.spi.LocationAwareLogger

/**
 * @author 宋志宗 on 2023/4/14
 */
object KotlinLogging {

  fun suspendLogger(func: () -> Unit): SuspendLogger {
    val name = KLoggerNameResolver.name(func)
    val logger = LoggerFactory.getLogger(name)
    return if (logger is LocationAwareLogger) {
      LocationAwareSuspendLogger(logger)
    } else {
      throw UnsupportedOperationException("Unsupported Logger type: ${logger.javaClass.name}")
    }
  }

}