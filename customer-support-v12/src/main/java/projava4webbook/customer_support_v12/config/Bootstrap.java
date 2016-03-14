package projava4webbook.customer_support_v12.config;

import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import projava4webbook.customer_support_v12.site.AuthenticationFilter;
import projava4webbook.customer_support_v12.site.LoggingFilter;
import projava4webbook.customer_support_v12.site.SessionListener;

/**
 * Starts Spring Framework, which is configured with two @Configuration classes, 
 * <code>RootContextConfiguration</code> and <code>ServletContextConfiguration</code>.
 */
public class Bootstrap implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        container.getServletRegistration("default").addMapping("/resource/*");
        
        /* Configuration of ApplicationContexts. */
        
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootContextConfiguration.class);
        container.addListener(new ContextLoaderListener(rootContext));
        container.addListener(SessionListener.class);
        
        AnnotationConfigWebApplicationContext servletContext = new AnnotationConfigWebApplicationContext();
        servletContext.register(ServletContextConfiguration.class);
        ServletRegistration.Dynamic dispatcher = container.addServlet("springDispatcher", new DispatcherServlet(servletContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.setMultipartConfig(new MultipartConfigElement(
                null, 20_971_520L, 41_943_040L, 512_000
        ));
        dispatcher.addMapping("/");
        
        /* Configuration of Filters. */
        
        FilterRegistration.Dynamic registration;
        
        registration = container.addFilter("loggingFilter", new LoggingFilter());
        registration.addMappingForUrlPatterns(null, false, "/*");
        
        registration = container.addFilter("authenticationFilter", new AuthenticationFilter());
        registration.addMappingForUrlPatterns(null, false, "/ticket", "/ticket/*", "/chat", "/chat/*", "/session", "/session/*");
    }

}
