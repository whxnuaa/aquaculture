package com.jit.aquaculture.enums;

public enum PlantTypeEnum {

    SOLANACEOUS_VEGETABLE(0,"茄果类"),

    LEAF_VEGETABLE(1,"叶菜类"),

    MELON(2,"瓜类"),

    CABBAGE_VEGETABLE(3,"甘蓝类"),

    BEANS(4,"豆类"),

    ROOT_VEGETABLE(5,"根菜类"),

    BULB_VEGETABLE(6,"葱蒜类"),

    TUBER_VEGETABLE(7,"薯芋类"),

    AQUATIC(8,"水生类"),

    PERNNIAL_SPECIES(9,"多年生类"),

    OTHER(10,"其他")

    ;

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }

    PlantTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
