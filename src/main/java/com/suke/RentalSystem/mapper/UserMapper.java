package com.suke.RentalSystem.mapper;

import com.suke.RentalSystem.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {
    List<User> searchUser(@Param("name") String name, @Param("password") String password);
}
