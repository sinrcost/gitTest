package com.jishibrain.ai.actions.sceneconfig.sceneconfiglist;

import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.ApiURLUtil;
import com.jishibrain.ai.utils.HttpRequestsUtil;
import com.jishibrain.ai.utils.JdbcUtil;
import com.jishibrain.ai.utils.TokenUtil;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * 删除场景
 * Created by sunxiufang on 2020/4/7 10:10
 */
public class SceneDelete {

    public Response deleScene(String sceneName){

        //删除场景地址
        ApiURLUtil apiURLUtil = new ApiURLUtil();
        String url = apiURLUtil.getApiUrl("sceneDelete.uri");

        //设置请求头
        TokenUtil tokenUtil = new TokenUtil();
        Map<String,String> header = new HashMap<String, String>();
        header.put("Authorization", tokenUtil.getTokenByConfigFile("host.properties","token"));

        //设置请求参数
        Connection connection = JdbcUtil.getConnection();
        Object[][] data = JdbcUtil.getData(connection, "SELECT id FROM t_scene WHERE scene_name = '" + sceneName + "' AND is_delete = '0'");
        Map<String,String> para = new HashMap<String, String>();
        para.put("id",(String)data[0][0]);

        //发送请求
        Response response = null;
        try {
            response = HttpRequestsUtil.doGetWithParams(url,header,para);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }

}
