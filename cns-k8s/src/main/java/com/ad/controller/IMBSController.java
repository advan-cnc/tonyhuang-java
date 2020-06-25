package com.ad.controller;

import com.ad.service.IBMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
                                     ) throws Exception {
        ibmsService.createTopo();
        ibmsService.createMachine(targetMachineType,parentId,topoName);
        return "OK";
    }

    @GetMapping("/init")
    public String createIBMSTopoInfo() throws Exception {
        ibmsService.initProfile();
        return "OK";
    }
}
