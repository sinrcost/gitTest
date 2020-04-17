package com.jishibrain.ai.actions.login;

import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.ApiURLUtil;
import com.jishibrain.ai.utils.HttpRequestsUtil;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunxiufang on 2020/4/1 15:32
 */
public class Login {

    public Response login(String username,String password){
        //登录的接口地址
        ApiURLUtil apiURLUtil = new ApiURLUtil();
        String url = apiURLUtil.getApiUrl("login.uri");

        //设置请求头
        Map<String,String> header = new HashMap<String, String>();
        header.put("Content-Type","application/json;charset=UTF-8");

        //设置请求参数
         JSONObject params = new JSONObject();
         params.put("userName",username);
         params.put("userPwd",password);
         String paramStr = params.toString();

        //发送请求
        Response response = null;
        try {
            response = HttpRequestsUtil.doPostWithString(url, header, paramStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
