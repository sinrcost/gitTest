package com.jishibrain.ai.actions.sceneconfig.sceneconfiglist;

import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.CrazyHttpClient;
import com.jishibrain.ai.utils.TokenUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunxiufang on 2020/5/29 15:03
 */
public class RecordUp {

    public String upRecord(String url, String filepath){

        //设置请求头
        TokenUtil tokenUtil = new TokenUtil();
        Map<String,String> header = new HashMap<String, String>();
        header.put("Authorization", tokenUtil.getTokenByConfigFile("host.properties","token"));

        //发送请求
        String res = null;
        try {
            res = CrazyHttpClient.sendUpload(url,header,filepath);
           // System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;

    }

}
