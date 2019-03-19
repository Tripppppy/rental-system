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


    //分页用户：
  @GetMapping("/listUser")
    public List<User> listUser(int page,int size){
        return userService.listUser(page,size);
    }

    //添加用户：
  @GetMapping("/insertUser")
    public User insertUer(@RequestBody User user){
        return  userService.insertUser(user);
    }

    //删除用户：
  @GetMapping("/deleteUser")
    public int deleteUser(int id){
        return  userService.deleteUser(id);
    }

    //更新用户：
  @GetMapping("/updateUser")
    public User updateUser(User user){
        return  userService.updateUser(user);
    }


}
