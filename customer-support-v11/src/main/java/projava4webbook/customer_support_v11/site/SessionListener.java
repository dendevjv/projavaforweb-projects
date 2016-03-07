package projava4webbook.customer_support_v11.site;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionIdListener {
    private static final Logger log = LogManager.getLogger();
    
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.debug("Session " + se.getSession().getId() + " created.");
        SessionRegistry.addSession(se.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.debug("Session " + se.getSession().getId() + " destroyed.");
        SessionRegistry.removeSession(se.getSession());
    }

    @Override
    public void sessionIdChanged(HttpSessionEvent event, String oldSessionId) {
        log.debug("Session ID " + oldSessionId + " changed to " + event.getSession().getId() + ".");
        SessionRegistry.updateSessionId(event.getSession(), oldSessionId);
    }
}
