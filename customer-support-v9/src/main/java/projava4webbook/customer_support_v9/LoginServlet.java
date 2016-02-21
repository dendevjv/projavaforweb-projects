package projava4webbook.customer_support_v9;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "loginServle", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger log = LogManager.getLogger();
    private static final Map<String, String> userDatabase = new Hashtable<>();
    
    static {
        userDatabase.put("Nick", "miller");
        userDatabase.put("Jess", "teacher");
        userDatabase.put("Couch", "teacher");
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (req.getParameter("logout") != null) {
            log.info("User {} logged out.", session.getAttribute("username"));
            session.invalidate();
            resp.sendRedirect("login");
            return;
        } else if (session.getAttribute("username") != null) {
            resp.sendRedirect("tickets");
            return;
        }
        
        req.setAttribute("loginFailed", false);
        req.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("username") != null) {
            resp.sendRedirect("tickets");
            return;
        }
        
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username == null || password == null || !userDatabase.containsKey(username) || !password.equals(userDatabase.get(username))) {
            log.warn("Login failed for user {}.", username);
            req.setAttribute("loginFailed", true);
            req.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp").forward(req, resp);
        } else {
            log.info("User {} successfully logged in.", username);
            session.setAttribute("username", username);
            req.changeSessionId();
            resp.sendRedirect("tickets");
        }
    }
}
