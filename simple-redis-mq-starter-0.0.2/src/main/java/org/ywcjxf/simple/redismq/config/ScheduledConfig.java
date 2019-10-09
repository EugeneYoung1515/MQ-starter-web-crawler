package org.ywcjxf.simple.redismq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class ScheduledConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(setExecutor());
    }

    @Bean
    public Executor setExecutor(){
        return Executors.newScheduledThreadPool(3); // 这样不好 会影响原来的吧 几处与调度有管的配置都不太好
    }
}