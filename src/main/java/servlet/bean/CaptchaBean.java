package servlet.bean;

import nl.captcha.Captcha;

import java.time.LocalDateTime;

public class CaptchaBean {
    public static final long TIMEOUT = 20;
    private String id;
    private Captcha value;
    private LocalDateTime expandDate;

    public CaptchaBean(String id, Captcha value) {
        this.id = id;
        this.value = value;
        this.expandDate = LocalDateTime.now().plusMinutes(TIMEOUT);
    }

    public String getId() {
        return id;
    }

    public Captcha getValue() {
        return value;
    }

    public LocalDateTime getExpandDate() {
        return expandDate;
    }
}
