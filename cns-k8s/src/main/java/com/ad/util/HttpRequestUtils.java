package com.ad.util;

import com.ad.entity.ResultBody;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HttpRequestUtils {
    public static <T> ResultBody<T> get(String url, HttpHeaders headers, Class<T> clazz) throws Exception {
        return getObjectResultBody(url, HttpMethod.GET, headers,null,clazz);
    }

    public static <T> ResultBody<T> post(String url, HttpHeaders headers, String reqJsonString,Class clazz) throws Exception {
        return getObjectResultBody(url, HttpMethod.POST, headers, reqJsonString,clazz);
    }

    public static <T> ResultBody<T> put(String url, HttpHeaders headers, String reqJsonString,Class clazz) throws Exception {
        return getObjectResultBody(url, HttpMethod.PUT, headers, reqJsonString,clazz);
    }

    private static <T> ResultBody<T> getObjectResultBody(
            String url,HttpMethod method,HttpHeaders headers,String reqJsonString,Class<T> clazz) throws Exception {
        System.out.println("请求的url= " + url);
        System.out.println("请求的para= " + reqJsonString);
        RestTemplate restTemplate = new RestTemplate();
        ResultBody<Object> requestResult = new ResultBody<Object>();
        HttpEntity<String> request = new HttpEntity<String>(reqJsonString, headers);
        if(reqJsonString == null){
            request = new HttpEntity<String>(headers);
        }
        try {
            final ResponseEntity<T> objectResponseEntity = restTemplate.exchange(url, method, request, clazz);
            if (objectResponseEntity.getStatusCodeValue() != 200
                    && objectResponseEntity.getStatusCodeValue() != 201) {
                return requestResult.setCode(objectResponseEntity.getStatusCodeValue()).setMessage(objectResponseEntity.getBody().toString());
            }
            return requestResult.setMessage("success").setCode(200).setData(objectResponseEntity.getBody());
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("发送请求失败，url=" + url);
        }
    }

}
