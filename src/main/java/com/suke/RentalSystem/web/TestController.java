package com.suke.RentalSystem.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suke.RentalSystem.core.Result;
import com.suke.RentalSystem.core.ResultGenerator;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/message")
    public Result getMessage() {
      PageHelper.startPage(1,4);
      PageInfo pageInfo=new PageInfo(new ArrayList(),5);
        String result = "你好";
        return ResultGenerator.genSuccessResult(result);
    }
}
