package cn.jishibrain.ai.sceneconfig.sceneconfiglist;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.sceneconfig.sceneconfiglist.SceneCreate;
import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.ExcelDataUtil;
import com.jishibrain.ai.utils.JdbcUtil;
import com.jishibrain.ai.utils.MapConvertStr;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunxiufang on 2020/5/28 17:27
 */
public class SceneCreateTestNormal {

    @Test(dataProvider = "creaSceDatas")
    public void test001_creaSce(String caseName,String jsonStr,String assertResValue,String assertMsgValue) throws SQLException {

        //获取场景类别id
        Connection connection = JdbcUtil.getConnection();
        Object[][] data = JdbcUtil.getData(connection,
                "SELECT id FROM t_scene_catagory where name = '接口自动化测试' and is_delete = '0' ORDER BY create_time DESC limit 0,1 ");
        String cateid = (String)data[0][0];

        //替换json参数中的cateid,获取formId
        JSONObject jsonparam = JSONObject.parseObject(jsonStr);
        jsonparam.replace("sceneCategory",cateid);
        String formId = jsonparam.getString("formId");
        String newJsonstr = jsonparam.toJSONString();

        //判断所用表单有没有被使用,如果被使用解除表单的关联关系
        ResultSet resultSetByQuery = JdbcUtil.getResultSetByQuery("SELECT * from t_mapping_scene_form  WHERE form_id = '"+formId+"' and is_delete = '0' ");

        if (resultSetByQuery.next()){
            JdbcUtil.executeUpdate(connection, "UPDATE t_mapping_scene_form SET is_delete = '1' WHERE form_id = '"+formId+"' and is_delete = '0' ");
        }

        SceneCreate sceneCreate = new SceneCreate();
        Response response = sceneCreate.addScene(newJsonstr);
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
    public Object[][] creaSceDatas() throws Exception {

        String file="src/main/resources/data/ApiTestData.xlsx";
        ExcelDataUtil excel = new ExcelDataUtil(file);
        Object[][] ob = excel.getTestData("新建场景（正常数据）");

        excel.close();
        return ob;

    }





}
