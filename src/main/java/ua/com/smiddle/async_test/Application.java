package ua.com.smiddle.async_test;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.concurrent.Executor;

/**
 * @author ksa on 16.06.17.
 * @project async_test
 */
@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@EnableAsync
@ComponentScan(basePackages = "ua.com.smiddle.async_test.core")
@PropertySource("classpath:application.properties")
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = new SpringApplicationBuilder(Application.class).headless(false).run(args);
    }


    @Bean(name = "SDProcessorThreadPool")
    public Executor processorThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(5);
        executor.setThreadNamePrefix("SDProcessorThread-");
        executor.initialize();
        return executor;
    }
}
