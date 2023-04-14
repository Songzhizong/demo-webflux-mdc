package com.example.webflux.mdc.logging

import org.slf4j.Logger
import org.slf4j.Marker

/**
 * @author 宋志宗 on 2023/4/12
 */
interface SuspendLogger {

  /** The  logger executing logging */
  val underlyingLogger: Logger

  suspend fun <T : Any> withContext(block: () -> T): T

  suspend fun <T> nullableContext(block: () -> T?): T?

  /**
   * Is the logger instance enabled for the DEBUG level?
   *
   * @return True if this Logger is enabled for the DEBUG level,
   * false otherwise.
   */
  fun isDebugEnabled(): Boolean

  /**
   * Log a message at the DEBUG level.
   *
   * @param msg the message string to be logged
   */
  suspend fun debug(msg: String?)

  /**
   * Log a message at the DEBUG level according to the specified format
   * and argument.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the DEBUG level.
   *
   * @param format the format string
   * @param arg    the argument
   */
  suspend fun debug(format: String?, arg: Any?)

  /**
   * Log a message at the DEBUG level according to the specified format
   * and arguments.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the DEBUG level.
   *
   * @param format the format string
   * @param arg1   the first argument
   * @param arg2   the second argument
   */
  suspend fun debug(format: String?, arg1: Any?, arg2: Any?)

  /**
   * Log a message at the DEBUG level according to the specified format
   * and arguments.
   *
   * <p>This form avoids superfluous string concatenation when the logger
   * is disabled for the DEBUG level. However, this variant incurs the hidden
   * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
   * even if this logger is disabled for DEBUG. The variants taking
   * {@link #debug(String, Object) one} and {@link #debug(String, Object, Object) two}
   * arguments exist solely in order to avoid this hidden cost.
   *
   * @param format    the format string
   * @param arguments a list of 3 or more arguments
   */
  suspend fun debug(format: String?, vararg arguments: Any?)

  /**
   * Log an exception (throwable) at the DEBUG level with an
   * accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t   the exception (throwable) to log
   */
  suspend fun debug(msg: String?, t: Throwable)

  /**
   * Log a message at the INFO level.
   *
   * @param msg the message string to be logged
   */
  suspend fun info(msg: String?)

  /**
   * Log a message at the INFO level according to the specified format
   * and argument.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the INFO level.
   *
   * @param format the format string
   * @param arg    the argument
   */
  suspend fun info(format: String?, arg: Any?)

  /**
   * Log a message at the INFO level according to the specified format
   * and arguments.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the INFO level.
   *
   * @param format the format string
   * @param arg1   the first argument
   * @param arg2   the second argument
   */
  suspend fun info(format: String?, arg1: Any?, arg2: Any?)

  /**
   * Log a message at the INFO level according to the specified format
   * and arguments.
   *
   * <p>This form avoids superfluous string concatenation when the logger
   * is disabled for the INFO level. However, this variant incurs the hidden
   * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
   * even if this logger is disabled for INFO. The variants taking
   * {@link #info(String, Object) one} and {@link #info(String, Object, Object) two}
   * arguments exist solely in order to avoid this hidden cost.
   *
   * @param format    the format string
   * @param arguments a list of 3 or more arguments
   */
  suspend fun info(format: String?, vararg arguments: Any?)

  /**
   * Log an exception (throwable) at the INFO level with an
   * accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t   the exception (throwable) to log
   */
  suspend fun info(msg: String?, t: Throwable)

  /**
   * Log a message at the WARN level.
   *
   * @param msg the message string to be logged
   */
  suspend fun warn(msg: String?)

  /**
   * Log a message at the WARN level according to the specified format
   * and argument.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the WARN level.
   *
   * @param format the format string
   * @param arg    the argument
   */
  suspend fun warn(format: String?, arg: Any?)

  /**
   * Log a message at the WARN level according to the specified format
   * and arguments.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the WARN level.
   *
   * @param format the format string
   * @param arg1   the first argument
   * @param arg2   the second argument
   */
  suspend fun warn(format: String?, arg1: Any?, arg2: Any?)

  /**
   * Log a message at the WARN level according to the specified format
   * and arguments.
   *
   * <p>This form avoids superfluous string concatenation when the logger
   * is disabled for the WARN level. However, this variant incurs the hidden
   * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
   * even if this logger is disabled for WARN. The variants taking
   * {@link #warn(String, Object) one} and {@link #warn(String, Object, Object) two}
   * arguments exist solely in order to avoid this hidden cost.
   *
   * @param format    the format string
   * @param arguments a list of 3 or more arguments
   */
  suspend fun warn(format: String?, vararg arguments: Any?)

  /**
   * Log an exception (throwable) at the WARN level with an
   * accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t   the exception (throwable) to log
   */
  suspend fun warn(msg: String?, t: Throwable)

  /**
   * Log a message at the ERROR level.
   *
   * @param msg the message string to be logged
   */
  suspend fun error(msg: String?)

  /**
   * Log a message at the ERROR level according to the specified format
   * and argument.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the ERROR level.
   *
   * @param format the format string
   * @param arg    the argument
   */
  suspend fun error(format: String?, arg: Any?)

  /**
   * Log a message at the ERROR level according to the specified format
   * and arguments.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the ERROR level.
   *
   * @param format the format string
   * @param arg1   the first argument
   * @param arg2   the second argument
   */
  suspend fun error(format: String?, arg1: Any?, arg2: Any?)

  /**
   * Log a message at the ERROR level according to the specified format
   * and arguments.
   *
   * <p>This form avoids superfluous string concatenation when the logger
   * is disabled for the ERROR level. However, this variant incurs the hidden
   * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
   * even if this logger is disabled for ERROR. The variants taking
   * {@link #error(String, Object) one} and {@link #error(String, Object, Object) two}
   * arguments exist solely in order to avoid this hidden cost.
   *
   * @param format    the format string
   * @param arguments a list of 3 or more arguments
   */
  suspend fun error(format: String?, vararg arguments: Any?)

  /**
   * Log an exception (throwable) at the ERROR level with an
   * accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t   the exception (throwable) to log
   */
  suspend fun error(msg: String?, t: Throwable)

  /** Lazy add a log message if isTraceEnabled is true */
  suspend fun trace(msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isTraceEnabled is true */
  suspend fun trace(t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message if isTraceEnabled is true */
  suspend fun trace(marker: Marker?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isTraceEnabled is true */
  suspend fun trace(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message if isDebugEnabled is true */
  suspend fun debug(msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isDebugEnabled is true */
  suspend fun debug(t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message if isDebugEnabled is true */
  suspend fun debug(marker: Marker?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isDebugEnabled is true */
  suspend fun debug(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message if isInfoEnabled is true */
  suspend fun info(msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isInfoEnabled is true */
  suspend fun info(t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message if isInfoEnabled is true */
  suspend fun info(marker: Marker?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isInfoEnabled is true */
  suspend fun info(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message if isWarnEnabled is true */
  suspend fun warn(msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isWarnEnabled is true */
  suspend fun warn(t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message if isWarnEnabled is true */
  suspend fun warn(marker: Marker?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isWarnEnabled is true */
  suspend fun warn(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message if isErrorEnabled is true */
  suspend fun error(msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isErrorEnabled is true */
  suspend fun error(t: Throwable?, msg: () -> Any?)

  /** Lazy add a log message if isErrorEnabled is true */
  suspend fun error(marker: Marker?, msg: () -> Any?)

  /** Lazy add a log message with throwable payload if isErrorEnabled is true */
  suspend fun error(marker: Marker?, t: Throwable?, msg: () -> Any?)

  /** Add a log message with all the supplied parameters along with method name */
  suspend fun entry(vararg argArray: Any?)

  /** Add log message indicating exit of a method */
  suspend fun exit()

  /** Add a log message with the return value of a method */
  suspend fun <T> exit(result: T): T where T : Any?

  /** Add a log message indicating an exception will be thrown along with the stack trace. */
  suspend fun <T> throwing(throwable: T): T where T : Throwable

  /** Add a log message indicating an exception is caught along with the stack trace. */
  suspend fun <T> catching(throwable: T) where T : Throwable
}
