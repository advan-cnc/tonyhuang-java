package com.ad.util;

import com.ad.entity.ResultBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class APMClientUtil {
    public static ResultBody<?> sendPostRequestToAPM(String apmURl, JSON param, Class returnClazz) throws Exception {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String authorization = request.getHeader("Authorization") == null ? request.getHeader("authorization")
                : request.getHeader("Authorization");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION,authorization);
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return HttpRequestUtils.post(apmURl,httpHeaders,param.toJSONString(),returnClazz);
    }
}
