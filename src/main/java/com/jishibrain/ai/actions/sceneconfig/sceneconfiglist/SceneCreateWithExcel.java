package com.jishibrain.ai.actions.sceneconfig.sceneconfiglist;

import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.*;
import okhttp3.ResponseBody;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用Excel文档生成场景
 * Created by sunxiufang on 2020/5/19 18:10
 */
public class SceneCreateWithExcel {

    public String addSceneExce(String url,String filepath){

        //设置请求头
        TokenUtil tokenUtil = new TokenUtil();
        Map<String,String> header = new HashMap<String, String>();
        header.put("Authorization", tokenUtil.getTokenByConfigFile("host.properties","token"));

        //发送请求
        File file = new File("src/main/resources/data/cjexcel.xlsx");
        String res = null;
        try {
            res = CrazyHttpClient.sendUpload(url,header,filepath);
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }


}
