package com.suke.RentalSystem.service;
import com.suke.RentalSystem.model.OrderBall;
import com.suke.RentalSystem.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/05/22.
 */
public interface OrderBallService extends Service<OrderBall> {

    List<OrderBall> findByOrderId(Long id);
}
