package pl.np.ehouse.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

/**
 * 
 * @author Bartek
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
public class EhouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(EhouseApplication.class, args);
	}
	
	@Bean
	public TaskExecutor threadTaskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}
}
