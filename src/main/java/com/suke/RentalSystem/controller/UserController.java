package com.suke.RentalSystem.controller;

import com.suke.RentalSystem.entity.Result;
import com.suke.RentalSystem.entity.User;
import com.suke.RentalSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
    public class UserController {

    @Autowired
    UserService userService;
    @GetMapping("/login")
    public Result login(){
        String loginName = "666";
        String password = "123";
        List<User> list = userService.searchUser(loginName,password);
        return new Result(true,"success",null);
    }


    //所有用户：
    public List<User> ListUser(){
        return userService.ListUser();
    }

    //添加用户：
    public User insertUer(User user){
        return  userService.insertUser(user);
    }

    //删除用户：
    public int deleteUser(String loginName){
        return  userService.deleteUser(loginName);
    }

    //更新用户：
    public User updateUser(User user){
        return  userService.updateUser(user);
    }







}
