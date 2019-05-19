package pl.np.ehouse.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import pl.np.ehouse.core.connection.SocketConnection;

import javax.annotation.PostConstruct;

/**
 * @author Bartek
 */
@Service
class EhouseThreads {

    private final ApplicationContext applicationContext;
    private final TaskExecutor taskExecutor;

    @Autowired
    public EhouseThreads(ApplicationContext applicationContext, @Qualifier("threadTaskExecutor") TaskExecutor taskExecutor) {
        this.applicationContext = applicationContext;
        this.taskExecutor = taskExecutor;
    }

    @PostConstruct
    public void startThreads() {
        taskExecutor.execute(applicationContext.getBean(SocketConnection.class));
        taskExecutor.execute(applicationContext.getBean(MessageReader.class));
        taskExecutor.execute(applicationContext.getBean(MessageSender.class));
    }

}
