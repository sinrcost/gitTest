package com.jishibrain.ai.actions.sceneconfig.sceneconfiglist;

import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.ApiURLUtil;
import com.jishibrain.ai.utils.CreateUrlUtil;
import com.jishibrain.ai.utils.HttpRequestsUtil;
import com.jishibrain.ai.utils.TokenUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 保存单元
 * Created by sunxiufang on 2020/4/8 10:55
 */
public class UnitPreservation {

    public Response preservUnit(String jsonStr,String sceneVersionId){

        //保存单元地址
        ApiURLUtil apiURLUtil = new ApiURLUtil();
        String urlIni = apiURLUtil.getApiUrl("unitPreservation.uri");
        Map<String,String> pa = new HashMap<String, String>();
        pa.put("sceneVersionId",sceneVersionId);
        String url = CreateUrlUtil.getUrlWithPara1(urlIni, pa);
        //http://192.168.3.68:2002/ai-web/ai/ask-unit/phone?sceneVersionId=5e8bf61ecfc4190001cd533f

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
