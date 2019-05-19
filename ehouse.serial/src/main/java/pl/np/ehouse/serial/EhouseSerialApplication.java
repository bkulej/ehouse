package pl.np.ehouse.serial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

/**
 * @author Bartek
 */
@SpringBootApplication
@EnableAsync
class EhouseSerialApplication {

    /**
     * @param args arguments of application
     */
    public static void main(String[] args) {
        SpringApplication.run(EhouseSerialApplication.class, args);
    }

    /**
     * @return tesk executor for @Async
     */
    @Bean
    public Executor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

}
