package pl.np.ehouse.core;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import pl.np.ehouse.core.connection.SocketConnection;

/**
 * 
 * @author Bartek
 *
 */
@Service
public class EhouseThreads {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired()
	@Qualifier("threadTaskExecutor")
	TaskExecutor taskExecutor;

	@PostConstruct
	public void startThreads() {
		taskExecutor.execute(applicationContext.getBean(SocketConnection.class));
		taskExecutor.execute(applicationContext.getBean(MessageReader.class));
		taskExecutor.execute(applicationContext.getBean(MessageSender.class));
	}

}
