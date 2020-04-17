package cn.jishibrain.ai.AIkonwledge.intention;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.AIknowledge.intention.IntentionCategoryEdit;
import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.ExcelDataUtil;
import com.jishibrain.ai.utils.JdbcUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by sunxiufang on 2020/4/2 14:03
 */
public class IntentionCategoryEditTest {

    @Test(dataProvider = "editCate")
    public void test001_editCate(String dataname,String nameBefore,String nameAfter,String assertResValue,String assertMsgValue){
        IntentionCategoryEdit intentionCategoryEdit = new IntentionCategoryEdit();
        Response response = intentionCategoryEdit.editIntenCate(nameBefore, nameAfter);
        int statusCode = response.getStatusCode();
        String responseStr = response.getResponseStr();

        //响应数据断言
        Assert.assertEquals(statusCode,200);
        JSONObject jsonObject = JSON.parseObject(responseStr);
        String actuslres = jsonObject.getString("res");
        String actualmsg = jsonObject.getString("msg");
        Assert.assertEquals(actuslres,assertResValue);
        Assert.assertEquals(actualmsg,assertMsgValue);

        //还原编辑的数据
        //UPDATE t_intention_category SET intention_type_name = '新类别002#￥%'  WHERE intention_type_name = '新类别002#￥%bs'
        String sqlreduction = "UPDATE t_intention_category SET intention_type_name = '"+ nameBefore +"' WHERE intention_type_name = '"
                + nameAfter+ "'AND is_delete = '0'";
        if(actuslres.equals("0") && actualmsg.equals("success")){
            Connection connection = JdbcUtil.getConnection();
            try {
                int i = JdbcUtil.executeUpdate(connection, sqlreduction);
                Assert.assertEquals(i,1);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    @DataProvider
    public Object[][] editCate() throws Exception {
        String file="src/main/resources/data/ApiTestData.xlsx";
        ExcelDataUtil excel = new ExcelDataUtil(file);
        Object[][] ob = excel.getTestData("编辑意图类别");

        excel.close();
        return ob;
    }

}
