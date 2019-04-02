package com.suke.RentalSystem.service.impl;


import com.suke.RentalSystem.entity.Ball;
import com.suke.RentalSystem.entity.Order;
import com.suke.RentalSystem.entity.User;
import com.suke.RentalSystem.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

  @Resource
  OrderService orderService;


  @Override
  public List<Order> orderList() {
    return orderService.orderList();
  }

  @Override
  public Order order(Long id) {
    return orderService.order(id);
  }

  @Override
  public User showUser(Long userId) {
    return orderService.showUser(userId);
  }

  @Override
  public Ball showBall(Long ballId) {
    return orderService.showBall(ballId);
  }

  @Override
  public LocalDateTime orderRentDate(Long id) {
    return orderService.orderRentDate(id);
  }

  @Override
  public LocalDateTime orderReturnDate(Long id) {
    return orderService.orderReturnDate(id);
  }

  @Override
  public Double cost(Long id) {
    return orderService.cost(id);
  }

  @Override
  public String status(Long id) {
    return orderService.status(id);
  }

  @Override
  public Order insertOrder(Order order) {
    return orderService.insertOrder(order);
  }

  @Override
  public int deleteOrder(Long id) {
    return orderService.deleteOrder(id);
  }

  @Override
  public Order updateOrder(Order order) {
    return orderService.updateOrder(order);
  }
}
