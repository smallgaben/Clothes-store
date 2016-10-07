package converter;

import servlet.bean.RegisterFormBean;

import javax.servlet.http.HttpServletRequest;

public class FormBeanConverter implements Converter<HttpServletRequest, RegisterFormBean> {

    @Override
    public RegisterFormBean convert(HttpServletRequest storage) {
        RegisterFormBean bean = new RegisterFormBean();
        bean.setUsername(storage.getParameter("username"));
        bean.setRealname(storage.getParameter("realname"));
        bean.setSurname(storage.getParameter("surname"));
        bean.setPhonenum(storage.getParameter("phonenum"));
        bean.setEmail(storage.getParameter("email"));
        bean.setPassword(storage.getParameter("password"));
        bean.setConfirmPassword(storage.getParameter("confirmpassword"));
        bean.setCaptcha(storage.getParameter("captchaInput"));
        return bean;
    }
}
