package com.jishibrain.ai.actions.robotmanage;

import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.ApiURLUtil;
import com.jishibrain.ai.utils.HttpRequestsUtil;
import com.jishibrain.ai.utils.TokenUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunxiufang on 2020/4/11 15:52
 */
public class ConfigRobotForScene {

    public Response configRobot(String jsonStr){

        //机器人配置场景地址
        ApiURLUtil apiURLUtil = new ApiURLUtil();
        String url = apiURLUtil.getApiUrl("robotAddScene.uri");
        //http://192.168.3.68:2002/ai-web/ai/robots/5e9174fdcfc41900016f18cd

        //设置请求头
        TokenUtil tokenUtil = new TokenUtil();
        Map<String,String> header = new HashMap<String, String>();
        header.put("Authorization", tokenUtil.getTokenByConfigFile("host.properties","token"));


        //发送请求
        Response response = null;
        try {
            response = HttpRequestsUtil.doPutWithString(url,header,jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

}
