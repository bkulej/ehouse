package pl.np.ehouse.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

/**
 * @author Bartek
 */
@Configuration
class EhouseConfiguration {

    @Bean
    public TaskExecutor threadTaskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

}
