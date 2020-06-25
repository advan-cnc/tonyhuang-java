package com.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class APP {
    public static void main(String[] args) {
        SpringApplication.run(APP.class,args);
        //docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
        //读取设备的topo表
    }
}
