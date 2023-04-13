package com.example.webflux.mdc.schema.a;

import com.example.webflux.mdc.utils.Constants;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Operators;
import reactor.util.context.Context;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * @author 宋志宗 on 2023/4/13
 */
@Configuration
@ConditionalOnClass({MDC.class, CoreSubscriber.class, Context.class})
public class MdcContextLifterConfiguration implements SmartInitializingSingleton, Disposable {
  private static final Logger log = LoggerFactory.getLogger(MdcContextLifterConfiguration.class);
  private static final String MDC_CONTEXT_REACTOR_KEY = MdcContextLifterConfiguration.class.getName();

  @Override
  public void afterSingletonsInstantiated() {
    Hooks.onEachOperator(MDC_CONTEXT_REACTOR_KEY, Operators.lift((scannable, coreSubscriber) -> new MdcContextLifter<>(coreSubscriber)));
  }

  @Override
  public void dispose() {
    Hooks.resetOnEachOperator(MDC_CONTEXT_REACTOR_KEY);
  }

  public static class MdcContextLifter<T> implements CoreSubscriber<T> {
    private final CoreSubscriber<T> coreSubscriber;

    public MdcContextLifter(CoreSubscriber<T> coreSubscriber) {
      this.coreSubscriber = coreSubscriber;
    }

    @Override
    public void onSubscribe(@Nonnull Subscription subscription) {
      coreSubscriber.onSubscribe(subscription);
    }

    @Override
    public void onNext(T t) {
      try {
        Context context = coreSubscriber.currentContext();
        if (context.isEmpty() || !context.hasKey(Constants.MDC_KEY)) {
          MDC.clear();
          return;
        }

        Object mdcValue = context.get(Constants.MDC_KEY);
        if (!(mdcValue instanceof Map<?, ?> mdcContext)) {
          log.error("MDC value is not a map: {}", mdcValue.getClass());
          MDC.clear();
          return;
        }
        try {
          //noinspection unchecked
          Map<String, String> contextMap = (Map<String, String>) mdcContext;
          MDC.setContextMap(contextMap);
        } catch (Exception e) {
          log.error("Failed to set MDC context map: {}", mdcContext, e);
        }
      } finally {
        coreSubscriber.onNext(t);
      }
    }

    @Override
    public void onError(Throwable throwable) {
      coreSubscriber.onError(throwable);
    }

    @Override
    public void onComplete() {
      coreSubscriber.onComplete();
    }

    @Nonnull
    @Override
    public Context currentContext() {
      return coreSubscriber.currentContext();
    }
  }
}
