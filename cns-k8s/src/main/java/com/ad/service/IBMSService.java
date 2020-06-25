package com.ad.service;

import java.io.IOException;

public interface IBMSService {
    /**
     * 根据ibms_config.xml创建profile
     */
    void initProfile();

    /**
     * 根据ibms_config.xml创建topo
     */
    void createTopo();
    /**
     * 根据ibms_config.xml创建设备
     */
    void createMachine(String targetMachineType,Integer parentId, String topoName) throws IOException;
}
