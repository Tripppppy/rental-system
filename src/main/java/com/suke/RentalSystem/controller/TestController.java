package com.suke.RentalSystem.controller;

import com.suke.RentalSystem.core.Result;
import com.suke.RentalSystem.core.ResultGenerator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/message")
    public Result getMessage() {
        String result = "你好";
        return ResultGenerator.genSuccessResult(result);
    }
}
