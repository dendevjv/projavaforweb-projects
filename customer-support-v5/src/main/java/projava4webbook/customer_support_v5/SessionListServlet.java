package projava4webbook.customer_support_v5;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "sessionListServlet", 
        urlPatterns = "/sessions")
public class SessionListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("username") == null) {
            resp.sendRedirect("login");
            return;
        }
        req.setAttribute("numberOfSessions", SessionRegistry.getNumberOfSessions());
        req.setAttribute("sessionList", SessionRegistry.getAllSessions());
        req.getRequestDispatcher("/WEB-INF/jsp/view/sessions.jsp").forward(req, resp);
    }
}
