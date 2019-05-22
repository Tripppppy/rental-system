package com.suke.RentalSystem.dao;

import com.suke.RentalSystem.bo.BallSearchParamBO;
import com.suke.RentalSystem.core.Mapper;
import com.suke.RentalSystem.model.Ball;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BallMapper extends Mapper<Ball> {
    List<Ball> search(@Param("param") BallSearchParamBO paramBO);

    List<Ball> findByIdList(@Param("idList") List<Long> idList);
}