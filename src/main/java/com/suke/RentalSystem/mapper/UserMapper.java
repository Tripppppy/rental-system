package com.suke.RentalSystem.mapper;

import com.suke.RentalSystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> searchUser(@Param("name") String name, @Param("password") String password);

    List<User> ListUser();

    public User insertUser(User user);

    public int deleteUser(String loginName);

    public User updateUser(User user);
}
