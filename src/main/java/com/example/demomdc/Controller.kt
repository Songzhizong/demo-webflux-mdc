package com.example.demomdc

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.context.Context
import java.time.Duration
import java.util.*

/**
 * @author 宋志宗 on 2023/4/13
 */
@RestController
class Controller {
  private val log = LoggerFactory.getLogger(Controller::class.java)


  /**
   * @return
   */
  @GetMapping("/hello")
  fun test(): Mono<String>? {
    val string = UUID.randomUUID().toString()
    MDC.put("trace-id", string)
    val name = Thread.currentThread().name

    return Mono.delay(Duration.ofMillis(200))
      .flatMap {
        mono {
          resetMdcContext()
          checkMdc(string, name, 0)
          delay(100);500
        }
      }
      .contextWrite(Context.of("MDC", mapOf("trace-id" to string)))
      .flatMap {
        checkMdc(string, name, 1)
        Flux.range(0, 3)
          .flatMap {
            mono {
              val delayMillis: Long = 50
              delay(delayMillis)
              resetMdcContext()
              checkMdc(string, name, 2)
              val list = Flux.range(0, 3).collectList().awaitSingle()
              list.map {
                async {
                  delay(10)
                  resetMdcContext()
                  checkMdc(string, name, 3)
                  100
                }
              }.forEach {
                resetMdcContext()
                checkMdc(string, name, 4)
                it.await()
              }
              delayMillis
            }
          }
          .collectList()
          .map { string }
      }
      .contextWrite(Context.of("MDC", mapOf("trace-id" to string)))
      .then(Mono.fromCallable {
        checkMdc(string, name, 5)
        string
      })
      .flatMap {
        checkMdc(string, name, 6)
        Mono.just(it)
          .flatMap { s ->
            mono {
              resetMdcContext()
              checkMdc(string, name, 7)
              s
            }
          }
          .flatMap {
            checkMdc(string, name, 8)
            Mono.just(it)
          }
      }
      .flatMap {
        checkMdc(string, name, 9)
        Mono.just(it)
      }
      .contextWrite(Context.of("MDC", mapOf("trace-id" to string)))
      .doFinally {
        MDC.clear()
      }
  }

  private fun checkMdc(`val`: String, threadName: String, l: Int) {
    val s = MDC.get("trace-id")
    if (`val` != s) {
      log.error("mdc value changed {} {} -> {}", l, `val`, s)
      return
    }
//    val name = Thread.currentThread().name
//    if (threadName != name) {
//      log.info("线程正常切换 {}", l)
//    }
  }

  suspend fun resetMdcContext() {
    val optional = Mono.deferContextual { cv ->
      val map = if (cv.isEmpty) {
        Optional.empty()
      } else {
        Optional.of(cv.get<Map<String, String>>("MDC"))
      }
      Mono.just(map)
    }.awaitSingle()
    if (optional.isPresent) {
      MDC.setContextMap(optional.get())
    }
  }
}