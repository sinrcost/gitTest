package cn.jishibrain.ai.sceneconfig.sceneconfiglist;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.sceneconfig.sceneconfiglist.SceneCreate;
import com.jishibrain.ai.actions.sceneconfig.sceneconfiglist.UnitCreate;
import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.ExcelDataUtil;
import com.jishibrain.ai.utils.JdbcUtil;
import com.jishibrain.ai.utils.MapConvertStr;
import net.sf.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * 新增单元
 * Created by sunxiufang on 2020/4/7 13:04
 */
public class UnitCreateTest {

    @Test(dataProvider = "addUnitDatas")
    public void test001_addUnit(String casename,String jsonStr,String assertResValue,String assertMsgValue){

        Connection connection = JdbcUtil.getConnection();
        Object[][] data1 = JdbcUtil.getData(connection, "SELECT id FROM t_scene WHERE scene_name = '场景名称IDEA（使用院内表单）' AND is_delete = '0'");
        String sceneId = (String) data1[0][0];
        Object[][] data2 = JdbcUtil.getData(connection,"SELECT id FROM t_scene_version WHERE scene_id = '"+ sceneId +"'");
        String sceneVersionId = (String) data2[0][0];

        Object[][] data3 = JdbcUtil.getData(connection,"SELECT id FROM t_mapping_scene_form WHERE scene_name = '场景名称IDEA（使用院内表单）' AND is_delete = '0'");
        String relationId = (String) data3[0][0];

        //替换json参数中的sceneVersionId和relationId
        JSONObject jsonparam = JSONObject.parseObject(jsonStr);
        jsonparam.replace("sceneVersionId",sceneVersionId);
        jsonparam.replace("relationId",relationId);
        String newJsonstr = jsonparam.toJSONString();


        UnitCreate unitCreate = new UnitCreate();
        Response response = unitCreate.addUnit(newJsonstr,sceneVersionId);
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
    public Object[][] addUnitDatas() throws Exception {

        String file="src/main/resources/data/ApiTestData.xlsx";
        ExcelDataUtil excel = new ExcelDataUtil(file);
        Object[][] ob = excel.getTestData("新建单元");

        excel.close();
        return ob;

    }

}
