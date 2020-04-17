package com.jishibrain.ai.actions.sceneconfig.sceneconfiglist;

import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.ApiURLUtil;
import com.jishibrain.ai.utils.HttpRequestsUtil;
import com.jishibrain.ai.utils.TokenUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunxiufang on 2020/4/2 17:32
 */
public class SceneCreate {

    public Response addScene(String jsonStr){

        //添加场景地址
        ApiURLUtil apiURLUtil = new ApiURLUtil();
        String url = apiURLUtil.getApiUrl("sceneAdd.uri");

        //设置请求头
        TokenUtil tokenUtil = new TokenUtil();
        Map<String,String> header = new HashMap<String, String>();
        header.put("Authorization", tokenUtil.getTokenByConfigFile("host.properties","token"));

        //发送请求
        Response response = null;
        try {
            response = HttpRequestsUtil.doPostWithString(url, header, jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
