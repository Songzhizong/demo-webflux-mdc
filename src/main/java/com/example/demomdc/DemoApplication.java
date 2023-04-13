package com.example.demomdc;

import io.micrometer.context.ContextRegistry;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    Hooks.enableAutomaticContextPropagation();
    ContextRegistry.getInstance()
      .registerThreadLocalAccessor("MDC",
        MDC::getCopyOfContextMap,
        MDC::setContextMap,
        MDC::clear);
    SpringApplication.run(DemoApplication.class, args);
  }
}
