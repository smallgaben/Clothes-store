package servlet.captcha;

import nl.captcha.servlet.CaptchaServletUtil;
import provider.CaptchaProvider;
import servlet.bean.CaptchaBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/drawCaptcha")
public class CaptchaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CaptchaProvider captchaProvider = (CaptchaProvider) req.getServletContext().getAttribute("provider");

        CaptchaBean bean = captchaProvider.get(req);

        CaptchaServletUtil.writeImage(resp, bean.getValue().getImage());
    }
}
