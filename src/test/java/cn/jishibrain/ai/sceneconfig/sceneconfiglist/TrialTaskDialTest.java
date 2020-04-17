package cn.jishibrain.ai.sceneconfig.sceneconfiglist;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.sceneconfig.sceneconfiglist.TrialTaskDial;
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
 * Created by sunxiufang on 2020/4/8 17:58
 */
public class TrialTaskDialTest {

    @Test(dataProvider = "dialTrialTaskDatas")
    public void test001_dialTrialTask(String jsonStr,String assertResValue,String assertMsgValue){

        TrialTaskDial trialTaskDial = new TrialTaskDial();
        Response response = trialTaskDial.dialTrialTask(jsonStr);
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
    public Object[][] dialTrialTaskDatas(){

        Connection connection = JdbcUtil.getConnection();
        Object[][] data1 = JdbcUtil.getData(connection, "SELECT form_id from t_mapping_scene_form WHERE form_name = '测试环境院内表单xfsun接口专用（勿动勿用）' AND is_delete = '0'");
        String formId = (String) data1[0][0];
        Object[][] data2 = JdbcUtil.getData(connection, "SELECT id FROM t_scene WHERE scene_name = '场景名称IDEA（使用院内表单）' AND is_delete = '0' ORDER BY create_time DESC limit 0,1 ");
        String sceneId = (String) data2[0][0];
        Object[][] data3 = JdbcUtil.getData(connection,"SELECT id FROM t_scene_version WHERE scene_id = '"+ sceneId +"'");
        String sceneVersionId = (String) data3[0][0];

        Map<String,Object> paras = new HashMap<>();
        paras.put("formId",formId);
        paras.put("patientName","xfsun");
        paras.put("patientPhone","15988865122");
        paras.put("deptCode","1");
        paras.put("deptName","耳鼻喉科");
        paras.put("sceneVersionId",sceneVersionId);
        paras.put("hospCode","joinhealth166");

        String jsonStr = MapConvertStr.getStrByMap(paras);

        Object[][] data = {
            {jsonStr,"0","添加试用任务成功"}
        };
        return data;
    }

}
