package com.suke.RentalSystem.dao;

import com.suke.RentalSystem.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> searchUser(@Param("name") String name, @Param("password") String password);

    List<User> listUser();

     User insertUser(User user);

     void deleteUser(long id);

     User updateUser(User user);

     User findById(long id);

  User findByLoginNameAndId(@Param("loginName") String loginName, @Param("id") Long id);
}
