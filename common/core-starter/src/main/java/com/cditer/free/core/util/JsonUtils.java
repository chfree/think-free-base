package com.cditer.free.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static{
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String toJson(Object obj){
        if(obj==null){
            return null;
        }

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("转换json异常", e);
            return null;
        }
    }

    public static <T> T toObject(Object objJson,Class<T> type){
        if(objJson == null){
            return null;
        }
        return toObject(objJson.toString(), type);
    }

    public static <T> T toObject(String strJson,Class<T> type){
        try {
            return objectMapper.readValue(strJson, type);
        } catch (JsonProcessingException ex) {
            log.error("转换json异常", ex);
            return null;
        }
    }
}
