package com.ad.service.impl;

import com.ad.entity.MachineDTO;
import com.ad.entity.MachineJsonTemplate;
import com.ad.entity.MachineTagMap;
import com.ad.entity.ResultBody;
import com.ad.service.IBMSService;
import com.ad.util.ExcelReaderUtil;
import com.ad.util.HttpRequestUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.ad.util.ExcelReaderUtil.XLSX;
import static com.ad.util.ExcelReaderUtil.parseTagConfigSheet;

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
    public void initProfile(boolean ifNeedCreationMonitor) throws Exception {
        System.out.println("initProfile...");
        //创建monitor
        //[{"kind":"monitor","name":"AHU1:AM","description":"手自動模式","type":"monitor","item":[]}]
        //https://api-apm-apmstage-eks005.bm.wise-paas.com.cn/property
        parseTagConfigSheet();
        if(ifNeedCreationMonitor){
            final Map<String, List<String>> map = MachineTagMap.getMap();
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
                sendPostRequestToAPM(param, JSONArray.class);
            }
        }

        System.out.println("创建monitor成功");
    }


    private ResultBody<?> sendPostRequestToAPM(JSON param, Class returnClazz) throws Exception {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String authorization = request.getHeader("Authorization") == null ? request.getHeader("authorization")
                : request.getHeader("Authorization");
        String apmURl = apmProtocol + apmHost + "/api-apm/property";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION,authorization);
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return HttpRequestUtils.post(apmURl,httpHeaders,param.toJSONString(),returnClazz);
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
            final List<String> tagList = MachineTagMap.getTagList(machineType);
            for(String tag:tagList){
                JSONObject tagObj = new JSONObject();
                String tagStr = this.type + "@" + groupId + "@" + machineName + ":" + tag;
                tagObj.put("tag", tagStr);
                tagObj.put("name", machineType + ":"+tag);
                tagObj.put("description", "");
                monitor.add(tagObj);
            }
//            System.out.println("设备：" +  machineName + "的machineJsonTemplate= " + machineJsonTemplate.toJSONString());
            JSONArray para = new JSONArray();
            para.add(machineJsonTemplate);
            sendPostRequestToAPM(para, JSONArray.class);
            monitor.clear();
            System.out.println("设备：" +  machineName + "添加成功！");
        }
    }

//    private void createMachineToAPM(JSONObject machineJson) throws Exception {
//        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//        String authorization = request.getHeader("Authorization") == null ? request.getHeader("authorization")
//                : request.getHeader("Authorization");
//        String apmURl = apmProtocol + apmHost + createPath;
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(HttpHeaders.AUTHORIZATION,authorization);
//        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//        JSONArray para = new JSONArray();
//        para.add(machineJson);
//        HttpRequestUtils.post(apmURl,httpHeaders,para.toJSONString(),JSONArray.class);
//    }
}
