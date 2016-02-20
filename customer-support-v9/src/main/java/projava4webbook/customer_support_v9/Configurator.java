package projava4webbook.customer_support_v9;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Configurator implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        FilterRegistration.Dynamic registration;

        registration = context.addFilter("loggingFilter", new LoggingFilter());
        registration.setAsyncSupported(true);
        registration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.INCLUDE,
                DispatcherType.FORWARD, DispatcherType.ERROR), false, "/*");

        registration = context.addFilter("authenticationFilter", new AuthenticationFilter());
        registration.setAsyncSupported(true);
        registration.addMappingForUrlPatterns(null, false, "/sessions", "/chat", "/tickets");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}

}
