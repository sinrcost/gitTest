package cn.jishibrain.ai.sceneconfig.sceneconfiglist;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.sceneconfig.sceneconfiglist.UnitPreservation;
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
 * 保存单元
 * Created by sunxiufang on 2020/4/8 11:11
 */
public class UnitPreservationTest {

    @Test(dataProvider = "preUnitDatas")
    public void test001_preUnit(String caseName,String jsonStr,String assertResValue,String assertMsgValue){

        Connection connection = JdbcUtil.getConnection();
        Object[][] scene = JdbcUtil.getData(connection, "SELECT id FROM t_scene WHERE scene_name = '场景名称IDEA（使用院内表单）' AND is_delete = '0'");
        String sceneId = (String) scene[0][0];
        Object[][] sceneversion = JdbcUtil.getData(connection,"SELECT id FROM t_scene_version WHERE scene_id = '"+ sceneId +"'");
        String sceneVersionId = (String) sceneversion[0][0];

        JSONObject jsonObjectParam = JSONObject.parseObject(jsonStr);
        jsonObjectParam.getString("");

        for(int i=1;i<=6;i++){
            //第i单元问题话术
            Object[][] conver1 = JdbcUtil.getData(connection, "SELECT id,conversation_name from t_conversation WHERE  conversation_name = '现在方便做个回访调查吗' AND is_delete = '0' ORDER BY create_time DESC limit 0,1");
            String conversationId1 = (String) conver1[0][0];
            String conversationName1 = (String) conver1[0][1];
            //第i单元unitId
            Object[][] unit1 = JdbcUtil.getData(connection,
                    "SELECT id from t_ask_unit WHERE unit_name = '现在方便做个回访调查吗' ORDER BY create_time DESC limit 0,1");
            String unitId1 = (String)unit1[0][0];

        }

        UnitPreservation unitPreservation = new UnitPreservation();
        Response response = unitPreservation.preservUnit(jsonStr, sceneVersionId);
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
    public Object[][] preUnitDatas() throws Exception {

        String file="src/main/resources/data/ApiTestData.xlsx";
        ExcelDataUtil excel = new ExcelDataUtil(file);
        Object[][] ob = excel.getTestData("保存单元");

        excel.close();
        return ob;

    }

}
