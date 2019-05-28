package com.suke.RentalSystem.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suke.RentalSystem.core.Result;
import com.suke.RentalSystem.core.ResultCode;
import com.suke.RentalSystem.core.ResultGenerator;
import com.suke.RentalSystem.dto.FileResultDTO;
import com.suke.RentalSystem.model.User;
import com.suke.RentalSystem.service.RoleService;
import com.suke.RentalSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;
  @Resource
  private RoleService roleService;

  @PostMapping("/login")
  public Result login(@RequestBody User user) {
    User item = new User();
    item.setLoginName(user.getLoginName());
    item.setPassword(user.getPassword());
    List<User> list = userService.find(item);
    if (list.isEmpty()) {
      return ResultGenerator.genFailResult("登录名或密码错误");
    } else {
      User user1 = list.get(0);
      user1.setRoles(roleService.findRolesByUserId(user1.getId()));
      return ResultGenerator.genSuccessResult(user1);
    }
  }


  /**
   * 新增用户
   * @param user
   * @return
   */
  @PostMapping
  public Result add(@Validated @RequestBody User user) {
    Boolean result = userService.saveUser(user);
    if (result) {
      return ResultGenerator.genSuccessResult();
    } else {
      return ResultGenerator.genFailResult(ResultCode.USER_EXIST, "用户已存在");
    }
  }

  /**
   * 删除用户
   * @param id
   * @return
   */
  @DeleteMapping("/deleteUser/{id}")
  public Result deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResultGenerator.genSuccessResult();
  }

  /**
   * 获取用户信息
   * @param id
   * @return
   */
  @GetMapping("/withRoles/{id}")
  public Result withRoles(@PathVariable Long id) {
    User user = userService.getUserIdentity(id);
    return ResultGenerator.genSuccessResult(user);
  }

  /**
   * 更新用户信息
   * @param user
   * @return
   */
  @PutMapping("/updateAdmin")
  public Result updateAdmin(@Validated @RequestBody User user) {
    userService.updateAdmin(user);
    return ResultGenerator.genSuccessResult();
  }

  /**
   * 获取用户列表并分页
   * @param page
   * @param size
   * @param keyword
   * @return
   */
  @GetMapping("/listUserPage")
  public Result listUserPage(@RequestParam(defaultValue = "0") Integer page,
                             @RequestParam(defaultValue = "0") Integer size,
                             @RequestParam(required = false) String keyword) {
    PageHelper.startPage(page, size);
    List<User> list = userService.listUserPage(keyword);
    PageInfo pageInfo = new PageInfo(list);
    return ResultGenerator.genSuccessResult(pageInfo);
  }

  /**
   * 更新密码
   * @param user
   * @return
   */
  @PutMapping("/password")
  public Result updatePassword(@RequestBody User user) {
    userService.updatePassword(user);
    return ResultGenerator.genSuccessResult();
  }
}
