package provider;


import servlet.bean.CaptchaBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface that works with storing and getting captchas if a different ways
 */
public interface CaptchaProvider {
    /**
     * Saving captcha in a storage
     *
     * @param request  contains captchas credentials
     * @param response used for setting captchas credentials to client
     * @param bean     captcha for storing
     */
    void store(HttpServletRequest request, HttpServletResponse response, CaptchaBean bean);

    /**
     * gets captchas from storage
     *
     * @param request contains captchas credentials
     * @return Captcha from storage
     */
    CaptchaBean get(HttpServletRequest request);

    void removeOldCaptcha();
}
