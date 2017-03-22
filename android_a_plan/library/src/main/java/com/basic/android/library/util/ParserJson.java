package com.basic.android.library.util;

import android.text.TextUtils;
import android.util.Log;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jackson 通用解析.
 */
public final class ParserJson {

    private static final String TAG = "ParserJson";

    private static ObjectMapper sObjectMapper = getJsonObjectMapper();

    private ParserJson() {
        // do nothing
    }

    /**
     * 获取统一的ObjectMapper.
     *
     * @return 统一的ObjectMapper.
     */
    public static ObjectMapper getJsonObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        return objectMapper;
    }


    /**
     * 转换json为对象.
     *
     * @param jsonParser  需要处理的json 字串.
     * @param <T>         需要传入的泛型类
     * @param objectClass 转换成的对象.
     * @return 转化的对象.
     * @throws IOException 解析异常.
     */
    public static <T> T getObject(String jsonParser, Class<T> objectClass) throws IOException {
        return sObjectMapper.readValue(jsonParser, objectClass);
    }

    /**
     * 转换json Array.
     *
     * @param jsonParser  需要处理的json 字串.
     * @param <T>         需要传入的泛型类
     * @param objectClass 转换成的对象.
     * @return 对象列表.
     * @throws IOException 解析异常.
     */
    public static <T> List<T> getObjectList(String jsonParser, Class<T> objectClass)
            throws IOException {
        return sObjectMapper.readValue(jsonParser, new TypeReference<List<T>>() {
        });
    }

    /**
     * 将对象转化为json字串.
     *
     * @param object 待转换json对象.
     * @param <T>    需要传入的泛型类
     * @return json字符串.
     * @throws IOException 解析异常.
     */
    public static <T> String getJsonStr(T object) throws IOException {
        return sObjectMapper.writeValueAsString(object);
    }

    /**
     * 将json转换为Map<String,String>，不用捕获异常.
     *
     * @param jsonString 需要处理的json 字串.
     * @return Map<String,String>
     */
    public static Map<String, String> getSafeStringMap(String jsonString) {
        Map<String, String> result = new HashMap<String, String>();
        if (!TextUtils.isEmpty(jsonString)) {
            try {
                result = sObjectMapper.readValue(jsonString, new TypeReference<Map<String, String>>() {
                });
            } catch (IOException e) {
                Log.e(TAG, "exception", e);
            }
        }
        return result;
    }

    /**
     * 将对象转化为json字串,不用捕获异常.
     *
     * @param object 待转换json对象.
     * @param <T>    需要传入的泛型类
     * @return json字符串.
     */
    public static <T> String getSafeJsonStr(T object) {
        String result = null;
        try {
            result = sObjectMapper.writeValueAsString(object);
        } catch (IOException e) {
            Log.e(TAG, "exception", e);
        }
        return result;
    }

    /**
     * 安全的转换json为对象.
     *
     * @param jsonParser  需要处理的json 字串.
     * @param <T>         需要传入的泛型类
     * @param objectClass 转换成的对象.
     * @return 转化的对象.
     */
    public static <T> T getSafeObject(String jsonParser, Class<T> objectClass) {
        if (TextUtils.isEmpty(jsonParser)) {
            return null;
        }
        T obj = null;
        try {
            obj = sObjectMapper.readValue(jsonParser, objectClass);
        } catch (IOException e) {
            Log.e(TAG, "exception", e);
        }
        return obj;
    }

}
