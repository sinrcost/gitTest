package cn.jishibrain.ai.sceneconfig.sceneconfiglist;

import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.sceneconfig.sceneconfiglist.SceneCreateWithExcel;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by sunxiufang on 2020/5/29 15:16
 */
public class RecordUpTest {

   // @Test
    public String test001_upRecord(){
        String url = "http://192.168.3.68:2002/ai-web/ai/conversation/voice";
        String filepath = "src/main/resources/data/luyinfile.mp3";

        SceneCreateWithExcel sceneCreateWithExcel = new SceneCreateWithExcel();
        String response = sceneCreateWithExcel.addSceneExce(url, filepath);

        JSONObject jsonObject = JSONObject.parseObject(response);
        String msg = jsonObject.getString("msg");
        String rescode = jsonObject.getString("res");

        Assert.assertEquals(msg,"success");
        Assert.assertEquals(rescode,"0");

        String data = jsonObject.getString("data");
        System.out.println("data的数据："+data);
        return data;
    }


}
