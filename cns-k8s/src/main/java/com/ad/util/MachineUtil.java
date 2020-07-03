package com.ad.util;

import java.util.HashMap;
import java.util.Map;

public class MachineUtil {

    private static final Map<String,Integer> machineTypeModel =
            new HashMap<>();
    static {
        machineTypeModel.put("ZWMD",93);
        machineTypeModel.put("EKG",90);
        machineTypeModel.put("ZWADD2",89);
        machineTypeModel.put("ZWADD1",88);
        machineTypeModel.put("AIT5",87);
        machineTypeModel.put("CTADD",86);
        machineTypeModel.put("SFG",85);
        machineTypeModel.put("CHPRO",84);
        machineTypeModel.put("AIT6",83);
        machineTypeModel.put("CHUG",82);
        machineTypeModel.put("CHUC",81);
        machineTypeModel.put("CHADD",80);
        machineTypeModel.put("VAE2",79);
        machineTypeModel.put("VAE1",78);
        machineTypeModel.put("US", 77);
        machineTypeModel.put("TWB",76);
        machineTypeModel.put("PUMPS",75);
        machineTypeModel.put("PUMP",73);
        machineTypeModel.put("PIZ5",72);
        machineTypeModel.put("PIDC", 71);
        machineTypeModel.put("PID1",70);
        machineTypeModel.put("PCV",69);
        machineTypeModel.put("LEVEL",68);
        machineTypeModel.put("CTMED",67);
        machineTypeModel.put("CTMD", 66);
        machineTypeModel.put("CHU",65);
        machineTypeModel.put("CHSEQ",64);
        machineTypeModel.put("AIT2",63);
        machineTypeModel.put("AIT1",62);
        machineTypeModel.put("AIP2", 61);
        machineTypeModel.put("AIF1", 60);
        machineTypeModel.put("AIDP", 59);

        machineTypeModel.put("AHU1",23);
        machineTypeModel.put("AHU2",24);
        machineTypeModel.put("AIC1",25);
        machineTypeModel.put("AIC2", 26);
        machineTypeModel.put("AIM1", 27);
        machineTypeModel.put("AIP1",28);
        machineTypeModel.put("AIT1",29);
        machineTypeModel.put("AITR",30);
        machineTypeModel.put("BASYS", 31);
        machineTypeModel.put("BOA", 32);
        machineTypeModel.put("BTU",33);
        machineTypeModel.put("CO2F",34);
        machineTypeModel.put("CO2T",36);
        machineTypeModel.put("EF1", 37);
        machineTypeModel.put("EF2", 38);
        machineTypeModel.put("EKG",39);
        machineTypeModel.put("FCUG",40);
        machineTypeModel.put("FCUS",41);
        machineTypeModel.put("MISAIR2", 42);
        machineTypeModel.put("MISUPS", 43);
        machineTypeModel.put("PID1",44);
        machineTypeModel.put("PID5",45);
        machineTypeModel.put("PID6",46);
        machineTypeModel.put("PIDA", 47);
        machineTypeModel.put("PROC", 48);
        machineTypeModel.put("SF1",91);
        machineTypeModel.put("SFG",49);
        machineTypeModel.put("TCO",50);
        machineTypeModel.put("UBIQ", 51);
        machineTypeModel.put("VAE1", 92);
        machineTypeModel.put("VAV", 52);
        machineTypeModel.put("MISAIR1", 96);

        machineTypeModel.put("GENO", 17);
        machineTypeModel.put("EXAT", 97);
        machineTypeModel.put("FGRP", 99);
        machineTypeModel.put("FIRE", 98);
        machineTypeModel.put("LGH", 100);
        machineTypeModel.put("LTON", 101);
        machineTypeModel.put("SEN", 102);
    }




    public static Integer getModelId(String machineType){
        return machineTypeModel.get(machineType);
    }

    public static void setModelId(String type, Integer modelId){
        machineTypeModel.put(type,modelId);
    }
}
