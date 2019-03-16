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

    public List<User> searchUser(String loginName,String password){
        return  userMapper.searchUser(loginName, password);
    }

    public User insertUser(User user){
        return userMapper.insertUser(user);

    }
    public List<User> ListUser(){
        return userMapper.ListUser();
    }
    public User updateUser(User user){
        return userMapper.updateUser(user);

    }
    public int deleteUser(String loginName){
        return userMapper.deleteUser(loginName);
    }



//    @Override
//    public List<User> searchUser(String name,String password){
//        return userMapper.searchUser(name,password);
//    }
//
//    public User insertUser(User user){
//        userMapper.insertUser(user);
//        return user;
//    }
//
//    public List<User> ListUser(){
//        return userMapper.ListUser();
//    }
//
//    public int updateUser(User user){
//        return  userMapper.updateUser(user);
//    }
//
//    public int deleteUser(String loginName){
//        return userMapper.deleteUser(loginName);
//    }


}
