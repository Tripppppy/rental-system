package com.suke.RentalSystem.mapper;

import com.suke.RentalSystem.entity.Ball;
import com.suke.RentalSystem.entity.Order;
import com.suke.RentalSystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
  List<Order> orderList();
  Order order(Long id);
  User showUser(Long userId);
  Ball showBall(Long ballId);
  LocalDateTime orderRentDate(Long id);
  LocalDateTime orderReturnDate(Long id);
  Double cost(Long id);
  String status(Long id);

  Order insertOrder(Order order);
  int deleteOrder(Long id);
  Order updateOrder(Order order);


}
