package com.example.webflux.mdc.filter;

import com.example.webflux.mdc.utils.Constants;
import com.example.webflux.mdc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.UUID;

/**
 * @author 宋志宗 on 2023/4/13
 */
@Component
public class MdcFilter implements WebFilter, Ordered {
  private static final Logger log = LoggerFactory.getLogger(MdcFilter.class);

  @Nonnull
  @Override
  public Mono<Void> filter(@Nonnull ServerWebExchange exchange,
                           @Nonnull WebFilterChain chain) {
    long nanoTime = System.nanoTime();
    String traceId = UUID.randomUUID().toString().replace("-", "");
    Map<String, String> mdcContextMap = Map.of(Constants.TRACE_ID_HEADER, traceId);
    MDC.setContextMap(mdcContextMap);
    exchange.getResponse().getHeaders().set(Constants.TRACE_ID_HEADER, traceId);
    return chain.filter(exchange)
      .then(
        Mono.<Void>fromRunnable(() -> {
            MDC.setContextMap(mdcContextMap);
            long elapsed = System.nanoTime() - nanoTime;
            long micro = elapsed / 1000;
            double mills = micro / 1000D;
            Utils.checkMDC(traceId, "MDC filter");
            log.info("{} Request completed in {} ms", traceId, mills);
          })
          .contextWrite(Context.of(Constants.MDC_KEY, mdcContextMap))
      )
      .contextWrite(Context.of(Constants.MDC_KEY, mdcContextMap));
  }

  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE + 1;
  }
}
