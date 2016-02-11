package projava4webbook.helloworld;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "userServlet", 
    urlPatterns = "/userServlet",
    loadOnStartup = 1)
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_USER = "Guest";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("user");
        if (user == null) {
            user = DEFAULT_USER;
        }
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.append(" <!DOCTYPE html>\r\n" )
            .append(" <html>\r\n" )
            .append("  <head>\r\n" )
            .append("  <title>Hello User Application</title>\r\n" )
            .append("  </head>\r\n" )
            .append("  <body>\r\n" )
            .append("  Hello, " ).append(user).append(" !<br/><br/>\r\n" )
            .append("  <form action=\"userServlet\"  method=\"POST\" >\r\n" )
            .append("  Enter your name:<br/>\r\n" )
            .append("  <input type=\"text\"  name=\"user\" /><br/>\r\n" )
            .append("  <input type=\"submit\"  value=\"Submit\" />\r\n" )
            .append("  </form>\r\n" )
            .append("  </body>\r\n" )
            .append(" </html>\r\n" );
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
