package com.ad.service.impl;

import com.ad.service.ProfileIService;
import com.ad.util.APMClientUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MachineIServiceImpl implements ProfileIService<JSONObject> {

    @Value("${apm.protocol}")
    private String apmProtocol;

    @Value("${apm.host}")
    private String apmHost;

    @Value("${apm.machine.add-profile-path}")
    private String addProfilePath;

    @Override
    public void createProfile(JSONObject param) throws Exception {
        String apmURl = apmProtocol + apmHost + addProfilePath;
        APMClientUtil.sendPostRequestToAPM(apmURl, param, JSONObject.class);
    }
}
