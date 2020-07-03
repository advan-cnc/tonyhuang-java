package com.ad.entity;

public class MachineDTO {
    private String name;
    private String type;
    private int modelId;
    private String category;

    public MachineDTO() {
    }

    public MachineDTO(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }
}
