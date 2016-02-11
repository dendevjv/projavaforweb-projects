package projava4webbook.helloworld;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MultiValueParameterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String[] fruits = {"Apple", "Orange", "Grapes"};
    private static final String[] overseasFruits = {"Banana", "Guava", "Kiwi"};
    
    private boolean useLists = true;
    private boolean useOverseas = true; 
    
    @Override
    public void init() throws ServletException {
        ServletContext context = this.getServletContext();
        
        useLists = Boolean.parseBoolean(context.getInitParameter("useLists"));
        useOverseas = Boolean.parseBoolean(getInitParameter("useOverseasFruits"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.append("<!DOCTYPE html>\r\n")
              .append("<html>\r\n")
              .append("    <head>\r\n")
              .append("        <title>Hello User Application</title>\r\n")
              .append("    </head>\r\n")
              .append("    <body>\r\n")
              .append("        <form action=\"checkboxes\" method=\"POST\">\r\n")
              .append("Select the fruits you like to eat:<br/>\r\n");
        
        for (String fruit : fruits) {
            addCheckbox(writer, fruit);
        }
        
        if (useOverseas) {
            for (String fruit : overseasFruits) {
                addCheckbox(writer, fruit);
            }
        }
              
        writer.append("<input type=\"submit\" value=\"Submit\"/>\r\n")
              .append("        </form>")
              .append("    </body>\r\n")
              .append("</html>\r\n");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] fruits = request.getParameterValues("fruit");

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.append("<!DOCTYPE html>\r\n")
              .append("<html>\r\n")
              .append("    <head>\r\n")
              .append("        <title>Hello User Application</title>\r\n")
              .append("    </head>\r\n")
              .append("    <body>\r\n")
              .append("        <h2>Your Selections</h2>\r\n");

        if(fruits == null)
            writer.append("        You did not select any fruits.\r\n");
        else { 
            if (useLists) {
                writer.append("        <ul>\r\n");
            }
            for(String fruit : fruits) {
                if (useLists) {
                    writer.append("        <li>").append(fruit).append("</li>\r\n");
                } else {
                    writer.append("        <p>").append(fruit).append("</p>\r\n");
                }
            }
            if (useLists) {
                writer.append("        </ul>\r\n");
            }
        }

        writer.append("    </body>\r\n")
              .append("</html>\r\n");
    }

    private void addCheckbox(PrintWriter writer, String name) {
        writer.append("<input type=\"checkbox\" name=\"fruit\"")
                .append("\" value=\"").append(name).append("\"/>")
                .append(name).append("<br/>\r\n");
    }
}