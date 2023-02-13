package com.springboot2.config;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
public class ToDoMetricInterceptor implements HandlerInterceptor {
    private MeterRegistry meterRegistry;
    private String URI;
    private String METHOD;

    public ToDoMetricInterceptor(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception e) {
        URI = request.getRequestURI();
        METHOD = request.getMethod();
        if (!URI.contains("prometheus")) {
            log.info(" >> PATH: {}", URI);
            log.info(" >> METHOD: {}", METHOD);
            String pathKey = "api_".concat(METHOD.toLowerCase())
                    .concat(URI.replaceAll("/", "_").toLowerCase());
            this.meterRegistry.counter(pathKey).increment();
        }

    }

}
