package servlet.registration;

import converter.ConverterContainer;
import exception.converter.ConvertException;
import exception.service.ServiceException;
import model.domain.User;
import model.service.UserService;
import nl.captcha.Captcha;
import org.apache.log4j.Logger;
import provider.CaptchaProvider;
import servlet.bean.CaptchaBean;
import servlet.bean.RegisterFormBean;
import validation.EqualsValidator;
import validation.NotNullValidator;
import validation.PatternValidator;
import validation.ValidationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@WebServlet("/registerUser")
@MultipartConfig
public class RegistrationServlet extends HttpServlet {
    public static final Logger LOGGER = Logger.getLogger(RegistrationServlet.class);
    private UserService userService;
    private CaptchaProvider captchaProvider;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        userService = Objects.requireNonNull((UserService) context.getAttribute("userService"));
        captchaProvider = Objects.requireNonNull((CaptchaProvider) context.getAttribute("provider"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegisterFormBean bean;
        try {
            bean = ConverterContainer.convert(HttpServletRequest.class, RegisterFormBean.class, req);
        } catch (ConvertException e) {
            LOGGER.warn("Bean can't be converted: ", e);
            resp.sendRedirect("registerUser");
            return;
        }

        LOGGER.debug("Converted bean object: " + bean);

        String captchaAnswer = captchaProvider.get(req).getValue().getAnswer();
        Map<String, String> errors = validateFormBean(bean, captchaAnswer);
        Map<String, String> inputers = saveInputersFields(bean);

        HttpSession session = req.getSession();

        if (!errors.isEmpty()) {
            LOGGER.debug("Not valid fields occurred: " + errors);
            session.setAttribute("inputers", inputers);
            session.setAttribute("errors", errors);
            resp.sendRedirect("registerUser");
            return;
        }

        User user;
        try {
            user = ConverterContainer.convert(RegisterFormBean.class, User.class, bean);

            if (user != null) {
                user.setImagePath(executeImagePath(req));
            }

            LOGGER.debug("Converted registration bean to user: " + user);
        } catch (ConvertException | NullPointerException e) {
            LOGGER.warn("User bean can't be parsed: ", e);
            resp.sendRedirect("registerUser");
            return;
        }

        try {
            userService.createUser(user);
            LOGGER.debug("Created a new user: " + user);
            session.setAttribute("user", user);
            resp.sendRedirect("personal.jsp");

            session.removeAttribute("existing user");
            session.removeAttribute("errors");
            session.removeAttribute("existingError");
            session.removeAttribute("inputers");
        } catch (ServiceException e) {
            LOGGER.warn("Service hadn't create user: ", e);
            session.setAttribute("existingError", "User already exists");

            resp.sendRedirect("registerUser");
        }
    }

    private String executeImagePath(HttpServletRequest req) throws ServletException, ConvertException {
        try {

            Part part = req.getPart("image");

            Objects.requireNonNull(part, "Image is empty");

            Random random = new Random();

            String initPath = getServletContext().getAttribute("rootDir") + "/avatars/";
            String fileName = part.hashCode() + random.nextLong() + ".jpg";
            String fullPath = initPath + fileName;

            part.write(fullPath);

            return fileName;
        } catch (IOException e) {
            LOGGER.warn(e);
            throw new ConvertException(e);
        }
    }

    private Map<String, String> saveInputersFields(RegisterFormBean bean) {
        Map<String, String> fields = new HashMap<>();

        fields.put("username", bean.getUsername());
        fields.put("realname", bean.getRealname());
        fields.put("surname", bean.getSurname());
        fields.put("phonenum", bean.getPhonenum());
        fields.put("email", bean.getEmail());

        return fields;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CaptchaProvider captchaProvider = (CaptchaProvider) req.getServletContext().getAttribute("provider");

        CaptchaBean captchaBean = generateCaptcha();

        captchaProvider.store(req, resp, captchaBean);

        req.getRequestDispatcher("WEB-INF/register.jsp").forward(req, resp);
    }

    private CaptchaBean generateCaptcha() {
        Captcha captcha = new Captcha.Builder(200, 50).addText().build();
        String id = String.valueOf(captcha.getAnswer().hashCode());

        return new CaptchaBean(id, captcha);
    }

    private Map<String, String> validateFormBean(RegisterFormBean bean, String captchaAnswer) {
        ValidationContext validationContext = new ValidationContext();
        validationContext.add(new NotNullValidator(bean.getUsername(), "username"));
        validationContext.add(new PatternValidator(bean.getUsername(), "username", "\\w{5,}"));

        validationContext.add(new NotNullValidator(bean.getRealname(), "realname"));
        validationContext.add(new PatternValidator(bean.getRealname(), "realname", "\\w{5,}"));

        validationContext.add(new NotNullValidator(bean.getSurname(), "surname"));
        validationContext.add(new PatternValidator(bean.getSurname(), "surname", "\\w{5,}"));

        validationContext.add(new NotNullValidator(bean.getCaptcha(), "captchaInput"));
        validationContext.add(new EqualsValidator(bean.getCaptcha(), "captchaInput", captchaAnswer));

        validationContext.add(new NotNullValidator(bean.getPhonenum(), "phonenum"));
        validationContext.add(new PatternValidator(bean.getPhonenum(), "phonenum", "^\\d{3}\\-\\d{3}\\-\\d{4}$"));

        validationContext.add(new NotNullValidator(bean.getPassword(), "password"));
        validationContext.add(new PatternValidator(bean.getPassword(), "password", "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})"));

        validationContext.add(new NotNullValidator(bean.getConfirmPassword(), "confirmpassword"));
        validationContext.add(new EqualsValidator(bean.getConfirmPassword(), "confirmpassword", bean.getPassword()));

        validationContext.add(new NotNullValidator(bean.getEmail(), "email"));
        validationContext.add(new PatternValidator(bean.getEmail(), "email", "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b"));

        return validationContext.validate();
    }
}
