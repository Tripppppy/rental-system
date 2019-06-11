package com.suke.RentalSystem.web;

import com.suke.RentalSystem.bo.BallSearchParamBO;
import com.suke.RentalSystem.core.Result;
import com.suke.RentalSystem.core.ResultGenerator;
import com.suke.RentalSystem.model.Ball;
import com.suke.RentalSystem.service.BallService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suke.RentalSystem.service.CodeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ball")
public class BallController {
    @Resource
    private BallService ballService;
    @Resource
    private CodeService codeService;

    /**
     * 增加一个球类
     * */
    @PostMapping
    public Result add(@Validated @RequestBody Ball ball) {
        ballService.save(ball);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 根据Id删除球类
     * */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        ballService.deleteByPK(id);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 更新一个球类
     **/
    @PutMapping
    public Result update(@Validated @RequestBody Ball ball) {
        ballService.updateByPK(ball);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 根据ID查找球类
     * */
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Ball ball = ballService.findById(id);
        return ResultGenerator.genSuccessResult(ball);
    }

    /**
     * 分页查询球类
     * */
    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Ball> list = ballService.findAll();
        list.forEach(item -> {
            item.setBrandName(codeService.findByCode(item.getBrand()).getName());
            item.setTypeName(codeService.findByCode(item.getType()).getName());
        });
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     *
     * */
    @PostMapping("/search")
    public Result search(@RequestBody(required = false) BallSearchParamBO paramBO, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Ball> list = ballService.search(paramBO);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
