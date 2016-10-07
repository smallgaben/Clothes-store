package converter;

import model.domain.User;
import servlet.bean.RegisterFormBean;

public class UserConverter implements Converter<RegisterFormBean, User> {
    @Override
    public User convert(RegisterFormBean storage) {
        User user = new User();
        user.setUsername(storage.getUsername());
        user.setRealname(storage.getRealname());
        user.setSurname(storage.getSurname());
        user.setPhonenum(storage.getPhonenum());
        user.setEmail(storage.getEmail());
        user.setPassword(storage.getPassword());

        return user;
    }
}
