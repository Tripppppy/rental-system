package com.suke.RentalSystem.service.impl;

import com.suke.RentalSystem.dao.OrderMapper;
import com.suke.RentalSystem.model.Order;
import com.suke.RentalSystem.service.OrderService;
import com.suke.RentalSystem.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/04/22.
 */
@Service
@Transactional
public class OrderServiceImpl extends AbstractService<Order> implements OrderService {
    @Resource
    private OrderMapper tblOrderMapper;

}
