package com.suke.RentalSystem.controller;


import com.suke.RentalSystem.core.ResultGenerator;
import com.suke.RentalSystem.entity.Ball;
import com.suke.RentalSystem.core.Result;
import com.suke.RentalSystem.service.BallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ball")
public class BallController {

  @Autowired
  BallService ballService;

  //球类信息总览:
  @GetMapping("/listBall")
  public Result listBall(){
    List<Ball> list = ballService.listBall();
    return ResultGenerator.genSuccessResult(list);
  }

  //根据ID查找球类:
  @GetMapping("/searchBallById")
  public Result searchBallById(@RequestParam Long id){
    Ball ball = ballService.searchBallById(id);
    return ResultGenerator.genSuccessResult(ball);
  }

  //根据类型查找球类:
  @GetMapping("/searchByType")
  public Result searchByType(@RequestParam String type){
    List<Ball> list = ballService.searchByType(type);
    return ResultGenerator.genSuccessResult(list);
  }

  //根据球的品牌查找:
  @GetMapping("/searchByBrand")
  public Result searchByBrand(@RequestParam String brand){
    List<Ball> list = ballService.searchByBrand(brand);
    return ResultGenerator.genSuccessResult(list);
  }

  //显示库存:
  @GetMapping("/showStock")
  public Result showStock(@RequestParam Long id){
    String stock = ballService.showStock(id);
    return ResultGenerator.genSuccessResult(stock);
  }

  //增加一条球类信息:
  @PostMapping("/insertBall")
  public Result insertBall(@RequestBody Ball ball){
    ballService.insertBall(ball);
    return ResultGenerator.genSuccessResult();
  }

  //删除一条球类信息:
  @GetMapping("/deleteBall")
  public Result deleteBall(@RequestParam Long id){
    ballService.deleteBall(id);
    return ResultGenerator.genSuccessResult();
  }

  //更新一条数据:
  @PostMapping("/updateBall")
  public Result updateBall(@RequestBody Ball ball){
    ballService.updateBall(ball);
    return ResultGenerator.genSuccessResult();
  }







}
