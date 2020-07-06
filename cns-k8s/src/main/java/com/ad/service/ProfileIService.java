package com.ad.service;

public interface ProfileIService<T> {
     void createProfile(T param) throws Exception;
     boolean hasExists(String profileName) throws Exception;
}
