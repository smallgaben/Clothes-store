package provider;

import org.apache.log4j.Logger;
import servlet.bean.CaptchaBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Uses session for storing captcha credentials
 */
public class SessionCaptchaProvider implements CaptchaProvider {
    public static final Logger LOGGER = Logger.getLogger(SessionCaptchaProvider.class);

    @Override
    public void store(HttpServletRequest request, HttpServletResponse response, CaptchaBean bean) {
        HttpSession session = request.getSession();
        session.setAttribute("captcha", bean);
    }

    @Override
    public CaptchaBean get(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (CaptchaBean) session.getAttribute("captcha");
    }

    @Override
    public void removeOldCaptcha() {
        LOGGER.info("Session can't remove old captchas");
    }
}
