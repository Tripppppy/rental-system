package com.suke.RentalSystem.service.impl;

import com.suke.RentalSystem.model.User;
import com.suke.RentalSystem.dao.UserMapper;
import com.suke.RentalSystem.service.UserService;
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
    public List<User> listUser(){
        return userMapper.listUser();
    }
    public User updateUser(User user){
        return userMapper.updateUser(user);

    }
    public void deleteUser(long id){
        userMapper.deleteUser(id);
    }

    public User findById(long id){
      return userMapper.findById(id);
    }

  @Override
  public User findByLoginNameAndId(String loginName, Long id) {
    return userMapper.findByLoginNameAndId(loginName, id);
  }


}
