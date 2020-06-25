package com.ad.service.impl;

import com.ad.entity.MachineDTO;
import com.ad.entity.MachineJsonTemplate;
import com.ad.entity.MachineTagMap;
import com.ad.service.IBMSService;
import com.ad.util.ExcelReaderUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.ad.util.ExcelReaderUtil.XLSX;

@Service
public class IBMSServiceImpl implements IBMSService {

    @Value("${apm.protocol}")
    private String apmProtocol;


    @Value("${apm.host}")
    private String apmHost;

    @Value("${apm.machine.create-path}")
    private String createPath;

    @Value("${ibms.groupId}")
    private String groupId;

    @Value("${ibms.deviceType}")
    private String deviceType;

    @Value("${ibms.type}")
    private String type;

    @Value("${ibms.deviceId}")
    private String deviceId;

    @Value("${ibms.groupName}")
    private String groupName;

    @Value("${ibms.deviceName}")
    private String deviceName;

    @Override
    public void initProfile() {
        System.out.println("initProfile...");
    }

    @Override
    public void createTopo() {
        System.out.println("createTopo...");
    }

    @Override
    public void createMachine(String targetMachineType,Integer parentId, String topoName) throws IOException {
        System.out.println("start createMachine ...");
        //读取文件
        List<MachineDTO> machineDTOList =  ExcelReaderUtil.parseDeviceConfigSheet(targetMachineType);
        System.out.println("设备类型【" + targetMachineType + "】有【" + machineDTOList.size() + "】台设备");
        final JSONObject machineJsonTemplate = MachineJsonTemplate.getMachineJsonTemplate();
        //设置共有属性
        final JSONObject initialProperty = machineJsonTemplate.getJSONObject("initialProperty");
        final JSONObject iotSense = initialProperty.getJSONObject("iotSense");
        iotSense.put("groupId",groupId);
        iotSense.put("type",type);
        iotSense.put("deviceId",deviceId);
        iotSense.put("groupName",groupName);
        iotSense.put("deviceName",deviceName);
        iotSense.put("deviceType",deviceType);
        for (MachineDTO machineDTO:machineDTOList){
            final String machineName = machineDTO.getName();
            machineJsonTemplate.put("name", machineName);
            machineJsonTemplate.put("parentId",parentId);
            machineJsonTemplate.put("topoName",topoName);
            machineJsonTemplate.put("modelId",machineDTO.getModelId());
            //绑定tag
            final JSONArray monitor = machineJsonTemplate.getJSONObject("initialFeature").getJSONArray("monitor");
//            System.out.println("设备："+ machineName + "的initialFeature是" + monitor);
//            System.out.println("设备："+ machineName + "的machineJsonTemplate是" + machineJsonTemplate);
            final List<String> tagList = MachineTagMap.getTagList(machineDTO.getType());
            for(String tag:tagList){
                JSONObject tagObj = new JSONObject();
                String tagStr = type + "@" + groupId + "@" + machineName + ":" + tag;
                tagObj.put("tag", tagStr);
                tagObj.put("name", tag);
                tagObj.put("description", "");
                monitor.add(tagObj);
            }
            System.out.println("设备：" +  machineName + "的machineJsonTemplate= " + machineJsonTemplate.toJSONString());
        }
    }
}
