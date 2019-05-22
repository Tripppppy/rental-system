package com.suke.RentalSystem.service.impl;

import com.suke.RentalSystem.dao.OrderBallMapper;
import com.suke.RentalSystem.model.OrderBall;
import com.suke.RentalSystem.service.OrderBallService;
import com.suke.RentalSystem.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/05/22.
 */
@Service
@Transactional
public class OrderBallServiceImpl extends AbstractService<OrderBall> implements OrderBallService {
    @Resource
    private OrderBallMapper tblOrderBallMapper;

}
