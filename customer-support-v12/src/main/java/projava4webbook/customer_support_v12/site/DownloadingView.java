package projava4webbook.customer_support_v12.site;

import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

public class DownloadingView implements View {
    private final String filename;
    private final String contentType;
    private final byte[] contents;

    public DownloadingView(String filename, String contentType, byte[] contents) {
        this.filename = filename;
        this.contentType = contentType;
        this.contents = contents;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setHeader("Content-Disposition",
                "attachment; filename=" + filename);
        response.setContentType("application/octet-stream");
        
        ServletOutputStream stream = response.getOutputStream();
        stream.write(contents);
    }

}
