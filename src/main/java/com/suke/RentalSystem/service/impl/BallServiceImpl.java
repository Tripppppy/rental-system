package com.suke.RentalSystem.service.impl;

import com.suke.RentalSystem.dao.BallMapper;
import com.suke.RentalSystem.model.Ball;
import com.suke.RentalSystem.service.BallService;
import com.suke.RentalSystem.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/04/22.
 */
@Service
@Transactional
public class BallServiceImpl extends AbstractService<Ball> implements BallService {
    @Resource
    private BallMapper tblBallMapper;

}
