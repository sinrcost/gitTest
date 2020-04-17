package com.jishibrain.ai.actions.AIknowledge.intention;

import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunxiufang on 2020/4/2 12:55
 */
public class IntentionCategoryEdit {

    public Response editIntenCate(String catenameBefore,String catenameAfter){

        //编辑意图类别的接口地址
        ApiURLUtil apiURLUtil = new ApiURLUtil();
        String urlIni = apiURLUtil.getApiUrl("publicIntentionCategoryEdit.uri");
        Connection connection = JdbcUtil.getConnection();
        Object[][] data = JdbcUtil.getData(connection,
                "SELECT id from t_intention_category WHERE intention_type_name = '"+ catenameBefore +"' AND is_delete = '0'");
        System.out.println((String)data[0][0]);
        String url = CreateUrlUtil.getUrlWithPara2(urlIni,(String)data[0][0]);

        //设置请求头
        Map<String,String> header = new HashMap<String,String>();
        TokenUtil tokenUtil = new TokenUtil();
        header.put("Authorization", tokenUtil.getTokenByConfigFile("host.properties","token"));

        //设置请求参数
         JSONObject json = new JSONObject();
         json.put("intentionTypeName", catenameAfter);
         String jsonStr = json.toString();

        //执行请求
        Response response = null;
        try {
            response = HttpRequestsUtil.doPutWithString(url,header,jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }



}
