package com.jishibrain.ai.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 针对整个项目中的参数封装的操作类
 * Created by sunxiufang on 2020/6/1 10:06
 */
public class ParameterUtil {

    private static Map<String, Object> propsMap = new HashMap<>();

    public void clear() {
        propsMap.clear();
    }

    public static Object getObject(String key) {
        return propsMap.get(key);
    }

    public static Map<String, Object> putObject(String key, Object value) {
        propsMap.put(key, value);
        return propsMap;
    }

    public static String get(String key) {
        return "" + propsMap.get(key);
    }

    public static Map<String, Object> put(String key, Object value) {
        propsMap.put(key, value);
        return propsMap;
    }

    public static Map<String, Object> remove(String key) {
        propsMap.remove(key);
        return propsMap;
    }

}
