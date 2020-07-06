package com.ad.service.impl;

import com.ad.entity.MachineDTO;
import com.ad.entity.MachineJsonTemplate;
import com.ad.entity.MachineTagAndCategoryMap;
import com.ad.service.IBMSService;
import com.ad.util.ExcelReaderUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.ad.entity.MachineTagAndCategoryMap.*;
import static com.ad.util.APMClientUtil.sendPostRequestToAPM;


@Service
public class IBMSServiceImpl implements IBMSService {

    @Value("${apm.protocol}")
    private String apmProtocol;


    @Value("${apm.host}")
    private String apmHost;

    @Value("${apm.machine.create-machine-path}")
    private String createMachinePath;

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


    @Value("${apm.machine.property-path}")
    private String propertyPath;

    @Value("${apm.version}")
    private String apmVersion;


    @Autowired
    private MachineIServiceImpl machineIService;

    @Override
    public synchronized JSONObject  initProfile(boolean ifNeedCreationMonitor) throws Exception {
        System.out.println("initProfile...");
        JSONObject rtv = new JSONObject();
        //创建monitor
        //[{"kind":"monitor","name":"AHU1:AM","description":"手自動模式","type":"monitor","item":[]}]
        //https://api-apm-apmstage-eks005.bm.wise-paas.com.cn/property
        if(ifNeedCreationMonitor){
            final Map<String, List<String>> map = MachineTagAndCategoryMap.getMachineTypeAndTagListMap();
            final Set<Map.Entry<String, List<String>>> entrySet = map.entrySet();
            final Iterator<Map.Entry<String, List<String>>> iterator = entrySet.iterator();
            while (iterator.hasNext()){
                final Map.Entry<String, List<String>> next = iterator.next();
                final String type = next.getKey();
                final List<String> tags = next.getValue();
                JSONArray param = new JSONArray();
                for(String tag:tags){
                    JSONObject monitor = new JSONObject();
                    monitor.put("kind","monitor");
                    monitor.put("name",type + ":" + tag);
                    monitor.put("description","monitor");
                    monitor.put("type","monitor");
                    monitor.put("item",new JSONArray());
                    param.add(monitor);
                }
                String apmURl = apmProtocol + apmHost + propertyPath;
                System.out.println("开始创建monitor:" +  param);
                sendPostRequestToAPM(apmURl, param, JSONArray.class);
            }
        }
        System.out.println("创建monitor全部成功");
        createProfilesInCategory(rtv);
        return rtv;
    }


    /**
     * 创建指定大类的所有设备的profile
     */
    private void createProfilesInCategory(JSONObject rtv) throws Exception {
        final Map<String, Set<String>> machineCategoryAndMachineTypeMap = getMachineCategoryAndMachineTypeMap();
        rtv.put("machineCategoryAndMachineTypeMap",machineCategoryAndMachineTypeMap);
        final Set<Map.Entry<String, Set<String>>> entries = machineCategoryAndMachineTypeMap.entrySet();
        final Iterator<Map.Entry<String, Set<String>>> iterator = entries.iterator();
        int cCount = 0;
        int hCount = 0;
        while (iterator.hasNext()){
            final Map.Entry<String, Set<String>> next = iterator.next();
            final String category = next.getKey();
            final Set<String> types = next.getValue();
            for(String machineType: types){
                final boolean hasExists = machineIService.hasExists(machineType);
                if(!hasExists){
                    createMachineProfile(category,machineType);
                    cCount++;
                }else {
                    System.out.println("【" + category + "】系统下的设备类型【" + machineType + "】profile已经创建了");
                    hCount++;
                }

            }
        }
        rtv.put("createProfileCount",cCount);
        rtv.put("hasExistsProfileCount",hCount);
        rtv.put("totalProfileCount",hCount + cCount);
        System.out.println("创建设备profile完毕，成功创建设备profile【" + cCount + "】个！已经存在设备profile【" + hCount + "】个");
    }

    private void createMachineProfile(String category,String machineType) throws Exception {
        final List<String> tagList = getTagList(machineType);
        JSONObject profileParam = new JSONObject();
        profileParam.put("name", machineType);
        profileParam.put("type", "machine");
        profileParam.put("category", category);
        profileParam.put("description", "des");
        //可以读取配置项
        profileParam.put("version", "APM 1.1.55");
        JSONObject feature = new JSONObject();
        feature.put("monitor",createMonitor(machineType ,tagList));
        feature.put("measure",new JSONArray());
        feature.put("report",new JSONArray());
        feature.put("alarm",new JSONArray());
        feature.put("video",new JSONArray());
        profileParam.put("feature",feature);
        JSONObject property = new JSONObject();
        profileParam.put("property",property);
        JSONObject TimeZone = new JSONObject();
        JSONObject scope = new JSONObject();
        JSONArray TimeZones = new JSONArray(Arrays.asList("UTC+0:00 London",
                "UTC+1:00 Paris",
                "UTC+2:00 Athens",
                "UTC+3:00 Jeddah",
                "UTC+4:00 Dubai",
                "UTC+5:00 KHI",
                "UTC+6:00 Dhaka",
                "UTC+7:00 Bangkok",
                "UTC+8:00 Bei Jing",
                "UTC+9:00 Tokyo",
                "UTC+10:00 Sydney",
                "UTC+11:00 Noumea",
                "UTC+12:00 Wellington",
                "UTC-11:00 Pago Pago",
                "UTC-10:00 Honolulu",
                "UTC-9:00 Anchorage",
                "UTC-8:00 Los Angeles",
                "UTC-7:00 Denver",
                "UTC-6:00 Chicago",
                "UTC-5:00 New York",
                "UTC-4:00 Santiago",
                "UTC-3:00 Rio De Janeiro",
                "UTC-2:00 Fernando de Noronha",
                "UTC-1:00 Praia"));
        scope.put("Time Zone", TimeZones);
        TimeZone.put("scope",scope);
        TimeZone.put("Time Zone","Asia/Shanghai");
        TimeZone.put("description","");
        property.put("Time Zone", TimeZone);
        property.put("image", new JSONObject());
        machineIService.createProfile(profileParam);

    }

    /**
     * 创建指定设备类型的
     * @param type
     * @param tagList
     * @return
     */
    private JSONArray createMonitor(String type, List<String> tagList) {
        JSONArray rtv = new JSONArray();
        tagList.forEach(tag->{
            JSONObject tagObj = new JSONObject();
           String t = type + ":" + tag;
            tagObj.put("name",t);
            tagObj.put("description","monitor");
            tagObj.put("tag",t);
            rtv.add(tagObj);
        });
        return rtv;
    }


    @Override
    public void createTopo() {
        System.out.println("createTopo...");
    }

    @Override
    public void createMachine(String targetMachineType,Integer parentId, String topoName,String floor) throws Exception {
        System.out.println("start createMachine ...");
        //读取文件
        List<MachineDTO> machineDTOList =  ExcelReaderUtil.parseDeviceConfigSheet(targetMachineType,topoName,floor);
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
            final String machineType = machineDTO.getType();
            final String machineName = machineDTO.getName();
            machineJsonTemplate.put("name", machineName);
            machineJsonTemplate.put("parentId",parentId);
            machineJsonTemplate.put("topoName",topoName);
            machineJsonTemplate.put("modelId",machineDTO.getModelId());
            //绑定tag
            final JSONArray monitor = machineJsonTemplate.getJSONObject("initialFeature").getJSONArray("monitor");
//            System.out.println("设备："+ machineName + "的initialFeature是" + monitor);
//            System.out.println("设备："+ machineName + "的machineJsonTemplate是" + machineJsonTemplate);
            final List<String> tagList = MachineTagAndCategoryMap.getTagList(machineType);
            for(String tag:tagList){
                JSONObject tagObj = new JSONObject();
                String tagStr = this.type + "@" + groupId + "@" + machineName + ":" + tag;
                tagObj.put("tag", tagStr);
                tagObj.put("name", machineType + ":"+tag);
                tagObj.put("description", "");
                monitor.add(tagObj);
            }
//            System.out.println("设备：" +  machineName + "的machineJsonTemplate= " + machineJsonTemplate.toJSONString());
            createMachineToAPM(machineJsonTemplate);
            monitor.clear();
            System.out.println("设备：" +  machineName + "添加成功！");
        }
    }

    private void createMachineToAPM(JSONObject machineJson) throws Exception {
        JSONArray para = new JSONArray();
        para.add(machineJson);
        String apmURl = apmProtocol + apmHost + createMachinePath;
        sendPostRequestToAPM(apmURl, para, JSONArray.class);
    }
}
