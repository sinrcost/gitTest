package cn.jishibrain.ai.sceneconfig.sceneconfiglist;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.sceneconfig.sceneconfiglist.ConfirmUpdate;
import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.JdbcUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Connection;

/**
 * Created by sunxiufang on 2020/4/8 16:53
 */
public class ConfirmUpdateTest {

    @Test(dataProvider = "conUpdateDatas")
    public void test001_conUpdate(String sceneVersionId,String assertResValue,String assertMsgValue){
        ConfirmUpdate confirmUpdate = new ConfirmUpdate();
        Response response = confirmUpdate.conUpdate(sceneVersionId);
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
    public Object[][] conUpdateDatas(){

        Connection connection = JdbcUtil.getConnection();
        Object[][] data1 = JdbcUtil.getData(connection, "SELECT id FROM t_scene WHERE scene_name = '场景名称IDEA（使用院内表单）' AND is_delete = '0' ORDER BY create_time DESC limit 0,1");
        String sceneId = (String) data1[0][0];
        Object[][] data2 = JdbcUtil.getData(connection,"SELECT id FROM t_scene_version WHERE scene_id = '"+ sceneId +"'");
        String sceneVersionId = (String) data2[0][0];

        Object[][] data = {
                {sceneVersionId,"0","success"}
        };

        return data;
    }

}
