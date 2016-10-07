package provider;


import servlet.bean.CaptchaBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Uses in-memory map for storing captcha bean
 */
public abstract class InmemoryCaptchaProvider implements CaptchaProvider {
    private Map<String, CaptchaBean> captchasMap = new ConcurrentHashMap<>();
    private CaptchaBean bean;

    public Map<String, CaptchaBean> getCaptchasMap() {
        return captchasMap;
    }

    public CaptchaBean getBean() {
        return bean;
    }

    @Override
    public void store(HttpServletRequest request, HttpServletResponse response, CaptchaBean bean) {
        getCaptchasMap().put(bean.getId(), bean);
        this.bean = bean;
    }

    @Override
    public void removeOldCaptcha() {
        LocalDateTime now = LocalDateTime.now();

        captchasMap.values().stream().filter(captcha -> captcha.getExpandDate().isAfter(now)).forEach(captcha -> captchasMap.remove(captcha.getId()));
    }
}
