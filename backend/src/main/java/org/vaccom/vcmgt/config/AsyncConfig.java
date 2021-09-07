package org.vaccom.vcmgt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author TrinhNX
 * @since 8/30/2021
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * This executor will be used for {@link org.springframework.scheduling.annotation.Async} task
     *
     * @return
     */
    @Bean
    public TaskExecutor taskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(100);
        executor.setThreadNamePrefix("ExecutionAsync-");
        return executor;
    }
}
