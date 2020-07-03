package com.ad.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MachineTagAndCategoryMap {
    private static final Map<String, List<String>> map = new HashMap<>();

    public static void init(String type, String tagName){
        if(map.containsKey(type)){
            final List<String> tags = map.get(type);
            if(!tags.contains(tagName)){
                tags.add(tagName);
            }
        }else {
            List<String> tags = new ArrayList<>();
            tags.add(tagName);
            map.put(type,tags);
        }
    }

    public static List<String> getTagList(String type){
        return map.get(type);
    }

    public static int getCount(){
        return map.size();
    }

    public static Map<String, List<String>> getMap(){
        return map;
    }
}
