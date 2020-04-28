package com.course.system.controller;

import com.course.system.domain.Test;
import com.course.system.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zcc
 * @date 2020/4/27 22:57
 * @description
 */
@RestController
public class TestController {

    @Resource
    private TestService testService;
    @RequestMapping("/test")
    public List<Test> test(){
        return testService.list();
    }
}
