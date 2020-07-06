package com.ad.util;

import com.ad.entity.ResultBody;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class APMClientUtil {
    public static ResultBody<?> sendPostRequestToAPM(String apmURl, JSON param, Class returnClazz) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION,getAuthorization());
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return HttpRequestUtils.post(apmURl,httpHeaders,param.toJSONString(),returnClazz);
    }

    public static ResultBody<?> sendGetRequestToAPM(String apmURl,  Class returnClazz) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, getAuthorization());
        return HttpRequestUtils.get(apmURl,httpHeaders,returnClazz);
    }

    private static String getAuthorization(){
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String authorization = request.getHeader("Authorization") == null ? request.getHeader("authorization")
                : request.getHeader("Authorization");
        if(StringUtils.isEmpty(authorization)){
            throw new IllegalArgumentException("用户请登录或者传入Authorization的header");
        }
        return authorization;
    }
}
