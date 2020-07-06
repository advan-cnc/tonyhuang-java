package com.ad.service.impl;

import com.ad.entity.ResultBody;
import com.ad.service.ProfileIService;
import com.ad.util.APMClientUtil;
import com.ad.util.MachineUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.ad.util.APMClientUtil.sendGetRequestToAPM;

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
        final ResultBody<?> resultBody = APMClientUtil.sendPostRequestToAPM(apmURl, param, JSONObject.class);
        final JSONObject data = (JSONObject)resultBody.getData();
        final Integer profileId = data.getInteger("id");
        System.out.println("常见设备大类：【" + param.getString("category")
                + "】下的设备类型：【" + param.getString("name") + "】的profile成功");
    }

    @Override
    public boolean hasExists(String profileName) throws Exception{
        String apmURl = apmProtocol + apmHost + addProfilePath;
        apmURl = apmURl + "?name=" + profileName;
        final ResultBody<?> resultBody = sendGetRequestToAPM(apmURl, JSONObject.class);
        final Object resultBodyData = resultBody.getData();
        return !Objects.isNull(resultBodyData);
    }

    @Override
    public Object getProfile(String profileName) throws Exception {
        String apmURl = apmProtocol + apmHost + addProfilePath;
        apmURl = apmURl + "?name=" + profileName;
        final ResultBody<?> resultBody = sendGetRequestToAPM(apmURl, JSONObject.class);
        return resultBody.getData();
    }
}
