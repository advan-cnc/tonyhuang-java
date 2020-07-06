package com.ad.controller;

import com.ad.service.IBMSService;
import com.ad.service.impl.MachineIServiceImpl;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.Charset;

@RestController
@RequestMapping("/ibms")
public class IMBSController {

    @Autowired
    private IBMSService ibmsService;

    @Autowired
    private MachineIServiceImpl machineIService;

    @PostMapping("/create/topo")
    public String createIBMSTopoInfo(@RequestParam("targetMachineType") String targetMachineType,
                                     @RequestParam("parentId") Integer parentId,
                                     @RequestParam("topoName") String topoName,
                                     @RequestParam("floor") String floor
                                     ) throws Exception {
        ibmsService.createTopo();
        ibmsService.createMachine(targetMachineType,parentId,topoName, floor);
        return "OK";
    }

    @GetMapping("/init")
    public String createIBMSTopoInfo(@RequestParam("ifNeedCreationMonitor") boolean ifNeedCreationMonitor) throws Exception {
        System.out.println("ifNeedCreationMonitor = " + ifNeedCreationMonitor);
        final JSONObject result = ibmsService.initProfile(ifNeedCreationMonitor);
        return result.toString();
    }

    @GetMapping("/profile")
    public String checkProfileHasExists(@RequestParam("typeName") String typeName) throws Exception {
        final boolean b = machineIService.hasExists(typeName);
        return "" + b;
    }
}
