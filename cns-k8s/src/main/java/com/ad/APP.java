package com.ad;

import com.ad.service.impl.MachineIServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class APP {
    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(APP.class, args);
    }
}
