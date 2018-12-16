package pl.np.ehouse.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

}
