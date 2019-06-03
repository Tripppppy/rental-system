package com.suke.RentalSystem.dao;

import com.suke.RentalSystem.core.Mapper;
import com.suke.RentalSystem.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends Mapper<User> {

    List<User> listUserPage(@Param("keyword") String keyword);

    void updateImageUrl(@Param("userId") Long userId, @Param("imageUrl") String imageUrl);

    List<User> findByPhoneNum(@Param("phoneNum")String phoneNum);
}