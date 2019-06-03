package com.suke.RentalSystem.dao;

import com.suke.RentalSystem.core.Mapper;
import com.suke.RentalSystem.model.Order;
import com.suke.RentalSystem.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends Mapper<Order> {
    List<Order> findAllByCond(@Param("userList") List<User> userList);
}