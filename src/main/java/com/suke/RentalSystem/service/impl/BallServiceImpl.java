package com.suke.RentalSystem.service.impl;


import com.suke.RentalSystem.entity.Ball;
import com.suke.RentalSystem.mapper.BallMapper;
import com.suke.RentalSystem.service.BallService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.List;

@Service
public class BallServiceImpl implements BallService {

  @Resource
  BallMapper ballMapper;


  public List<Ball> listBall(){
    return ballMapper.listBall();
  }

  public Ball searchBallById(Long id){
    return ballMapper.searchBallById(id);
  }

  public List<Ball> searchByType(String type){
    return ballMapper.searchByType(type);
  }

  public List<Ball> searchByBrand(String brand){
    return ballMapper.searchByBrand(brand);
  }

  public String showStock(Long id) {
    return ballMapper.showStock(id);
  }

  public String showStatus(Long id){
    return ballMapper.showStatus(id);
  }

  public Ball insertBall(Ball ball){
    return ballMapper.insertBall(ball);
  }

  public int deleteBall(Long id){
    return ballMapper.deleteBall(id);
  }

  public Ball updateBall(Ball ball){
    return ballMapper.updateBall(ball);
  }
}
