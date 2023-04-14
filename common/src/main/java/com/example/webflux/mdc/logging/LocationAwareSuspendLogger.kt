package com.example.webflux.mdc.logging

import com.example.webflux.mdc.utils.Constants
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.MDC
import org.slf4j.Marker
import org.slf4j.MarkerFactory
import org.slf4j.helpers.MessageFormatter
import org.slf4j.spi.LocationAwareLogger
import reactor.core.publisher.Mono

/**
 * @author 宋志宗 on 2023/4/12
 */
internal class LocationAwareSuspendLogger(override val underlyingLogger: LocationAwareLogger) :
  SuspendLogger {
  companion object {
    private val fqcn: String = LocationAwareSuspendLogger::class.java.name
    private val ENTRY = MarkerFactory.getMarker("ENTRY")
    private val EXIT = MarkerFactory.getMarker("EXIT")
    private val THROWING = MarkerFactory.getMarker("THROWING")
    private val CATCHING = MarkerFactory.getMarker("CATCHING")
    private const val EXIT_ONLY = "exit"
    private const val EXIT_MESSAGE = "exit with ({})"
  }

  override suspend fun <T : Any> withContext(block: () -> T): T {
    return mdcContext(block)!!
  }

  override suspend fun <T> nullableContext(block: () -> T?): T? {
    return mdcContext(block)
  }

  override fun isDebugEnabled(): Boolean {
    return underlyingLogger.isDebugEnabled
  }

  override suspend fun debug(msg: String?) {
    if (underlyingLogger.isDebugEnabled) mdcContext {
      underlyingLogger.log(
        null, fqcn, LocationAwareLogger.DEBUG_INT, msg, null, null
      )
    }
  }

  override suspend fun debug(format: String?, arg: Any?) {
    if (underlyingLogger.isDebugEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.DEBUG_INT, format, arrayOf(arg), null
      )
    }
  }

  override suspend fun debug(format: String?, arg1: Any?, arg2: Any?) {
    if (underlyingLogger.isDebugEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.DEBUG_INT, format, arrayOf(arg1, arg2), null
      )
    }
  }

  override suspend fun debug(format: String?, vararg arguments: Any?) {
    if (underlyingLogger.isDebugEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.DEBUG_INT, format, arguments, null
      )
    }
  }

  override suspend fun debug(msg: String?, t: Throwable) {
    if (underlyingLogger.isDebugEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.DEBUG_INT, msg, null, t
      )
    }
  }

  override suspend fun info(msg: String?) {
    if (underlyingLogger.isInfoEnabled) mdcContext {
      underlyingLogger.log(
        null, fqcn, LocationAwareLogger.INFO_INT, msg, null, null
      )
    }
  }

  override suspend fun info(format: String?, arg: Any?) {
    if (underlyingLogger.isInfoEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.INFO_INT, format, arrayOf(arg), null
      )
    }
  }

  override suspend fun info(format: String?, arg1: Any?, arg2: Any?) {
    if (underlyingLogger.isInfoEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.INFO_INT, format, arrayOf(arg1, arg2), null
      )
    }
  }

  override suspend fun info(format: String?, vararg arguments: Any?) {
    if (underlyingLogger.isInfoEnabled) mdcContext {
      underlyingLogger.log(
        null, fqcn, LocationAwareLogger.INFO_INT, format, arguments, null
      )
    }
  }

  override suspend fun info(msg: String?, t: Throwable) {
    if (underlyingLogger.isInfoEnabled) mdcContext {
      underlyingLogger.log(
        null, fqcn, LocationAwareLogger.INFO_INT, msg, null, t
      )
    }
  }

  override suspend fun warn(msg: String?) {
    if (underlyingLogger.isWarnEnabled) mdcContext {
      underlyingLogger.log(
        null, fqcn, LocationAwareLogger.WARN_INT, msg, null, null
      )
    }
  }

  override suspend fun warn(format: String?, arg: Any?) {
    if (underlyingLogger.isWarnEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.WARN_INT, format, arrayOf(arg), null
      )
    }
  }

  override suspend fun warn(format: String?, arg1: Any?, arg2: Any?) {
    if (underlyingLogger.isWarnEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.WARN_INT, format, arrayOf(arg1, arg2), null
      )
    }
  }

  override suspend fun warn(format: String?, vararg arguments: Any?) {
    if (underlyingLogger.isWarnEnabled) mdcContext {
      underlyingLogger.log(
        null, fqcn, LocationAwareLogger.WARN_INT, format, arguments, null
      )
    }

  }

  override suspend fun warn(msg: String?, t: Throwable) {
    if (underlyingLogger.isWarnEnabled) mdcContext {
      underlyingLogger.log(
        null, fqcn, LocationAwareLogger.WARN_INT, msg, null, t
      )
    }
  }

  override suspend fun error(msg: String?) {
    if (underlyingLogger.isErrorEnabled) mdcContext {
      underlyingLogger.log(
        null, fqcn, LocationAwareLogger.ERROR_INT, msg, null, null
      )
    }
  }

  override suspend fun error(format: String?, arg: Any?) {
    if (underlyingLogger.isErrorEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.ERROR_INT, format, arrayOf(arg), null
      )
    }
  }

  override suspend fun error(format: String?, arg1: Any?, arg2: Any?) {
    if (underlyingLogger.isErrorEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.ERROR_INT, format, arrayOf(arg1, arg2), null
      )
    }
  }

  override suspend fun error(format: String?, vararg arguments: Any?) {
    if (underlyingLogger.isErrorEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.ERROR_INT, format, arguments, null
      )
    }
  }

  override suspend fun error(msg: String?, t: Throwable) {
    if (underlyingLogger.isErrorEnabled) mdcContext {
      underlyingLogger.log(
        null, fqcn, LocationAwareLogger.ERROR_INT, msg, null, t
      )
    }
  }


  override suspend fun trace(msg: () -> Any?) {
    if (underlyingLogger.isTraceEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.TRACE_INT, msg.toStringSafe(), null, null
      )
    }
  }

  override suspend fun trace(t: Throwable?, msg: () -> Any?) {
    if (underlyingLogger.isTraceEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.TRACE_INT, msg.toStringSafe(), null, t
      )
    }
  }

  override suspend fun trace(marker: Marker?, msg: () -> Any?) {
    if (underlyingLogger.isTraceEnabled) mdcContext {
      underlyingLogger.log(
        marker,
        fqcn, LocationAwareLogger.TRACE_INT, msg.toStringSafe(), null, null
      )
    }
  }

  override suspend fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (underlyingLogger.isTraceEnabled) mdcContext {
      underlyingLogger.log(
        marker,
        fqcn, LocationAwareLogger.TRACE_INT, msg.toStringSafe(), null, t
      )
    }
  }

  override suspend fun debug(msg: () -> Any?) {
    if (underlyingLogger.isDebugEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.DEBUG_INT, msg.toStringSafe(), null, null
      )
    }
  }

  override suspend fun debug(t: Throwable?, msg: () -> Any?) {
    if (underlyingLogger.isDebugEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.DEBUG_INT, msg.toStringSafe(), null, t
      )
    }
  }

  override suspend fun debug(marker: Marker?, msg: () -> Any?) {
    if (underlyingLogger.isDebugEnabled) mdcContext {
      underlyingLogger.log(
        marker,
        fqcn, LocationAwareLogger.DEBUG_INT, msg.toStringSafe(), null, null
      )
    }
  }

  override suspend fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (underlyingLogger.isDebugEnabled) mdcContext {
      underlyingLogger.log(
        marker,
        fqcn, LocationAwareLogger.DEBUG_INT, msg.toStringSafe(), null, t
      )
    }
  }

  override suspend fun info(msg: () -> Any?) {
    if (underlyingLogger.isInfoEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.INFO_INT, msg.toStringSafe(), null, null
      )
    }
  }

  override suspend fun info(t: Throwable?, msg: () -> Any?) {
    if (underlyingLogger.isInfoEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.INFO_INT, msg.toStringSafe(), null, t
      )
    }
  }

  override suspend fun info(marker: Marker?, msg: () -> Any?) {
    if (underlyingLogger.isInfoEnabled) mdcContext {
      underlyingLogger.log(
        marker,
        fqcn, LocationAwareLogger.INFO_INT, msg.toStringSafe(), null, null
      )
    }
  }

  override suspend fun info(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (underlyingLogger.isInfoEnabled) mdcContext {
      underlyingLogger.log(
        marker,
        fqcn, LocationAwareLogger.INFO_INT, msg.toStringSafe(), null, t
      )
    }
  }

  override suspend fun warn(msg: () -> Any?) {
    if (underlyingLogger.isWarnEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.WARN_INT, msg.toStringSafe(), null, null
      )
    }
  }

  override suspend fun warn(t: Throwable?, msg: () -> Any?) {
    if (underlyingLogger.isWarnEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.WARN_INT, msg.toStringSafe(), null, t
      )
    }
  }

  override suspend fun warn(marker: Marker?, msg: () -> Any?) {
    if (underlyingLogger.isWarnEnabled) mdcContext {
      underlyingLogger.log(
        marker,
        fqcn, LocationAwareLogger.WARN_INT, msg.toStringSafe(), null, null
      )
    }
  }

  override suspend fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (underlyingLogger.isWarnEnabled) mdcContext {
      underlyingLogger.log(
        marker,
        fqcn, LocationAwareLogger.WARN_INT, msg.toStringSafe(), null, t
      )
    }
  }

  override suspend fun error(msg: () -> Any?) {
    if (underlyingLogger.isErrorEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.ERROR_INT, msg.toStringSafe(), null, null
      )
    }
  }

  override suspend fun error(t: Throwable?, msg: () -> Any?) {
    if (underlyingLogger.isErrorEnabled) mdcContext {
      underlyingLogger.log(
        null,
        fqcn, LocationAwareLogger.ERROR_INT, msg.toStringSafe(), null, t
      )
    }
  }

  override suspend fun error(marker: Marker?, msg: () -> Any?) {
    if (underlyingLogger.isErrorEnabled) mdcContext {
      underlyingLogger.log(
        marker,
        fqcn, LocationAwareLogger.ERROR_INT, msg.toStringSafe(), null, null
      )
    }
  }

  override suspend fun error(marker: Marker?, t: Throwable?, msg: () -> Any?) {
    if (underlyingLogger.isErrorEnabled) mdcContext {
      underlyingLogger.log(
        marker,
        fqcn, LocationAwareLogger.ERROR_INT, msg.toStringSafe(), null, t
      )
    }
  }

  override suspend fun entry(vararg argArray: Any?) {
    if (underlyingLogger.isTraceEnabled(ENTRY)) {
      val tp = MessageFormatter.arrayFormat(buildMessagePattern(argArray.size), argArray)
      mdcContext {
        underlyingLogger.log(
          ENTRY,
          fqcn, LocationAwareLogger.TRACE_INT, tp.message, null, null
        )
      }
    }
  }

  override suspend fun exit() {
    if (underlyingLogger.isTraceEnabled(EXIT)) {
      mdcContext {
        underlyingLogger.log(
          EXIT,
          fqcn, LocationAwareLogger.TRACE_INT,
          EXIT_ONLY, null, null
        )
      }
    }
  }

  override suspend fun <T> exit(result: T): T {
    if (underlyingLogger.isTraceEnabled(EXIT)) {
      val tp = MessageFormatter.format(EXIT_MESSAGE, result)
      mdcContext {
        underlyingLogger.log(
          EXIT,
          fqcn, LocationAwareLogger.TRACE_INT, tp.message, arrayOf<Any?>(result), tp.throwable
        )
      }
    }
    return result
  }

  override suspend fun <T : Throwable> throwing(throwable: T): T {
    mdcContext {
      underlyingLogger.log(
        THROWING,
        fqcn, LocationAwareLogger.ERROR_INT, "throwing", null, throwable
      )
    }
    throw throwable
  }

  override suspend fun <T : Throwable> catching(throwable: T) {
    if (underlyingLogger.isErrorEnabled) {
      mdcContext {
        underlyingLogger.log(
          CATCHING,
          fqcn, LocationAwareLogger.ERROR_INT, "catching", null, throwable
        )
      }
    }
  }

  private suspend fun <T> mdcContext(block: () -> T?): T? {
    val contextMap = Mono.deferContextual { view ->
      if (!view.hasKey(Constants.MDC_KEY)) {
        Mono.just(hashMapOf())
      } else {
        val map = view.get<Map<String, String>>(Constants.MDC_KEY)
        Mono.just(map)
      }
    }.awaitSingle()
    MDC.setContextMap(contextMap)
    return block.invoke()
  }

  private fun buildMessagePattern(len: Int): String {
    return (1..len).joinToString(separator = ", ", prefix = "entry with (", postfix = ")") { "{}" }
  }
}
