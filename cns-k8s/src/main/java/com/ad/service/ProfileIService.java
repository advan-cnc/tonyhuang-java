package com.ad.service;

import com.alibaba.fastjson.JSONObject;

public interface ProfileIService<T> {
     void createProfile(T param) throws Exception;
     boolean hasExists(String profileName) throws Exception;
     Object getProfile(String profileName) throws Exception;
}
