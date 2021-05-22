package taass.shoepp.userservice.service;

import taass.shoepp.userservice.entity.User;
import taass.shoepp.userservice.model.UserForm;

public interface UserService {
    User getUser(Long id);

    void editUser(long id, UserForm userForm);

    void deleteUser(Long id);
}
