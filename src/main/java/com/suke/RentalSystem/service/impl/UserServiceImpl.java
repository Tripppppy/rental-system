package com.suke.RentalSystem.service.impl;

import com.suke.RentalSystem.entity.User;
import com.suke.RentalSystem.mapper.UserMapper;
import com.suke.RentalSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public List<User> searchUser(String name,String password){
        return userMapper.searchUser(name,password);
    }

}
