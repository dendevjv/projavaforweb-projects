package projava4webbook.userprofile;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "profileServlet", 
        urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User(19384L, "Coder217", "Jack", "Sparrow");
        Hashtable<String, Boolean> permissions = new Hashtable<>();
        permissions.put("user", true);
        permissions.put("moderator", true);
        permissions.put("admin", true);
        user.setPermissions(permissions);
        
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/jsp/view/profile.jsp").forward(req, resp);
    }
}
