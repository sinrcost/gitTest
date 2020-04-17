package cn.jishibrain.ai.sceneconfig.sceneconfiglist;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.sceneconfig.sceneconfiglist.SceneCategoryDele;
import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.ExcelDataUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by sunxiufang on 2020/4/3 10:08
 */
public class SceneCateDeleTest {

    @Test(dataProvider = "deleSceneCateDatas")
    public void deleSceneCate(String dataName,String cateName,String assertResValue,String assertMsgValue){
        SceneCategoryDele dele = new SceneCategoryDele();
        Response response = dele.deleSceneCate(cateName);
        System.out.println(response);
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
    public Object[][] deleSceneCateDatas() throws Exception {
        String file="src/main/resources/data/ApiTestData.xlsx";
        ExcelDataUtil excel = new ExcelDataUtil(file);
        Object[][] ob = excel.getTestData("删除场景类别");

        excel.close();
        return ob;

    }

}
