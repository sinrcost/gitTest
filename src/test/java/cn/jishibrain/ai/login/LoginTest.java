package cn.jishibrain.ai.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.login.Login;
import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.ExcelDataUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by sunxiufang on 2020/4/1 16:32
 */
public class LoginTest {

    @Test(dataProvider = "loginDatas")
    public void test001_login(String dataname,String uname,String pword,String assertValue){
        Login logintest = new Login();
        Response loginRes = logintest.login(uname,pword);
        int actualCode = loginRes.getStatusCode();
        String actualBody = loginRes.getResponseStr();

        //断言
        Assert.assertEquals(actualCode,200);
        JSONObject jsonObject = JSON.parseObject(actualBody);
        String actualmsg = jsonObject.getString("msg");
        Assert.assertEquals(actualmsg,assertValue);

    }

    @DataProvider
    public Object[][] loginDatas() throws Exception {
        String file="src/main/resources/data/ApiTestData.xlsx";
        ExcelDataUtil excel = new ExcelDataUtil(file);
        Object[][] ob = excel.getTestData("登录");

        excel.close();
        return ob;
    }

}
