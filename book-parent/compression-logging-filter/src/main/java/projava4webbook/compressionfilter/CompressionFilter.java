package projava4webbook.compressionfilter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CompressionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (((HttpServletRequest) request).getHeader("Accept-Encoding").contains("gzip")) {
            System.out.println("Encoding requested");
            ((HttpServletResponse) response).setHeader("Content-Encoding", "gzip");
            ResponseWrapper wrapper = new ResponseWrapper((HttpServletResponse) response);
            try {
                chain.doFilter(request, wrapper);
            } finally {
                try {
                    wrapper.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Encoding not requested");
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

    private static class ResponseWrapper extends HttpServletResponseWrapper {
        private GZIPServletOutputStream outputStream;
        private PrintWriter writer;

        public ResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public synchronized ServletOutputStream getOutputStream() throws IOException {
            if (writer != null) {
                throw new IllegalStateException("getWriter() already called.");
            }
            if (outputStream == null) {
                outputStream = new GZIPServletOutputStream(super.getOutputStream());
            }
            return outputStream;
        }

        @Override
        public synchronized PrintWriter getWriter() throws IOException {
            if (writer == null && outputStream != null) {
                throw new IllegalStateException("getOutputStream() already called.");
            }
            if (writer == null) {
                outputStream = new GZIPServletOutputStream(super.getOutputStream());
                writer = new PrintWriter(new OutputStreamWriter(outputStream, this.getCharacterEncoding()));
            }
            return writer;
        }

        @Override
        public void flushBuffer() throws IOException {
            if (writer != null) {
                writer.flush();
            } else if (outputStream != null) {
                outputStream.flush();
            }
            super.flushBuffer();
        }

        @Override
        public void setContentLength(int len) {
        }

        @Override
        public void setContentLengthLong(long len) {
        }

        @Override
        public void setHeader(String name, String value) {
            if (!"content-length".equalsIgnoreCase(name)) {
                super.setHeader(name, value);
            }
        }

        @Override
        public void addHeader(String name, String value) {
            if (!"content-length".equalsIgnoreCase(name)) {
                super.addHeader(name, value);
            }
        }

        @Override
        public void setIntHeader(String name, int value) {
            if (!"content-length".equalsIgnoreCase(name)) {
                super.setIntHeader(name, value);
            }
        }

        @Override
        public void addIntHeader(String name, int value) {
            if (!"content-length".equalsIgnoreCase(name)) {
                super.addIntHeader(name, value);
            }
        }

        public void finish() throws IOException {
            if (writer != null) {
                writer.close();
            } else if (outputStream != null) {
                outputStream.finish();
            }
        }
    } // end class ResponseWrapper

    private static class GZIPServletOutputStream extends ServletOutputStream {
        private final ServletOutputStream servletOutputStream;
        private final GZIPOutputStream gzipStream;

        public GZIPServletOutputStream(ServletOutputStream servletOutputStream) throws IOException {
            this.servletOutputStream = servletOutputStream;
            gzipStream = new GZIPOutputStream(servletOutputStream);
        }

        @Override
        public boolean isReady() {
            return this.servletOutputStream.isReady();
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            this.servletOutputStream.setWriteListener(writeListener);
        }

        @Override
        public void write(int b) throws IOException {
            gzipStream.write(b);
        }

        @Override
        public void close() throws IOException {
            gzipStream.close();
        }

        @Override
        public void flush() throws IOException {
            gzipStream.flush();
        }

        public void finish() throws IOException {
            gzipStream.finish();
        }
    } // end class GZIPServletOutputStream

} // end class CompressionFilter
