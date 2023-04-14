package com.example.webflux.mdc.logging

import java.lang.reflect.Modifier

/**
 * @author 宋志宗 on 2022/11/17
 */
internal object KLoggerNameResolver {
  /** get class name for function by the package of the function */
  @Suppress("NOTHING_TO_INLINE")
  inline fun name(noinline func: () -> Unit): String {
    val name = func.javaClass.name
    val slicedName =
      when {
        name.contains("Kt$") -> name.substringBefore("Kt$")
        name.contains("$") -> name.substringBefore("$")
        else -> name
      }
    return slicedName
  }

  /** get class name for java class (that usually represents kotlin class) */
  fun <T : Any> name(forClass: Class<T>): String =
    unwrapCompanionClass(forClass).name

  /** unwrap companion class to enclosing class given a Java Class */
  private fun <T : Any> unwrapCompanionClass(clazz: Class<T>): Class<*> {
    return clazz.enclosingClass?.let { enclosingClass ->
      try {
        enclosingClass.declaredFields
          .find { field ->
            field.name == clazz.simpleName &&
              Modifier.isStatic(field.modifiers) &&
              field.type == clazz
          }
          ?.run { enclosingClass }
      } catch (se: SecurityException) {
        // The security manager isn't properly set up, so it won't be possible
        // to search for the target declared field.
        null
      }
    }
      ?: clazz
  }
}
