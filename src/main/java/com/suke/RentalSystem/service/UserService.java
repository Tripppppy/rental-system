package com.suke.RentalSystem.service;

import com.suke.RentalSystem.entity.User;

import java.util.List;

public interface UserService {
    public List<User> searchUser(String name,String password);


}
