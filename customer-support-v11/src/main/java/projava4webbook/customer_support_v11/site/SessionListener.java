package projava4webbook.customer_support_v11.site;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SessionListener implements HttpSessionListener, HttpSessionIdListener, ServletContextListener {
    private static final Logger log = LogManager.getLogger();
    
    @Inject
    SessionRegistryInterface sessionRegistry;
    
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.debug("Session " + se.getSession().getId() + " created.");
        sessionRegistry.addSession(se.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.debug("Session " + se.getSession().getId() + " destroyed.");
        sessionRegistry.removeSession(se.getSession());
    }

    @Override
    public void sessionIdChanged(HttpSessionEvent event, String oldSessionId) {
        log.debug("Session ID " + oldSessionId + " changed to " + event.getSession().getId() + ".");
        sessionRegistry.updateSessionId(event.getSession(), oldSessionId);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
        AutowireCapableBeanFactory factory = context.getAutowireCapableBeanFactory();
        factory.autowireBeanProperties(this, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
        factory.initializeBean(this, "sessionListener");
        log.info("Session listener initialized in Spring application context");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) { }
}
