package com.example.webflux.mdc.utils;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * @author 宋志宗 on 2023/4/13
 */
public class Utils {
  private static final Logger log = LoggerFactory.getLogger(Utils.class);

  /**
   * 验证MDC中的traceId与是否与预期值一致
   *
   * @param target 预期的traceId值
   * @param mark   标记调试位置
   */
  public static String checkMDC(@Nonnull String target, @Nonnull String mark) {
    String traceIe = MDC.get(Constants.TRACE_ID_HEADER);
    if (!target.equals(traceIe)) {
      log.error("{} MDC value not matching {} -> {}", mark, target, traceIe);
    }
    return traceIe;
  }

  @Nonnull
  public static Mono<Map<String, String>> getMDCContext() {
    return Mono.deferContextual(view -> {
      if (view.isEmpty() || !view.hasKey(Constants.MDC_KEY)) {
        return Mono.just(Map.of());
      }
      Object o = view.get(Constants.MDC_KEY);
      @SuppressWarnings("unchecked")
      Map<String, String> ctx = (Map<String, String>) o;
      return Mono.just(ctx);
    });
  }

  @NotNull
  public static Mono<String> deferContextual(@Nonnull String traceId) {
    return Mono.deferContextual(view -> {
      Utils.checkMDC(traceId, "deferContextual1");
      Object mdcMap = view.get(Constants.MDC_KEY);
      if (!(mdcMap instanceof Map<?, ?> ctx)) {
        String msg = "MDC value is not a map: " + mdcMap.getClass().getName();
        log.error(msg);
        return Mono.error(new RuntimeException(msg));
      }
      Object traceIdObj = ctx.get(Constants.TRACE_ID_HEADER);
      if (traceIdObj == null) {
        log.error("traceId value is null");
        return Mono.error(new RuntimeException("traceId value is null"));
      }
      String contextTraceId = traceIdObj.toString();
      Utils.checkMDC(contextTraceId, "deferContextual2");
      return Mono.just(traceId);
    });
  }
}
