package com.course.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zcc
 * @date 2020/4/27 22:57
 * @description
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public String test(){
        return "success";
    }
}
