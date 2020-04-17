package cn.jishibrain.ai.robotmanage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.robotmanage.ConfigRobotForScene;
import com.jishibrain.ai.response.Response;
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
 * Created by sunxiufang on 2020/4/11 16:00
 */
public class ConfigRobotForSceneTest {

    @Test(dataProvider = "configRobotData")
    public void test001_configRobot(String jsonStr,String assertResValue,String assertMsgValue){
        ConfigRobotForScene configRobotForScene = new ConfigRobotForScene();
        Response response = configRobotForScene.configRobot(jsonStr);
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
    public Object[][] configRobotData(){

        //  [{"workBeginTime":"08:00:00","workEndTime":"23:00:00"}]     ["5e8bf61ecfc4190001cd533e"]
        //机器人工作时间
        String workTimeJson =  "[{workBeginTime:'08:00:00',workEndTime:'23:00:00'}]";
        JSONArray workTime = JSONArray.fromObject(workTimeJson);
        //场景
        Connection connection = JdbcUtil.getConnection();
        Object[][] dataObj = JdbcUtil.getData(connection, "SELECT id FROM t_scene WHERE scene_name = '场景名称IDEA（使用院内表单）' AND is_delete = '0' ORDER BY create_time DESC limit 0,1 ");
        String sceneId = (String) dataObj[0][0];
        String sceneStr = "["+sceneId+"]";
        JSONArray scene = JSONArray.fromObject(sceneStr);

        Map<String,Object> paras = new HashMap<>();
        paras.put("robotName","接口测试场景专用机器人");
        paras.put("robotDesc","机器人描述");
        paras.put("workingTimes",workTime);
        paras.put("scenes",scene);
        paras.put("gatewayKey","gw1");
        paras.put("hospCode","joinhealth166");
        paras.put("speechType","1");

        String jsonStr = MapConvertStr.getStrByMap(paras);

        Object[][] data = {
                {jsonStr,"0","success"}
        };

        return data;
    }

}
