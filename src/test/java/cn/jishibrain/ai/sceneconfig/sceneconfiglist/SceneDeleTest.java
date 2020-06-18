package cn.jishibrain.ai.sceneconfig.sceneconfiglist;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.sceneconfig.sceneconfiglist.SceneDelete;
import com.jishibrain.ai.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by sunxiufang on 2020/4/7 11:25
 */
public class SceneDeleTest {

    @Test(dataProvider = "deleSceneDatas")
    public void test001_deleScene(String sceneName,String assertResValue,String assertMsgValue){
        SceneDelete sceneDelete = new SceneDelete();
        Response response = sceneDelete.deleScene(sceneName);
        int statusCode = response.getStatusCode();
        String responseStr = response.getResponseStr();

        //响应数据断言
        Assert.assertEquals(statusCode,200);
        JSONObject jsonObject = JSON.parseObject(responseStr);
        String actuslres = jsonObject.getString("res");
        String actualmsg = jsonObject.getString("msg");
        Assert.assertEquals(actuslres,assertResValue);
        Assert.assertEquals(actualmsg,assertMsgValue);

    }

    @DataProvider
    public Object[][] deleSceneDatas(){
        Object[][] data = {
                {"场景名称IDEA（使用云端表单）","0","success"},
                {"场景名称IDEA（使用院内表单）","0","success"},
                {"引用模板","0","success"}
        };
        return data;
    }

}
