package com.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class APP {
    public static void main(String[] args) {
        SpringApplication.run(APP.class,args);
        //读取设备的topo表
    }
}
