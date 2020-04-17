package cn.jishibrain.ai.AIkonwledge.intention;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.AIknowledge.intention.IntentionCategoryDele;
import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.ExcelDataUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by sunxiufang on 2020/4/2 15:14
 */
public class IntentionCategoryDeleTest {

    @Test(dataProvider = "deleteCateDatas")
    public void test001_deleteCate(String dataName,String cateName,String assertResValue,String assertMsgValue){
        IntentionCategoryDele intentionCategoryDele = new IntentionCategoryDele();
        Response response = intentionCategoryDele.deleteIntenCate(cateName);
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
    public Object[][] deleteCateDatas() throws Exception {
        String file="src/main/resources/data/ApiTestData.xlsx";
        ExcelDataUtil excel = new ExcelDataUtil(file);
        Object[][] ob = excel.getTestData("删除意图类别");

        excel.close();
        return ob;
    }
}
