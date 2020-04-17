package com.jishibrain.ai.actions.sceneconfig.sceneconfiglist;

import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunxiufang on 2020/4/3 9:48
 */
public class SceneCategoryDele {

    public Response deleSceneCate(String sceneCateName){

        //http://192.168.3.68:2002/ai-web/ai/scene/deleteSceneCategory?id=5e8694aacfc41900014570ce
        //删除场景类别地址
        ApiURLUtil apiURLUtil = new ApiURLUtil();
        String urlIni = apiURLUtil.getApiUrl("sceneCategoryDelete.uri");
        Connection connection = JdbcUtil.getConnection();
        Object[][] data = JdbcUtil.getData(connection,
                "SELECT id FROM t_scene_catagory where name = '" + sceneCateName + "' and is_delete = '0'");
        String id = (String)data[0][0];
        Map<String,String> para = new HashMap<String, String>();
        para.put("id",id);
        String url = CreateUrlUtil.getUrlWithPara1(urlIni, para);

        //设置请求头
        TokenUtil tokenUtil = new TokenUtil();
        Map<String,String> header = new HashMap<String, String>();
        header.put("Authorization", tokenUtil.getTokenByConfigFile("host.properties","token"));

        //发送请求
        Response response = null;
        try {
            response = HttpRequestsUtil.delete(url, header);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
