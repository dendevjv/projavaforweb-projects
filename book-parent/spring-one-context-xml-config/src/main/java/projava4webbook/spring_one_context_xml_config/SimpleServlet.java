package projava4webbook.spring_one_context_xml_config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "simpleServlet", urlPatterns = "/simple")
public class SimpleServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private static final Logger log = LogManager.getLogger();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("SimpeServlet doGet starts");
        resp.getWriter().println("SimpeServlet");
        log.info("SimpeServlet doGet exits");
    }

}
