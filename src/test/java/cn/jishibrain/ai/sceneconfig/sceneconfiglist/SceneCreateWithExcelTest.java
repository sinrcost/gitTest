package cn.jishibrain.ai.sceneconfig.sceneconfiglist;

import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.sceneconfig.sceneconfiglist.SceneCreateWithExcel;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by sunxiufang on 2020/5/27 11:37
 */
public class SceneCreateWithExcelTest {
    
    @Test
    public void test001_creSceExc(){

        String url = "http://192.168.3.68:2002/ai-web/ai/scene/excel2Scene?sceneName=Excel1&hospCode=&hospName=&pushContentType=1&formId=7c6bc37f932b4a8cb203b96f47ba858a&formRepeatId=283e435f6a6e46bb8df717887fefa724&pushContentName=xfsunsxf&voiceType=baidu_1&volume=15&speed=5&examine=true&sceneType=1&categoryId=5d1044c48c79ac2d6dc510c0&categoryName=1.1.0&automaticFrequency=0&automaticTime=5&automaticPostBack=false&enableSms=false&dynamic=false&autoFill=false&approach=1&importConversation=1";
        String filepath = "src/main/resources/data/cjexcel.xlsx";

        SceneCreateWithExcel sceneCreateWithExcel = new SceneCreateWithExcel();
        String response = sceneCreateWithExcel.addSceneExce(url, filepath);

        JSONObject jsonObject = JSONObject.parseObject(response);
        String msg = jsonObject.getString("msg");
        String rescode = jsonObject.getString("res");

        Assert.assertEquals(msg,"success");
        Assert.assertEquals(rescode,"0");

    }
    
    
}
