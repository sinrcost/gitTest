package cn.jishibrain.ai.sceneconfig.sceneconfiglist;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.sceneconfig.sceneconfiglist.SceneCreate;
import com.jishibrain.ai.actions.sceneconfig.sceneconfiglist.UnitCreate;
import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.JdbcUtil;
import com.jishibrain.ai.utils.MapConvertStr;
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
    public void test001_addUnit(String jsonStr,String assertResValue,String assertMsgValue,String sceneVersionId){
        UnitCreate unitCreate = new UnitCreate();
        Response response = unitCreate.addUnit(jsonStr,sceneVersionId);
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
    public Object[][] addUnitDatas(){

        Connection connection = JdbcUtil.getConnection();
        Object[][] data1 = JdbcUtil.getData(connection, "SELECT id FROM t_scene WHERE scene_name = '场景名称IDEA（使用院内表单）' AND is_delete = '0'");
        String sceneId = (String) data1[0][0];
        Object[][] data2 = JdbcUtil.getData(connection,"SELECT id FROM t_scene_version WHERE scene_id = '"+ sceneId +"'");
        String sceneVersionId = (String) data2[0][0];

        Object[][] data3 = JdbcUtil.getData(connection,"SELECT id FROM t_mapping_scene_form WHERE scene_name = '场景名称IDEA（使用院内表单）' AND is_delete = '0'");
        String relationId = (String) data3[0][0];

        //第一单元
        Map<String,Object> para1 = new HashMap<String, Object>();
        para1.put("askUnitNum",0);
        para1.put("sceneVersionId",sceneVersionId);
        para1.put("relationId",relationId);          //场景版本和表单关联关系
        para1.put("unitName","问候语");              //单元名称
        para1.put("commType",3);                     //	1表示电话,2表示微信
        para1.put("titles","");
        para1.put("id","");
        para1.put("modelid","");
        para1.put("modelname","");
        String jsonStr1 = MapConvertStr.getStrByMap(para1);

        //第二单元
        Map<String,Object> para2 = new HashMap<String, Object>();
        para2.put("askUnitNum",2);
        para2.put("sceneVersionId",sceneVersionId);
        para2.put("relationId",relationId);                  //场景版本和表单关联关系
        para2.put("unitName","身体调查");
        para2.put("commType",3);                     //	1表示电话,2表示微信
        para2.put("titles","f5ecc50d22cd40d58c2e3fd9834eb167");  //关联的题目id
        para2.put("id","");
        para2.put("modelid","");
        para2.put("modelname","");
        String jsonStr2 = MapConvertStr.getStrByMap(para2);



        Object[][] data = {
                {jsonStr1,"0","success",sceneVersionId},
                {jsonStr2,"0","success",sceneVersionId}
        };
        return data;
    }

}
