package com.jit.aquaculture.serviceimpl.daily;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jit.aquaculture.AquacultureApplication;
import com.jit.aquaculture.commons.util.JacksonUtils;
import com.jit.aquaculture.commons.util.ReadFileUtils;
import com.jit.aquaculture.config.iot.EquipType;
import com.jit.aquaculture.config.iot.SensorCmd;
import com.jit.aquaculture.config.iot.SensorValue;
import com.jit.aquaculture.serviceinterface.daily.TypeListService;
import org.apache.commons.io.FileUtils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



@Service
public class TypeListServiceImpl implements TypeListService {
    private static StringBuilder all = new StringBuilder();

    private JSONObject getJsonString(){
        InputStream stream = getClass().getClassLoader().getResourceAsStream("/json/daily_type.json");

        StringBuilder s = ReadFileUtils.readFileStream(stream);
        JSONObject jsonObject = JSON.parseObject(s.toString());
        return jsonObject;
    }

    @Override
    public List<String> getFixedThrowName() {
       JSONObject jsonObject = getJsonString();
       List<String> list = null;
       String fixed = jsonObject.get("fixed_throw").toString();
       list =  JacksonUtils.readValue(fixed.toString(), new TypeReference<List<String>>() { });
        return list;
    }

    @Override
    public List<String> getSeedName() {
        JSONObject jsonObject = getJsonString();
        List<String> list = null;
        String fixed = jsonObject.get("seed_name").toString();
        list =  JacksonUtils.readValue(fixed.toString(), new TypeReference<List<String>>() { });
        return list;
    }

    @Override
    public List<String> getSeedBrand() {
        JSONObject jsonObject = getJsonString();
        List<String> list = null;
        String fixed = jsonObject.get("seed_brand").toString();
        list =  JacksonUtils.readValue(fixed.toString(), new TypeReference<List<String>>() { });
        return list;
    }

    @Override
    public List<String> getFeedName() {
        JSONObject jsonObject = getJsonString();
        List<String> list = null;
        String fixed = jsonObject.get("feed_name").toString();
        list =  JacksonUtils.readValue(fixed.toString(), new TypeReference<List<String>>() { });
        return list;
    }

    @Override
    public List<String> getFeedContent() {
        JSONObject jsonObject = getJsonString();
        List<String> list = null;
        String fixed = jsonObject.get("feed_content").toString();
        list =  JacksonUtils.readValue(fixed.toString(), new TypeReference<List<String>>() { });
        return list;
    }

    @Override
    public List<String> getMedicineName() {
        JSONObject jsonObject = getJsonString();
        List<String> list = null;
        String fixed = jsonObject.get("medicine_name").toString();
        list =  JacksonUtils.readValue(fixed.toString(), new TypeReference<List<String>>() { });
        return list;
    }

    @Override
    public List<String> getObserveName() {
        JSONObject jsonObject = getJsonString();
        List<String> list = null;
        String fixed = jsonObject.get("observe_name").toString();
        list =  JacksonUtils.readValue(fixed.toString(), new TypeReference<List<String>>() { });
        return list;
    }

    @Override
    public List<String> getObserveContent() {
        JSONObject jsonObject = getJsonString();
        List<String> list = null;
        String fixed = jsonObject.get("observe_content").toString();
        list =  JacksonUtils.readValue(fixed.toString(), new TypeReference<List<String>>() { });
        return list;

    }

    @Override
    public List<String> getBuyName() {
        JSONObject jsonObject = getJsonString();
        List<String> list = null;
        String fixed = jsonObject.get("buy_name").toString();
        list =  JacksonUtils.readValue(fixed.toString(), new TypeReference<List<String>>() { });
        return list;
    }

    @Override
    public List<String> getSaleName() {
        JSONObject jsonObject = getJsonString();
        List<String> list = null;
        String fixed = jsonObject.get("sale_name").toString();
        list =  JacksonUtils.readValue(fixed.toString(), new TypeReference<List<String>>() { });
        return list;
    }
}
