package com.jishibrain.ai.actions.sceneconfig.sceneconfiglist;

import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.ApiURLUtil;
import com.jishibrain.ai.utils.HttpRequestsUtil;
import com.jishibrain.ai.utils.TokenUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建场景类别
 * Created by sunxiufang on 2020/4/2 16:41
 */
public class SceneCategoryCreate {

    public Response addSceneCate(String sceneCateName){

        //添加场景类别地址
        ApiURLUtil apiURLUtil = new ApiURLUtil();
        String url = apiURLUtil.getApiUrl("sceneCategoryCreate.uri");

        //设置请求头
        TokenUtil tokenUtil = new TokenUtil();
        Map<String,String> header = new HashMap<String, String>();
        header.put("Authorization", tokenUtil.getTokenByConfigFile("host.properties","token"));

        //设置请求参数
        JSONObject params = new JSONObject();
        params.put("name",sceneCateName);
        String paramStr = params.toString();

        //发送请求
        Response response = null;
        try {
            response = HttpRequestsUtil.doPostWithString(url,header,paramStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }
}
