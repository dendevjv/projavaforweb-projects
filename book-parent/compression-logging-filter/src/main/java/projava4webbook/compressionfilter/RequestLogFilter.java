package projava4webbook.compressionfilter;

import org.apache.commons.lang3.time.StopWatch;

import java.io.IOException;
import java.time.Instant;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestLogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Instant requestTime = Instant.now();
        StopWatch timer = new StopWatch();
        try {
            timer.start();
            chain.doFilter(request, response);
        } finally {
            timer.stop();
            HttpServletRequest in = (HttpServletRequest) request;
            HttpServletResponse out = (HttpServletResponse) response;
            String length = out.getHeader("Content-Lenght");
            if (length == null || length.isEmpty()) {
                length = "-";
            }
            System.out.println(in.getRemoteAddr() +
                    " - - [" + requestTime + "]" + 
                    " \"" + in.getMethod() + " " + in.getRequestURI() +
                    " " + in.getProtocol() + "\" " + out.getStatus() + " " + length + 
                    " " + timer);
        }
    }

    @Override
    public void destroy() {}

}
