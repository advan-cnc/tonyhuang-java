package com.ad.service;


import com.alibaba.fastjson.JSONObject;

public interface IBMSService {
    /**
     * 根据ibms_config.xml创建profile
     */
    JSONObject initProfile(boolean ifNeedCreationMonitor) throws Exception;

    /**
     * 根据ibms_config.xml创建topo
     */
    void createTopo();
    /**
     * 根据ibms_config.xml创建设备
     */
    void createMachine(String targetMachineType,Integer parentId, String topoName,String floor) throws Exception;
}
