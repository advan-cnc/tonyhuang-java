package com.ad.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class MachineJsonTemplate {
    public static JSONObject getMachineJsonTemplate(){
        JSONObject rtv = new JSONObject();
        rtv.put("name","machineName");
        rtv.put("parentId",0);
        rtv.put("topoName","HSTest");
        rtv.put("layerName","Machine");
        rtv.put("modelId",0);
        JSONObject initialProperty = new JSONObject();
        rtv.put("initialProperty",initialProperty);
        JSONObject initialFeature = new JSONObject();
        rtv.put("initialFeature",initialFeature);
        return null;
    }

    private static JSONObject initialProperty(){
        JSONObject image = new JSONObject();
        JSONObject TimeZone = new JSONObject();
        JSONObject iotSense = new JSONObject();
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
