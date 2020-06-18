package com.jishibrain.ai.actions.sceneconfig.sceneconfiglist;

import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.ApiURLUtil;
import com.jishibrain.ai.utils.HttpRequestsUtil;
import com.jishibrain.ai.utils.TokenUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 保存场景配置页面表单的默认答案
 * Created by sunxiufang on 2020/5/27 16:04
 */
public class SaveAnswerDef {

    public Response ansDef(String jsonStr){

        //保存默认答案地址
        ApiURLUtil apiURLUtil = new ApiURLUtil();
        String url = apiURLUtil.getApiUrl("answerDefAdd.uri");

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
