package com.suke.RentalSystem.service;

import com.suke.RentalSystem.core.Service;
import com.suke.RentalSystem.model.User;

import java.util.List;

public interface UserService extends Service<User> {

    User findByLoginName(String loginName);

    Boolean saveUser(User user);

    User getUserIdentity(Long userId);

    int updateAdmin(User user);

    List<User> listUserPage(String keyword);

    void deleteUser(Long userId);

    void updatePassword(User user);

    List<User> findByPhoneNum(String phoneNum);
}
