package com.jishibrain.ai.actions.sceneconfig.sceneconfiglist;

import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunxiufang on 2020/4/7 12:48
 */
public class UnitCreate {

    public Response addUnit(String jsonStr,String sceneVersionId){

        //新建单元地址
        ApiURLUtil apiURLUtil = new ApiURLUtil();
        String urlIni = apiURLUtil.getApiUrl("unitAdd.uri");
        Map<String,String> pa = new HashMap<String, String>();
        pa.put("sceneVersionId",sceneVersionId);
        String url = CreateUrlUtil.getUrlWithPara1(urlIni, pa);
        //http://192.168.3.68:2002/ai-web/ai/ask-unit/bind?sceneVersionId=5e8bf61ecfc4190001cd533f

        //设置请求头
        TokenUtil tokenUtil = new TokenUtil();
        Map<String,String> header = new HashMap<String, String>();
        header.put("Authorization", tokenUtil.getTokenByConfigFile("host.properties","token"));


        //发送请求
        Response response = null;
        try {
            response = HttpRequestsUtil.doPostWithString(url, header, jsonStr);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
