package com.jit.aquaculture.config.iot;

import java.util.HashMap;
import java.util.Map;

public class LoraData {
    private static LoraData loraData = null;
    private static Map<String, String> map = new HashMap<>();

    static {
        map.put("1011","Rainfall(hour)");
        map.put("1010","SoilTemperaturex");
        map.put("100F","SoilElectricalConductivity");
        map.put("100E","SoilVolumetricWaterContent");
        map.put("100D","DissolvedOxygen");
        map.put("100C","EletricalConductivity");
        map.put("100B","LightQuantum");
        map.put("100A","WaterPH");
        map.put("1009","WindSpeed");
        map.put("1008","WindDirection");
        map.put("1007","SoilHumidity");
        map.put("1006","SoilTemperature");
        map.put("1005","AirPressure");
        map.put("1004","CO2");
        map.put("1003","Light");
        map.put("1002","AirHumidity");
        map.put("1001","AirTemperature");
    }

    private LoraData() {
    }

    public static LoraData getInstance() {
        if (null == loraData) {
            loraData = new LoraData();
        }
        return loraData;
    }

    public String get(String key){
        return map.get(key);
    }
}
