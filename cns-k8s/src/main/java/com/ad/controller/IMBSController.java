package com.ad.controller;

import com.ad.service.IBMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/ibms")
public class IMBSController {

    @Autowired
    private IBMSService ibmsService;
    @PostMapping("/create/topo")
    public String createIBMSTopoInfo(@RequestParam("targetMachineType") String targetMachineType,
                                     @RequestParam("parentId") Integer parentId,
                                     @RequestParam("topoName") String topoName
                                     ) throws IOException {
        ibmsService.initProfile();
        ibmsService.createTopo();
        ibmsService.createMachine(targetMachineType,parentId,topoName);
        return "OK";
    }
}
