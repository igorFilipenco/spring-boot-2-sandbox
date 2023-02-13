package com.springboot2.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.handler.MappedInterceptor;

public class ToDoConfig {
    @Bean
    public MappedInterceptor metricInterceptor(MeterRegistry registry) {
        return new MappedInterceptor(new String[]{"/**"}, new ToDoMetricInterceptor(registry));
    }
}
