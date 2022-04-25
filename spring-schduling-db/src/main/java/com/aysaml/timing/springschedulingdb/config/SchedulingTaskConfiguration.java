package com.aysaml.timing.springschedulingdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Schedule task pool configuration.
 *
 * @author aysaml
 * @date 2022/4/21
 */
@Configuration
public class SchedulingTaskConfiguration {

    @Bean("customTaskScheduler")
    public TaskScheduler taskScheduler () {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(100);
        // Turn on permission to remove or cancel tasks.
        taskScheduler.setRemoveOnCancelPolicy(true);
        taskScheduler.setThreadNamePrefix("SchedulingTaskThread-");
        return taskScheduler;
    }
}
