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
 *  确认更新场景
 * Created by sunxiufang on 2020/4/8 16:37
 */
public class ConfirmUpdate {

    public Response conUpdate(String sceneVersionId){

        //确认更新地址
        ApiURLUtil apiURLUtil = new ApiURLUtil();
        String urlIni = apiURLUtil.getApiUrl("confirmUpdate.uri");
        String url = CreateUrlUtil.getUrlWithPara2(urlIni,sceneVersionId);
        //http://192.168.3.68:2002/ai-web/ai/scene/load/5e8bf61ecfc4190001cd533f

        //设置请求头
        TokenUtil tokenUtil = new TokenUtil();
        Map<String,String> header = new HashMap<String, String>();
        header.put("Authorization", tokenUtil.getTokenByConfigFile("host.properties","token"));

        //发送请求
        Response response = null;
        try {
            response = HttpRequestsUtil.doGet(url,header);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }


}
