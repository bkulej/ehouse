package pl.np.ehouse.serial;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import pl.np.ehouse.serial.network.NetworkServer;

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
		ApplicationContext context = SpringApplication.run(EhouseSerialApplication.class, args);
		try {
			context.getBean(NetworkServer.class).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
