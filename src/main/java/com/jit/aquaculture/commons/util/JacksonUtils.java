package com.jit.aquaculture.commons.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
//@Log4j2
public class JacksonUtils {
    public static ObjectMapper objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//    public static ObjectMapper objectMapper = new ObjectMapper();
    /*static {
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(JsonParser.Feature.INTERN_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.CANONICALIZE_FIELD_NAMES, true);
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }*/

    /**
     * 使用泛型方法,把json字符串转转为相应的javabean对象
     */
    public static <T> T readValue(String jsonStr, Class<T> valueType){
        try {
            return objectMapper.readValue(jsonStr, valueType);
        }catch (JsonParseException e) {
            log.error("decode(String, JsonTypeReference<T>)", e);
        } catch (JsonMappingException e) {
            log.error("decode(String, JsonTypeReference<T>)", e);
        } catch (IOException e) {
            log.error("decode(String, JsonTypeReference<T>)", e);
        }
        return null;
    }

    /**
     * 将json array反序列化为对象
     *
     * @param json
     * @param jsonTypeReference
     * @return
     */
    public static <T> T readValue(String json, TypeReference<T> jsonTypeReference) {
        try {
            return (T) objectMapper.readValue(json, jsonTypeReference);
        } catch (JsonParseException e) {
            log.error("decode(String, JsonTypeReference<T>)", e);
        } catch (JsonMappingException e) {
            log.error("decode(String, JsonTypeReference<T>)", e);
        } catch (IOException e) {
            log.error("decode(String, JsonTypeReference<T>)", e);
        }
        return null;
    }


    /**
     * 把jaavabean转换为json字符串
     */
    public static String toJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("toJson is error :{}",e);
        }
        return null;
    }
}
