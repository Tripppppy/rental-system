package com.suke.RentalSystem.service;
import com.suke.RentalSystem.bo.BallSearchParamBO;
import com.suke.RentalSystem.bo.OrderConfirmParamBO;
import com.suke.RentalSystem.model.Ball;
import com.suke.RentalSystem.core.Service;

import java.util.List;


public interface BallService extends Service<Ball> {
    List<Ball> search(BallSearchParamBO paramBO);

    List<Ball> findByIdList(List<Long> idList);

    List<Ball> findByOrderId(Long id);
}
