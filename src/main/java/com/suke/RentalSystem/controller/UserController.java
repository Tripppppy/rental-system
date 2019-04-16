package com.suke.RentalSystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suke.RentalSystem.core.Result;
import com.suke.RentalSystem.core.ResultGenerator;
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

  @PostMapping("/login")
  public Result login(@RequestBody User user) {
    List<User> list = userService.searchUser(user.getLoginName(), user.getPassword());
    if (list.isEmpty()) {
      return ResultGenerator.genFailResult("登录名或密码错误");
    } else {
      return ResultGenerator.genSuccessResult(list.get(0));
    }
  }


  //分页用户：
  @GetMapping("/listUser")
  public Result listUser(@RequestParam Integer page, @RequestParam Integer size) {
    PageHelper.startPage(page, size);
    List<User> list = userService.listUser();
    PageInfo pageInfo = new PageInfo(list);
    return ResultGenerator.genSuccessResult(pageInfo);
  }

  //单条查询
  @GetMapping("/{id}")
  public Result findById(@PathVariable Long id){
    User user = userService.findById(id);
    return ResultGenerator.genSuccessResult(user);
  }


  //添加用户：
  @PostMapping("/insertUser")
  public Result insertUer(@RequestBody User user) {
    User user1 = userService.findByLoginNameAndId(user.getLoginName(), user.getId());
    if (user1 != null) {
      return ResultGenerator.genFailResult("用户名重复");
    } else {
      userService.insertUser(user);
      return ResultGenerator.genSuccessResult();
    }
  }

  //删除用户：
  @GetMapping("/deleteUser/{id}")
  public Result deleteUser(@PathVariable  Long id) {
    userService.deleteUser(id);
    return ResultGenerator.genSuccessResult();
  }

  //更新用户：
  @PostMapping("/updateUser")
  public Result updateUser(@RequestBody User user) {
    User user1 = userService.findByLoginNameAndId(user.getLoginName(), user.getId());
    if (user1 != null) {
      return ResultGenerator.genFailResult("用户名重复");
    } else {
      userService.updateUser(user);
      return ResultGenerator.genSuccessResult();
    }
  }

}
