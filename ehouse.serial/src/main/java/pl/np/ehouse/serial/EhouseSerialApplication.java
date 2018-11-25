package pl.np.ehouse.serial;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 
 * @author Bartek
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableAsync
public class EhouseSerialApplication {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(EhouseSerialApplication.class, args);
	}

	/**
	 * 
	 * @return tesk executor for @Async
	 */
	@Bean
	public Executor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

}
