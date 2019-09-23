package pl.np.ehouse.core;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import pl.np.ehouse.core.databus.SocketConnection;
import pl.np.ehouse.core.message.MessageReaderImpl;
import pl.np.ehouse.core.message.MessageSenderImpl;

/**
 * @author Bartek
 */
@Service
class ThreadsRunner {

    private final ApplicationContext applicationContext;
    private final TaskExecutor taskExecutor;

    @Autowired
    public ThreadsRunner(ApplicationContext applicationContext,
            @Qualifier("threadTaskExecutor") TaskExecutor taskExecutor) {
        this.applicationContext = applicationContext;
        this.taskExecutor = taskExecutor;
    }

    @PostConstruct
    public void startThreads() {
        taskExecutor.execute(applicationContext.getBean(SocketConnection.class));
        taskExecutor.execute(applicationContext.getBean(MessageReaderImpl.class));
        taskExecutor.execute(applicationContext.getBean(MessageSenderImpl.class));
    }

}