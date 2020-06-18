package cn.jishibrain.ai.AIkonwledge.intention;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.AIknowledge.intention.IntentionCategoryCreate;
import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.ExcelDataUtil;
import com.jishibrain.ai.utils.JdbcUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.xml.soap.SAAJResult;
import java.sql.Connection;

/**
 * Created by sunxiufang on 2020/4/2 9:38
 */
public class IntentionCategoryCreateTest {

    @Test(dataProvider = "intenCateDatas")
    public void test001_intentionCategoryCreate(String casename,String intenCateName,String assertResValue,String assertMsgValue){
        IntentionCategoryCreate intentionCategoryCreate = new IntentionCategoryCreate();
        Response categoryRes = intentionCategoryCreate.createCategory(intenCateName);
        int statusCode = categoryRes.getStatusCode();
        String responseStr = categoryRes.getResponseStr();

        //响应数据断言
        Assert.assertEquals(statusCode,200);
        JSONObject jsonObject = JSON.parseObject(responseStr);
        String actuslres = jsonObject.getString("res");
        String actualmsg = jsonObject.getString("msg");
        Assert.assertEquals(actuslres,assertResValue);
        Assert.assertEquals(actualmsg,assertMsgValue);

        //数据库断言
        if(actuslres.equals("0") && actualmsg.equals("success")){
            Connection connection = JdbcUtil.getConnection();
            Object[][] data = JdbcUtil.getData(connection,
                    "SELECT intention_type_name from t_intention_category ORDER BY create_time DESC limit 0,1");
            Assert.assertEquals(data[0][0],intenCateName);

        }


        //清理数据库数据



    }

    @DataProvider
    public Object[][] intenCateDatas() throws Exception {
        String file="src/main/resources/data/ApiTestData.xlsx";
        ExcelDataUtil excel = new ExcelDataUtil(file);
        Object[][] ob = excel.getTestData("创建意图类别");

        excel.close();
        return ob;
    }

}
