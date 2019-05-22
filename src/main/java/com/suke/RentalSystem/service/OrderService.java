package com.suke.RentalSystem.service;
import com.suke.RentalSystem.bo.OrderConfirmParamBO;
import com.suke.RentalSystem.model.Order;
import com.suke.RentalSystem.core.Service;

import java.time.LocalDateTime;
import java.util.List;


public interface OrderService extends Service<Order> {

    List<Long> orderConfirm(List<OrderConfirmParamBO> data, Long id, LocalDateTime returnDate, Double predictCost);
}
