package provider;

import org.apache.log4j.Logger;
import servlet.bean.CaptchaBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Storing captcha credentials with hidden field on client
 */
public class HiddenFieldCaptchaProvider extends InmemoryCaptchaProvider {
    public static final Logger LOGGER = Logger.getLogger(HiddenFieldCaptchaProvider.class);

    @Override
    public void store(HttpServletRequest request, HttpServletResponse response, CaptchaBean bean) {
        super.store(request, response, bean);
        request.setAttribute("hiddenField", true);
        request.setAttribute("captchaId", getBean().getId());
    }

    @Override
    public CaptchaBean get(HttpServletRequest request) {
        String id = request.getParameter("captchaId");
        return getCaptchasMap().get(id);
    }
}
