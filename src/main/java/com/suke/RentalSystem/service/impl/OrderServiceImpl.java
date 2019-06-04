package com.suke.RentalSystem.service.impl;

import com.suke.RentalSystem.bo.OrderConfirmParamBO;
import com.suke.RentalSystem.core.Constant;
import com.suke.RentalSystem.core.ResultCode;
import com.suke.RentalSystem.core.ServiceException;
import com.suke.RentalSystem.dao.OrderMapper;
import com.suke.RentalSystem.model.Ball;
import com.suke.RentalSystem.model.Order;
import com.suke.RentalSystem.model.OrderBall;
import com.suke.RentalSystem.model.User;
import com.suke.RentalSystem.service.BallService;
import com.suke.RentalSystem.service.OrderBallService;
import com.suke.RentalSystem.service.OrderService;
import com.suke.RentalSystem.core.AbstractService;
import com.suke.RentalSystem.service.UserService;
import com.suke.RentalSystem.util.IpAddressUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class OrderServiceImpl extends AbstractService<Order> implements OrderService {
    @Resource
    private OrderMapper tblOrderMapper;
    @Resource
    private BallService ballService;
    @Resource
    private OrderBallService orderBallService;
    @Resource
    private UserService userService;

    @Override
    public List<Long> orderConfirm(List<OrderConfirmParamBO> data, Long id, LocalDateTime returnDate, Double predictCost, String phoneNum, HttpServletRequest request) {
        List<Long> idList = data.stream().map(OrderConfirmParamBO::getId).collect(Collectors.toList());
        List<Ball> list = ballService.findByIdList(idList);
        List<Long> stockIdList = new ArrayList<>();
        list.forEach(item -> {
            data.forEach(paramItem -> {
                if (item.getId().equals(paramItem.getId())) {
                    Integer stock = item.getStock();
                    int result = stock - paramItem.getCount();
                    if (result < 0) {
                        stockIdList.add(item.getId());
                    } else {
                        item.setStock(result);
                    }
                }
            });
        });
        if (stockIdList.isEmpty()) {
            if (phoneNum != null) {
                String ipAddress = IpAddressUtil.getIpAddress(request);
                User user = new User();
                user.setLoginName(ipAddress);
                user.setMobile(phoneNum);
                user.setName("游客");
                user.setImageUrl("http://img.kimen.xyz/psb.png");
                user.setType("visitor");
                userService.save(user);
                id = user.getId();
            }
            Order order = new Order();
            order.setUserId(id);
            order.setOrderDate(LocalDateTime.now());
            order.setOrderReturnDate(returnDate);
            order.setPredictCost(predictCost);
            order.setStatus(Constant.ORDER_STATUS_ORDERED);
            order.setCost(0d);
            save(order);
            list.forEach(item -> {
                ballService.updateByPK(item);
            });
            data.forEach(item -> {
                OrderBall orderBall = new OrderBall();
                orderBall.setOrderId(order.getId());
                orderBall.setBallId(item.getId());
                orderBall.setCount(item.getCount());
                orderBallService.save(orderBall);
            });
        }
        return stockIdList;
    }

    @Override
    public void returnBall(Long id, Double cost) {
        Order order = findById(id);
        if (order != null) {
            List<OrderBall> orderBallList = orderBallService.findByOrderId(id);
            orderBallList.forEach(item -> {
                Ball ball = ballService.findById(item.getBallId());
                ball.setStock(ball.getStock() + item.getCount());
                ballService.updateByPK(ball);
            });
            order.setCost(cost);
            order.setOrderRealReturnDate(LocalDateTime.now());
            order.setStatus(Constant.ORDER_STATUS_COMPLETE);
            updateByPK(order);
        } else {
            throw new ServiceException("订单不存在");
        }
    }

    @Override
    public List<Order> findAllByCond(List<User> userList) {
        return tblOrderMapper.findAllByCond(userList);
    }
}
