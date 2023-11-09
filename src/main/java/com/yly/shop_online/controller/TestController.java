package com.yly.shop_online.controller;

import com.yly.shop_online.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @PostMapping("/login")
    public Result<String> login(){
        log.info("测试前端请求");
        Result result=new Result();
        result.setMsg("登陆成功");
        result.setCode(200);
        return result;
    }
}
