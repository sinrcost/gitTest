package com.jishibrain.ai.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jishibrain.ai.actions.login.Login;
import com.jishibrain.ai.response.Response;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by sunxiufang on 2020/4/2 9:32
 */
public class TokenUtil {

    /**
     * 通过登录获取token
     * @return
     */
    public String getTokenByLogin(){
        String token = null;

        Login login = new Login();
        Response response = login.login("xfsun", "123");
        String responseStr = response.getResponseStr();
        //获取响应中的token值
        JsonObject jsonObject = new JsonParser().parse(responseStr).getAsJsonObject();
        JsonObject content = jsonObject.getAsJsonObject("data");
        token = "Bearer"+content.get("token").getAsString();

        System.out.println(token);
        return token;
    }




    public static String getToken001(){

        String token = null;
        //创建POST请求
        String url = "http://192.168.3.68:2002/auth/login";
        HttpPost post = new HttpPost(url);

        //接口参数
        JSONObject json = new JSONObject();
        json.put("userName", "xfsun");
        json.put("userPwd", "123");
        StringEntity entity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);
        entity.setContentType("application/json");

        //注入请求参数
        post.setEntity(entity);

        //创建客户端
        CloseableHttpClient client = HttpClients.createDefault();

        CloseableHttpResponse res = null;
        try {
            //发送请求
            res = client.execute(post);
            //获取响应
            HttpEntity httpEntity = res.getEntity();
            String strEntity = EntityUtils.toString(httpEntity);
            //获取响应中的token值
            JsonObject jsonObject = new JsonParser().parse(strEntity).getAsJsonObject();
            JsonObject content = jsonObject.getAsJsonObject("data");
            token = "Bearer"+content.get("token").getAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                res.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return token;
    }

    /**
     * 通过配置文件获取token
     * @param filename
     * @param paraname
     * @return
     */
    public String getTokenByConfigFile(String filename,String paraname){
        PropertyUtil propertyUtil = new PropertyUtil();
        String token = propertyUtil.readPropertiesPara(filename, paraname);
        return token;
    }

}
