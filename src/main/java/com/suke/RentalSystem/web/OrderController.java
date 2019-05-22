package com.suke.RentalSystem.web;

import com.suke.RentalSystem.bo.OrderConfirmParamBO;
import com.suke.RentalSystem.core.Result;
import com.suke.RentalSystem.core.ResultGenerator;
import com.suke.RentalSystem.model.Ball;
import com.suke.RentalSystem.model.Order;
import com.suke.RentalSystem.service.BallService;
import com.suke.RentalSystem.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suke.RentalSystem.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
* Created by CodeGenerator on 2019/04/22.
*/
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
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Order> list = orderService.findAll();
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
                               @RequestParam Double predictCost) {
        List<Long> list = orderService.orderConfirm(data, id, returnDate, predictCost);
        return ResultGenerator.genSuccessResult(list);
    }
}
