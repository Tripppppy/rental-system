package com.suke.RentalSystem.service.impl;

import com.suke.RentalSystem.bo.OrderConfirmParamBO;
import com.suke.RentalSystem.core.Constant;
import com.suke.RentalSystem.core.ResultCode;
import com.suke.RentalSystem.dao.OrderMapper;
import com.suke.RentalSystem.model.Ball;
import com.suke.RentalSystem.model.Order;
import com.suke.RentalSystem.model.OrderBall;
import com.suke.RentalSystem.service.BallService;
import com.suke.RentalSystem.service.OrderBallService;
import com.suke.RentalSystem.service.OrderService;
import com.suke.RentalSystem.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
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

    @Override
    public List<Long> orderConfirm(List<OrderConfirmParamBO> data, Long id, LocalDateTime returnDate, Double predictCost) {
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
            Order order = new Order();
            order.setUserId(id);
            order.setOrderRentDate(LocalDateTime.now());
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
}
