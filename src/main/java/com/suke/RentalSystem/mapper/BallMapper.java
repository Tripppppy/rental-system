package com.suke.RentalSystem.mapper;

import com.suke.RentalSystem.entity.Ball;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BallMapper {

  //查询球类信息:
  List<Ball> listBall();
  Ball searchBallById(Long Id);
  List<Ball> searchByType(String type);
  List<Ball> searchByBrand(String brand);
  String showStock(Long id);
  String showStatus(Long id);

  //增加球类:
  Ball insertBall(Ball ball);

  //删除球类:
  int deleteBall(Long id);

  //更新球类:
  Ball updateBall(Ball ball);



}
