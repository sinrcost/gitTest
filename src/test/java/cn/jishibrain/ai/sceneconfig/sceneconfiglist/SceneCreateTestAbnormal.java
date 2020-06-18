package cn.jishibrain.ai.sceneconfig.sceneconfiglist;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.sceneconfig.sceneconfiglist.SceneCreate;
import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.ExcelDataUtil;
import com.jishibrain.ai.utils.JdbcUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by sunxiufang on 2020/5/28 17:28
 */
public class SceneCreateTestAbnormal {

    @Test(dataProvider = "creaSceWithAbnormalData")
    public void test002_creaSceWithAbnormal(String caseName,String jsonStr,String assertResValue,String assertMsgValue) throws SQLException {


        //获取场景类别id
        Connection connection = JdbcUtil.getConnection();
        Object[][] data = JdbcUtil.getData(connection,
                "SELECT id FROM t_scene_catagory where name = '接口自动化测试' and is_delete = '0' ORDER BY create_time DESC limit 0,1 ");
        String cateid = (String)data[0][0];

        //替换json参数中的cateid
        JSONObject jsonparam = JSONObject.parseObject(jsonStr);
        jsonparam.replace("sceneCategory",cateid);
        String newJsonstr = jsonparam.toJSONString();

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
    public Object[][] creaSceWithAbnormalData() throws Exception {

        String file="src/main/resources/data/ApiTestData.xlsx";
        ExcelDataUtil excel = new ExcelDataUtil(file);
        Object[][] ob = excel.getTestData("新建场景（异常数据）");

        excel.close();
        return ob;


    }




}
