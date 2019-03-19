package com.suke.RentalSystem.service;

import com.suke.RentalSystem.entity.User;

import java.util.List;

public interface UserService {
    List<User> searchUser(String loginName,String password);
    User insertUser(User user);
    User updateUser(User user);
    int deleteUser(int id);
    List<User> listUser(int page,int size);
}
