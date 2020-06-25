package com.ad.util;

import java.util.HashMap;
import java.util.Map;

public class MachineUtil {

    private static final Map<String,Integer> machineTypeModel =
            new HashMap<>();
    static {
        machineTypeModel.put("",0);
        machineTypeModel.put("",0);
        machineTypeModel.put("",0);
        machineTypeModel.put("",0);
        machineTypeModel.put("",0);
        machineTypeModel.put("",0);
        machineTypeModel.put("",0);
        machineTypeModel.put("",0);
        machineTypeModel.put("",0);
    }




    public static Integer getModelId(String machineType){
        return machineTypeModel.get(machineType);
    }
}
