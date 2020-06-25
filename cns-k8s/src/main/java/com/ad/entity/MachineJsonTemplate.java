package com.ad.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
public class MachineJsonTemplate {
    public static JSONObject getMachineJsonTemplate(){
        JSONObject rtv = new JSONObject();
        rtv.put("name","machineName");
        rtv.put("parentId",0);
        rtv.put("topoName","HSTest");
        rtv.put("layerName","Machine");
        rtv.put("modelId",0);
        JSONObject initialProperty = initialProperty();
        rtv.put("initialProperty",initialProperty);
        JSONObject initialFeature = initialFeature();
        rtv.put("initialFeature",initialFeature);
        return null;
    }

    private static JSONObject initialProperty(){
        JSONObject image = new JSONObject();
        image.put("imgId","");
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
        JSONObject iotSense = new JSONObject();
//        iotSense.put("groupId","");
//        iotSense.put("type","");
//        iotSense.put("deviceId","");
//        iotSense.put("groupName","");
//        iotSense.put("deviceName","");
//        iotSense.put("deviceType","");
        JSONObject rtv = new JSONObject();
        rtv.put("image", image);
        rtv.put("Time Zone", TimeZone);
        rtv.put("iotSense", iotSense);
        return rtv;
    }
    private static JSONObject initialFeature(){
        JSONArray alarm = new JSONArray();
        JSONArray measure = new JSONArray();
        JSONArray monitor = new JSONArray();
        JSONObject rtv = new JSONObject();
        rtv.put("alarm", alarm);
        rtv.put("measure", measure);
        rtv.put("monitor", monitor);
        return rtv;
    }
}
