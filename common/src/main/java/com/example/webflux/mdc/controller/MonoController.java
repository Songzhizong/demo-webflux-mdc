package com.example.webflux.mdc.controller;

import com.example.webflux.mdc.utils.Constants;
import com.example.webflux.mdc.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author 宋志宗 on 2023/4/13
 */
@RestController
@RequestMapping("/mono")
public class MonoController {
  private static final Logger log = LoggerFactory.getLogger(MonoController.class);
  private static final Duration delay = Duration.ofMillis(50);

  @NotNull
  private static Mono<String> checkFlux1(String traceId) {
    return Flux
      .range(1, 10)
      .flatMap(i -> {
        Utils.checkMDC(traceId, "flux-1-1");
        return Mono.delay(delay)
          .doOnNext(l -> Utils.checkMDC(traceId, "flux-1-2"));
      })
      .collectList()
      .map(l -> traceId);
  }

  @NotNull
  private static Mono<String> checkThen1(String traceId) {
    return Mono.fromCallable(() -> {
        Utils.checkMDC(traceId, "then-1-1");
        return traceId;
      })
      .delayElement(delay)
      .flatMap(s ->
        Mono.delay(delay)
          .flatMap(i -> {
            Utils.checkMDC(traceId, "then-1-2");
            return Mono.just(traceId);
          })
      )
      .flatMap(s ->
        Flux.range(1, 10)
          .doOnNext(i -> Utils.checkMDC(traceId, "then-1-3"))
          .collectList()
          .map(l -> {
            Utils.checkMDC(traceId, "then-1-4");
            return traceId;
          })
      );
  }

  @NotNull
  private static Mono<String> checkThen2(String traceId) {
    return Mono.<Boolean>create(s -> {
        Utils.checkMDC(traceId, "then-2-1");
        s.success(true);
      })
      .flatMap(b ->
        Flux.range(1, 10)
          .doOnNext(i -> Utils.checkMDC(traceId, "then-2-2"))
          .collectList()
          .map(l -> {
            Utils.checkMDC(traceId, "then-2-3");
            return traceId;
          }))
      .thenReturn(traceId);
  }

  @GetMapping
  public Mono<String> mono() {
    String traceId = MDC.get(Constants.TRACE_ID_HEADER);
    // 验证MDC与ReactorContext中的值是否一致
    return Utils.deferContextual(traceId)
      .delayElement(delay)
      .flatMap(MonoController::checkFlux1)
      .then(checkThen1(traceId))
      .then(checkThen2(traceId))
      .then(Mono.just(traceId))
      ;
  }
}
