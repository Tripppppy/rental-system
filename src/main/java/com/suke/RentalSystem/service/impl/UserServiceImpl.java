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
    public List<User> listUser(){
        return userMapper.listUser();
    }
    public User updateUser(User user){
        return userMapper.updateUser(user);

    }
    public int deleteUser(long id){
        return userMapper.deleteUser(id);
    }

    public User findById(long id){
      return userMapper.findById(id);
    }

  @Override
  public User findByLoginNameAndId(String loginName, Long id) {
    return userMapper.findByLoginNameAndId(loginName, id);
  }


}
