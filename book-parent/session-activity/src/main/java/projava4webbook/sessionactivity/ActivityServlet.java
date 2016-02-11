package projava4webbook.sessionactivity;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(
        name = "activityServlet", 
        urlPatterns = "/do/*")
public class ActivityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        recordSessionActivity(req);
        viewSessionActivity(req, resp);
    }

    private void recordSessionActivity(HttpServletRequest req) {
        HttpSession session = req.getSession();
        
        if (session.getAttribute("visits") == null) {
            session.setAttribute("visits", new Vector<PageVisit>());
        }
        @SuppressWarnings("unchecked")
        Vector<PageVisit> visits = (Vector<PageVisit>) session.getAttribute("visits");
        if (!visits.isEmpty()) {
            PageVisit last = visits.lastElement();
            last.setLeftTimestamp(System.currentTimeMillis());
        }
        
        PageVisit now = new PageVisit();
        now.setEnteredTimestamp(System.currentTimeMillis());
        if (req.getQueryString() == null) {
            now.setRequest(req.getRequestURL().toString());
        } else {
            now.setRequest(req.getRequestURL() + "?" + req.getQueryString());
        }
        try {
            now.setIpAddress(InetAddress.getByName(req.getRemoteAddr()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        visits.add(now);
    }
    
    private void viewSessionActivity(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/view/viewSessionActivity1.jsp");
        requestDispatcher.forward(req, resp);
    }
}
