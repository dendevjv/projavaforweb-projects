package projava4webbook.customer_support_v7;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionIdListener {
    private SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println(date() + ": Session " + se.getSession().getId() + " created.");
        SessionRegistry.addSession(se.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println(date() + ": Session " + se.getSession().getId() + " destroyed.");
        SessionRegistry.removeSession(se.getSession());
    }

    @Override
    public void sessionIdChanged(HttpSessionEvent event, String oldSessionId) {
        System.out.println(date() + ": Session ID " + oldSessionId + " changed to " + event.getSession().getId() + ".");
        SessionRegistry.updateSessionId(event.getSession(), oldSessionId);
    }

    private String date() {
        return formatter.format(new Date());
    }
}
