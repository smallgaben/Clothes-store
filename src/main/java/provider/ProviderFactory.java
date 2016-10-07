package provider;

import servlet.captcha.CaptchaType;

public class ProviderFactory {

    public static CaptchaProvider getInstance(CaptchaType type) {
        switch (type) {
            case SESSION:
                return new SessionCaptchaProvider();
            case COOKIE:
                return new CookieCaptchaProvider();
            case HIDDEN_FIELD:
                return new HiddenFieldCaptchaProvider();
            default:
                throw new IllegalArgumentException("There is no provider for your type");
        }
    }
}
