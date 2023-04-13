package com.example.webflux.mdc.controller

import com.example.webflux.mdc.utils.Constants
import com.example.webflux.mdc.utils.Utils
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.reactor.mono
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

/**
 * @author 宋志宗 on 2023/4/13
 */
@RestController
@RequestMapping("/coroutine")
class CoroutineController {
  private val log = LoggerFactory.getLogger(CoroutineController::class.java)
  private val delay = 50L
  private val delayDuration = Duration.ofMillis(delay)

  @GetMapping
  suspend fun coroutine(): String {
    val traceId = MDC.get(Constants.TRACE_ID_HEADER)
    Utils.deferContextual(traceId).awaitSingleOrNull()
    delay(delay)
    checkAsync1(traceId)
    checkFlux(traceId)
    return traceId
  }

  private suspend fun checkAsync1(traceId: String) = coroutineScope {
    val list = Flux.range(1, 10).collectList().awaitSingle()
    list.map {
      async {
        checkMDC(traceId, "async-1-1")
        delay(delay)
        true
      }
    }.forEach { it.await() }
    mono { checkMDC(traceId, "async-1-2") }.awaitSingleOrNull()
  }

  private suspend fun checkFlux(traceId: String) {
    mono { checkMDC(traceId, "flux-1-1") }
      .flatMap {
        Utils.checkMDC(traceId, "flux-1-2")
        Flux.range(1, 10)
          .flatMap {
            Utils.checkMDC(traceId, "flux-1-3")
            Mono.delay(delayDuration)
              .doOnNext { Utils.checkMDC(traceId, "flux-1-4") }
          }
          .collectList()
          .map { traceId }
      }.awaitSingleOrNull()
  }

  suspend fun checkMDC(traceId: String, mark: String): String {
    val mdcContext = Utils.getMDCContext().awaitSingle()
    MDC.setContextMap(mdcContext)
    Utils.checkMDC(traceId, mark)
    log.info("checkMDC: $mark")
    return traceId
  }
}