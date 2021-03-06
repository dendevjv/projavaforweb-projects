package projava4webbook.customer_support_v13.config;

import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
//@EnableAsync(proxyTargetClass = true)     // I did not find any @Async annotations in the project for now 
//@EnableScheduling     // I did not find any @Scheduled annotations in the project for now
@ComponentScan(
        basePackages = "projava4webbook.customer_support_v13.site", 
        excludeFilters = @ComponentScan.Filter(Controller.class) 
)
public class RootContextConfiguration 
        /*implements AsyncConfigurer, SchedulingConfigurer*/ {
    
    private static final Logger log = LogManager.getLogger();
    private static final Logger schedulingLogger =
            LogManager.getLogger(log.getName() + ".[scheduling]");
    
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setCacheSeconds(-1);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setBasenames("/WEB-INF/i18n/titles", "/WEB-INF/i18n/messages", "/WEB-INF/i18n/errors", "/WEB-INF/i18n/validation");
        return messageSource;
    }
    
    /**
     * Configures Spring Validator Bean.<br />
     * <br />
     * The <code>LocalValidatorFactoryBean</code> automatically detects the Bean Validation implementation on
     * the classpath, whether that’s Hibernate Validator or some other implementation, and uses its default
     * <code>javax.validation.ValidatorFactory</code> as the backing factory.<br />
     * <br />
     * Configures the <code>LocalValidatorFactoryBean</code> to use <code>MessageSource</code> bean. 
     */
    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(this.messageSource());
        return validator;
    }
    
    /**
     * Creates instance of <code>MethodValidationPostProcessor</code> to support validation
     * of method arguments and return values.<br />
     * Configures it to use the <code>LocalValidatorFactoryBean</code> created earlier.
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        processor.setValidator(this.localValidatorFactoryBean());
        return processor;
    }
    
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        return mapper;
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(new String[] { "projava4webbook.customer_support_v13.site" });
        return marshaller;
    }
    
    /* TaskScheduler is used in ChatEndpoint. */
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        log.info("Setting up thread pool task scheduler with 20 threads.");
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(20);
        scheduler.setThreadNamePrefix("task-");
        scheduler.setAwaitTerminationSeconds(60);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.setErrorHandler(t -> schedulingLogger.error(
                "Unknown error occurred while executing task.", t
        ));
        scheduler.setRejectedExecutionHandler(
                (r, e) -> schedulingLogger.error(
                        "Execution of task {} was rejected for unknown reasons.", r
                )
        );
        return scheduler;
    }

    /* I did not find any @Scheduled annotations in the project now, so commented configureTasks() out. */
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        TaskScheduler scheduler = this.taskScheduler();
//        log.info("Configuring scheduled method executor {}.", scheduler);
//        taskRegistrar.setTaskScheduler(scheduler);
//    }

    /* I did not find any @Async annotations in the project for now, so commented getAsyncExecutor() out. */
//    @Override
//    public Executor getAsyncExecutor() {
//        Executor executor = this.taskScheduler();
//        log.info("Configuring asynchronous method executor {}.", executor);
//        return executor;
//    }
    
    /* I did not find any @Async annotations in the project for now, so commented getAsyncUncaughtExceptionHandler() out. */
//    @Override
//    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//        AsyncUncaughtExceptionHandler handler = new AsyncUncaughtExceptionHandler() {
//            @Override
//            public void handleUncaughtException(Throwable ex, Method method, Object... params) {
//                schedulingLogger.error("AsyncUncaughtExceptionHandler: Error executing Method {} with Parameters ({}). Exception {}",
//                        method, Arrays.toString(params), ex );
//            }
//        };
//        return handler;
//    }
}
