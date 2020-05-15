package com.ad.controller;

import com.ad.util.TestThreadLocalUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @GetMapping("/hello")
    public String test(){
        System.out.println("TestController test");
        System.out.println(TestThreadLocalUtil.get());
        return "hello helm";
    }

}
