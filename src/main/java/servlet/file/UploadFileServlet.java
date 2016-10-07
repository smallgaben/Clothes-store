package servlet.file;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/file/*")
public class UploadFileServlet extends HttpServlet {
    public static final Logger LOGGER = Logger.getLogger(UploadFileServlet.class);
    private String ROOT_DIR;

    @Override
    public void init() throws ServletException {
        ROOT_DIR = (String) getServletContext().getAttribute("rootDir");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filePath = ROOT_DIR + req.getPathInfo();
        try {

            File downloadFile = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(downloadFile);

            OutputStream outputStream = resp.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            fileInputStream.close();
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            LOGGER.warn("Troubles: ", e);
        }
    }
}
