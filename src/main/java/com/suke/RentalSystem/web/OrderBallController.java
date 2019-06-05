package com.suke.RentalSystem.web;

import com.suke.RentalSystem.core.Result;
import com.suke.RentalSystem.core.ResultGenerator;
import com.suke.RentalSystem.model.OrderBall;
import com.suke.RentalSystem.service.OrderBallService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/order/ball")
public class OrderBallController {
    @Resource
    private OrderBallService orderBallService;

    @PostMapping
    public Result add(@Validated @RequestBody OrderBall orderBall) {
        orderBallService.save(orderBall);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        orderBallService.deleteByPK(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@Validated @RequestBody OrderBall orderBall) {
        orderBallService.updateByPK(orderBall);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        OrderBall orderBall = orderBallService.findById(id);
        return ResultGenerator.genSuccessResult(orderBall);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<OrderBall> list = orderBallService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
