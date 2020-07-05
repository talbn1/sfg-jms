package talbn1.springframework.sfgjms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author talbn on 7/5/2020
 **/
@Configuration
@EnableScheduling
@EnableAsync
public class TaskConfig {

    @Bean
    TaskExecutor taskExecutor (){
        return new SimpleAsyncTaskExecutor();
    }



}
