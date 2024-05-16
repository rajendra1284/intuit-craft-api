package com.intuit.craft.config;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {

    @Value("${spring.async.corePoolSize:2}")
    private Integer corePoolSize;
    @Value("${spring.async.maxPoolSize:4}")
    private Integer maxPoolSize;
    @Value("${spring.async.queueCapacity:500}")
    private Integer queueCapacity;

    @Bean(name = "threadPoolTaskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("AuditLogs-");
        executor.initialize();
        return executor;
    }
}
