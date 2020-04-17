package com.jishibrain.ai.actions.AIknowledge.intention;

import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunxiufang on 2020/4/2 15:09
 */
public class IntentionCategoryDele {

    public Response deleteIntenCate(String catename){

        //删除意图类别的接口地址
        ApiURLUtil apiURLUtil = new ApiURLUtil();
        String urlIni = apiURLUtil.getApiUrl("publicIntentionCategoryDelete.uri");
        Connection connection = JdbcUtil.getConnection();
        Object[][] data = JdbcUtil.getData(connection,
                "SELECT id from t_intention_category WHERE intention_type_name = '"+ catename +"' AND is_delete = '0'");

        String url = CreateUrlUtil.getUrlWithPara2(urlIni,(String)data[0][0]);

        //设置请求头
        TokenUtil tokenUtil = new TokenUtil();
        Map<String,String> header = new HashMap<String,String>();
        header.put("Authorization", tokenUtil.getTokenByConfigFile("host.properties","token"));

        //发送请求
        Response response = null;
        try {
            response = HttpRequestsUtil.delete(url,header);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


}
