package com.suke.RentalSystem.controller;


import com.suke.RentalSystem.core.Result;
import com.suke.RentalSystem.core.ResultGenerator;
import com.suke.RentalSystem.entity.Ball;
import com.suke.RentalSystem.entity.Order;
import com.suke.RentalSystem.entity.User;
import com.suke.RentalSystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

  @Autowired
  OrderService orderService;

  //显示所有订单
  @GetMapping("/orderList")
  public Result orderList(){
    List<Order> orderList = orderService.orderList();
    return ResultGenerator.genSuccessResult(orderList);
  }

  //查找单条订单信息
  @GetMapping("/order")
  public Result order(@RequestParam Long id){
    Order order = orderService.order(id);
    return ResultGenerator.genSuccessResult(order);
  }

  //显示订单用户
  @GetMapping("/showUser")
  public Result showUser(@RequestParam Long userId){
    User user1 = orderService.showUser(userId);
    return ResultGenerator.genSuccessResult(user1);
  }

  //显示订单球类
  @GetMapping("/showBall")
  public Result showBall(@RequestParam Long ballId){
    Ball ball = orderService.showBall(ballId);
    return ResultGenerator.genSuccessResult(ball);
  }

  //显示订单起租日期
  @GetMapping("/orderRentDate")
  public Result orderRentDate(@RequestParam Long id){
    LocalDateTime localDateTime = orderService.orderRentDate(id);
    return ResultGenerator.genSuccessResult(localDateTime);
  }

  //显示订单归还日期
  @GetMapping("/orderReturnDate")
  public Result orderReturnDate(@RequestParam Long id){
    LocalDateTime localDateTime = orderService.orderReturnDate(id);
    return ResultGenerator.genSuccessResult(localDateTime);
  }

  //显示订单费用
  @GetMapping("/cost")
  public Result cost(@RequestParam Long id){
    Double cost = orderService.cost(id);
    return ResultGenerator.genSuccessResult(cost);
  }

  //显示订单状态
  @GetMapping("/status")
  public Result status(@RequestParam Long id){
    String status = orderService.status(id);
    return ResultGenerator.genSuccessResult(status);
  }

  //插入订单
  @PostMapping("/insertOrder")
  public Result insertOrder(@RequestBody Order order){
    Order order1 = orderService.insertOrder(order);
    return ResultGenerator.genSuccessResult(order1);
  }

  //删除订单
  @GetMapping("/deleteOrder")
  public Result deleteOrder(@RequestParam Long id){
    int deleteOrder = orderService.deleteOrder(id);
    return ResultGenerator.genSuccessResult(deleteOrder);
  }

  //更新订单
  @PostMapping("/updateOrder")
  public Result updateOrder(@RequestBody Order order){
    Order order1 = orderService.updateOrder(order);
    return ResultGenerator.genSuccessResult(order1);
  }
}
