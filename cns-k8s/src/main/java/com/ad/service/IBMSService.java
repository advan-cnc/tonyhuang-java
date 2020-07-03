package com.ad.service;


public interface IBMSService {
    /**
     * 根据ibms_config.xml创建profile
     */
    void initProfile(boolean ifNeedCreationMonitor) throws Exception;

    /**
     * 根据ibms_config.xml创建topo
     */
    void createTopo();
    /**
     * 根据ibms_config.xml创建设备
     */
    void createMachine(String targetMachineType,Integer parentId, String topoName,String floor) throws Exception;
}
