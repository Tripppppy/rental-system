package com.suke.RentalSystem.service.impl;

import com.suke.RentalSystem.bo.BallSearchParamBO;
import com.suke.RentalSystem.bo.OrderConfirmParamBO;
import com.suke.RentalSystem.dao.BallMapper;
import com.suke.RentalSystem.model.Ball;
import com.suke.RentalSystem.service.BallService;
import com.suke.RentalSystem.core.AbstractService;
import com.suke.RentalSystem.service.CodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class BallServiceImpl extends AbstractService<Ball> implements BallService {
    @Resource
    private BallMapper tblBallMapper;
    @Resource
    private CodeService codeService;

    @Override
    public Ball findById(Long id) {
        Ball ball = super.findById(id);
        ball.setBrandName(codeService.findByCode(ball.getBrand()).getName());
        ball.setTypeName(codeService.findByCode(ball.getType()).getName());
        return ball;
    }

    @Override
    public List<Ball> search(BallSearchParamBO paramBO) {
        return tblBallMapper.search(paramBO);
    }

    @Override
    public List<Ball> findByIdList(List<Long> idList) {
        return tblBallMapper.findByIdList(idList);
    }
}
