package com.suke.RentalSystem.controller;

import com.suke.RentalSystem.entity.Result;
import com.suke.RentalSystem.entity.User;
import com.suke.RentalSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public Result login(){
        String name = "666";
        String password = "123";
        List<User> list = userService.searchUser(name,password);
        return new Result(true,"success",null);
    }

}
