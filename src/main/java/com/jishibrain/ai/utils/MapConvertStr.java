package com.jishibrain.ai.utils;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by sunxiufang on 2020/4/3 11:33
 */
public class MapConvertStr {

    public static String getStrByMap(Map<String,Object> map){

        JSONObject jsonObject= JSONObject.fromObject(map);
        String str = jsonObject.toString();
        return str;
    }
}
