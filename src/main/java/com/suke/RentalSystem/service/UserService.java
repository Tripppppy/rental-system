package com.suke.RentalSystem.service;

import com.suke.RentalSystem.model.User;

import java.util.List;

public interface   UserService {
    List<User> searchUser(String loginName,String password);
    User insertUser(User user);
    User updateUser(User user);
    void deleteUser(long id);
    List<User> listUser();
    User findById(long id);

  User findByLoginNameAndId(String loginName, Long id);
}
