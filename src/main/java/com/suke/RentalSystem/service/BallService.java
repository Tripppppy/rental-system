package com.suke.RentalSystem.service;

import com.suke.RentalSystem.entity.Ball;

import java.util.List;

public interface BallService {
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
