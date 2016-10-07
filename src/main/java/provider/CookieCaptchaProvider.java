package provider;

import servlet.bean.CaptchaBean;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Storing captcha using cookie on client
 */
public class CookieCaptchaProvider extends InmemoryCaptchaProvider {
    @Override
    public void store(HttpServletRequest request, HttpServletResponse response, CaptchaBean bean) {
        super.store(request, response, bean);
        Cookie captchaCookie = new Cookie("captchaId", getBean().getId());
        response.addCookie(captchaCookie);
    }

    @Override
    public CaptchaBean get(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("captchaId")) {
                return getCaptchasMap().get(cookie.getValue());
            }
        }

        return null;
    }
}
