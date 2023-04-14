package com.example.webflux.mdc.logging

/**
 * @author 宋志宗 on 2022/11/18
 */
@Suppress("NOTHING_TO_INLINE")
internal inline fun (() -> Any?).toStringSafe(): String {
  return try {
    this.invoke().toString()
  } catch (e: Exception) {
    return "Log message invocation failed: $e"
  }
}
