package com.jishibrain.ai.actions.AIknowledge.intention;

import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.ApiURLUtil;
import com.jishibrain.ai.utils.HttpRequestsUtil;
import com.jishibrain.ai.utils.TokenUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunxiufang on 2020/4/2 9:27
 */
public class IntentionCategoryCreate {

    public Response createCategory(String categoryName){

        //新增意图类别的接口地址
        ApiURLUtil apiURLUtil = new ApiURLUtil();
        String url = apiURLUtil.getApiUrl("publicIntentionCategoryCreate.uri");

        //设置请求头
        Map<String,String> header = new HashMap<String,String>();
        TokenUtil tokenUtil = new TokenUtil();
        header.put("Authorization", tokenUtil.getTokenByConfigFile("host.properties","token"));

        //设置请求参数
        Map<String,String> param = new HashMap<String,String>();
        param.put("intentionTypeName", categoryName);

        //发送请求
        Response response = null;
        try {
            response = HttpRequestsUtil.doPostWithUrlParams(url, header, param);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
