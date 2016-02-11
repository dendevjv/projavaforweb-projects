package projava4webbook.addressbook;

import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "fooServlet", urlPatterns = "/foo")
public class FooServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("showTimes") != null) {
            req.setAttribute("minus2TimeZone", TimeZone.getTimeZone("GMT-2"));
            req.setAttribute("minus3TimeZone", TimeZone.getTimeZone("GMT-3"));
            req.setAttribute("plus2TimeZone", TimeZone.getTimeZone("GMT+2"));
            req.setAttribute("plus3TimeZone", TimeZone.getTimeZone("GMT+3"));
            req.setAttribute("today", new Date());
            req.getRequestDispatcher("/WEB-INF/jsp/view/times.jsp").forward(req, resp);
        } else {
            req.setAttribute("var1", "String without XML");
            req.setAttribute("var2", "String with <b>XML</b>");
            req.setAttribute("varScript", "<script>prompt(\"Can you see me? :)\");</script>");
            req.setAttribute("var3", "simpleWord");
            String[] days = {"Monday", "Tuesday", "Wednesday"};
            req.setAttribute("days", days);
            
            req.getRequestDispatcher("/WEB-INF/jsp/view/foo.jsp").forward(req, resp);
        }
    }
}
