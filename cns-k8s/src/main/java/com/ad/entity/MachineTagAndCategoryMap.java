package com.ad.entity;

import java.util.*;

/**
 * 存储设备类型与tagList的关系以及设备大类与设备类型的关系
 */
public class MachineTagAndCategoryMap {
    private static final Map<String, List<String>> machineTypeAndTagListMap = new HashMap<>();
    private static final Map<String, Set<String>> machineCategoryAndMachineTypeMap = new HashMap<>();

    /**
     *
     * @param type 设备类型名字
     * @param tagName tag名字
     */
    public static void initMachineTypeAndTagListMap(String type, String tagName){

        //处理machineTypeAndTagListMap
        if(machineTypeAndTagListMap.containsKey(type)){
            final List<String> tags = machineTypeAndTagListMap.get(type);
            if(!tags.contains(tagName)){
                tags.add(tagName);
            }
        }else {
            List<String> tags = new ArrayList<>();
            tags.add(tagName);
            machineTypeAndTagListMap.put(type,tags);
        }
    }


    public static void initMachineCategoryAndMachineTypeMap(String category, String machineType){
        //处理MachineCategoryAndMachineTypeMap
        if (machineCategoryAndMachineTypeMap.containsKey(category)){
            machineCategoryAndMachineTypeMap.get(category).add(machineType);
        }else {
            Set<String> machineTypes = new HashSet<>();
            machineTypes.add(machineType);
            machineCategoryAndMachineTypeMap.put(category, machineTypes);
        }
    }

    public static List<String> getTagList(String type){
        return machineTypeAndTagListMap.get(type);
    }

    public static int getCount(){
        return machineTypeAndTagListMap.size();
    }

    public static Map<String, List<String>> getMachineTypeAndTagListMap(){
        return machineTypeAndTagListMap;
    }

    public static Map<String, Set<String>> getMachineCategoryAndMachineTypeMap(){
        return machineCategoryAndMachineTypeMap;
    }


}
