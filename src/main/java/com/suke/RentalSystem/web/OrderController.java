package com.suke.RentalSystem.web;

import com.suke.RentalSystem.bo.OrderConfirmParamBO;
import com.suke.RentalSystem.core.Constant;
import com.suke.RentalSystem.core.Result;
import com.suke.RentalSystem.core.ResultGenerator;
import com.suke.RentalSystem.model.Ball;
import com.suke.RentalSystem.model.Order;
import com.suke.RentalSystem.model.User;
import com.suke.RentalSystem.service.BallService;
import com.suke.RentalSystem.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suke.RentalSystem.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private UserService userService;
    @Resource
    private BallService ballService;

    @PostMapping
    public Result add(@Validated @RequestBody Order order) {
        orderService.save(order);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        orderService.deleteByPK(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@Validated @RequestBody Order order) {
        orderService.updateByPK(order);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Order order = orderService.findById(id);
        return ResultGenerator.genSuccessResult(order);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size, @RequestParam(required = false) String phoneNum) {
        List<User> userList = null;
        if (phoneNum != null) {
            userList = userService.findByPhoneNum(phoneNum);
        }
        PageHelper.startPage(page, size);
        List<Order> list = new ArrayList<>();
        if (userList != null) {
            list = orderService.findAllByCond(userList);
        } else {
            list = orderService.findAll();
        }
        list.forEach(item -> {
            item.setUser(userService.findById(item.getUserId()));
            List<Ball> ballList = ballService.findByOrderId(item.getId());
            item.setBalls(ballList);
        });
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/my/{id}")
    public Result myOrder(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size, @PathVariable Long id) {
        PageHelper.startPage(page, size);
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setId(id);
        userList.add(user);
        List<Order> list = orderService.findAllByCond(userList);
        list.forEach(item -> {
            item.setUser(userService.findById(item.getUserId()));
            List<Ball> ballList = ballService.findByOrderId(item.getId());
            item.setBalls(ballList);
        });
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/confirm/{id}")
    public Result orderConfirm(@RequestBody List<OrderConfirmParamBO> data,
                               @PathVariable Long id,
                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                               @RequestParam LocalDateTime returnDate,
                               @RequestParam Double predictCost,
                               @RequestParam(required = false) String phoneNum,
                               HttpServletRequest request) {
        List<Long> list = orderService.orderConfirm(data, id, returnDate, predictCost, phoneNum, request);
        return ResultGenerator.genSuccessResult(list);
    }

    @GetMapping("/return")
    public Result returnBall(@RequestParam Long id, @RequestParam Double cost) {
        orderService.returnBall(id, cost);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/rental/{id}")
    public Result rentalBall(@PathVariable Long id) {
        Order order = orderService.findById(id);
        order.setStatus(Constant.ORDER_STATUS_RENTALING);
        order.setOrderRentDate(LocalDateTime.now());
        orderService.updateByPK(order);
        return ResultGenerator.genSuccessResult();
    }
}
