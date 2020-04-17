package com.jishibrain.ai.utils;

import java.util.ResourceBundle;

/**
 * Created by sunxiufang on 2020/4/1 17:53
 */
public class ApiURLUtil {

    public String getApiUrl(String uri){

        ResourceBundle bundle = ResourceBundle.getBundle("host");
        String hostUrl = bundle.getString("test.url");
        String url = hostUrl+bundle.getString(uri);

        return url;
    }

}
