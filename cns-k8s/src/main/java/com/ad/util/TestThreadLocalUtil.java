package com.ad.util;

public class TestThreadLocalUtil {
    private static final ThreadLocal<String>  nameThreadLocal = new ThreadLocal<String>(){
        @Override
        protected String initialValue(){
            return "hello";
        }
    };

    public static String get(){
        return nameThreadLocal.get();
    }

    public static void set(String ss){
        nameThreadLocal.set(ss);
    }
    public static void remove(){
        nameThreadLocal.remove();
    }
}
