package com.example.webflux.mdc.schema.b;

import com.example.webflux.mdc.utils.Constants;
import io.micrometer.context.ContextRegistry;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication(scanBasePackages = "com.example.webflux.mdc")
public class SchemeBApplication {

  public static void main(String[] args) {
    Hooks.enableAutomaticContextPropagation();
    ContextRegistry.getInstance()
      .registerThreadLocalAccessor(Constants.MDC_KEY,
        MDC::getCopyOfContextMap,
        MDC::setContextMap,
        MDC::clear);
    SpringApplication.run(SchemeBApplication.class, args);
  }
}
