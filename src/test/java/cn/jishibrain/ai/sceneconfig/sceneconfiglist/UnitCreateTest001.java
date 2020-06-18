package cn.jishibrain.ai.sceneconfig.sceneconfiglist;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.sceneconfig.sceneconfiglist.UnitCreate;
import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.JdbcUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Connection;

/**
 * Created by sunxiufang on 2020/5/29 10:13
 */
public class UnitCreateTest001 {



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
    public Object[][] addUnitDatas() throws Exception {

        Connection connection = JdbcUtil.getConnection();
        Object[][] data1 = JdbcUtil.getData(connection, "SELECT id FROM t_scene WHERE scene_name = '场景名称IDEA（使用院内表单）' AND is_delete = '0'");
        String sceneId = (String) data1[0][0];
        Object[][] data2 = JdbcUtil.getData(connection,"SELECT id FROM t_scene_version WHERE scene_id = '"+ sceneId +"'");
        String sceneVersionId = (String) data2[0][0];

        Object[][] data3 = JdbcUtil.getData(connection,"SELECT id FROM t_mapping_scene_form WHERE scene_name = '场景名称IDEA（使用院内表单）' AND is_delete = '0'");
        String relationId = (String) data3[0][0];




        //第一单元
/**
 {"askUnitNum":0,"sceneVersionId":"5ece0d9f3252c00001d1305a","relationId":"5ece0d9f3252c00001d1305b","unitName":"现在方便做个回访调查吗",
 "title":{"titleId":null,"optionId":null,"questionTypeForm":null},
 "id":"","commType":1,"modelid":"","modelname":""}

 */



        String jsonstry1 ="{\"askUnitNum\":0,\"sceneVersionId\":\"5ece18d33252c00001e934ea\",\"relationId\":\"5ece18d33252c00001e934eb\",\"unitName\":\"现在方便做个回访调查吗\",\"title\":{\"titleId\":null,\"optionId\":null,\"questionTypeForm\":null},\"id\":\"\",\"commType\":1,\"modelid\":\"\",\"modelname\":\"\"}";
        JSONObject json1 = JSONObject.parseObject(jsonstry1);
        json1.replace("sceneVersionId", sceneVersionId);
        json1.replace("relationId", relationId);
        String jsonStr1 = json1.toString();


        //第二单元-身体调查（单选题）
        String jsonstry2 = "{\"askUnitNum\":1,\"sceneVersionId\":\"5ece18d33252c00001e934ea\",\"relationId\":\"5ece18d33252c00001e934eb\",\"unitName\":\"就诊后身体怎么样\",\"title\":{\"titleId\":\"f5ecc50d22cd40d58c2e3fd9834eb167\",\"optionId\":null,\"questionTypeForm\":2},\"id\":\"\",\"commType\":1,\"modelid\":\"\",\"modelname\":\"\"}";

        JSONObject json2 = JSONObject.parseObject(jsonstry2);
        json2.replace("sceneVersionId", sceneVersionId);
        json2.replace("relationId", relationId);
        String jsonStr2 = json2.toString();

        /**
         {"askUnitNum":3,"sceneVersionId":"5ecde3dc3252c00001d12cd9","relationId":"5ecde3dc3252c00001d12cda", "unitName":"就诊后身体怎么样",
         "title":{"titleId":"f5ecc50d22cd40d58c2e3fd9834eb167","optionId":null,"questionTypeForm":2},
         "id":"","commType":1,"modelid":"","modelname":""}

         */

        //第三单元-医生态度（单选题）
        String jsonstry3 = "{\"askUnitNum\":2,\"sceneVersionId\":\"5ece18d33252c00001e934ea\",\"relationId\":\"5ece18d33252c00001e934eb\",\"unitName\":\"医生态度怎么样\",\"title\":{\"titleId\":\"d0fa1de9e05d4d39a7f0ead4f2bc9bf1\",\"optionId\":null,\"questionTypeForm\":2},\"id\":\"\",\"commType\":1,\"modelid\":\"\",\"modelname\":\"\"}";

        JSONObject json3 = JSONObject.parseObject(jsonstry3);
        json3.replace("sceneVersionId", sceneVersionId);
        json3.replace("relationId", relationId);
        String jsonStr3 = json3.toString();

        //第四单元-早餐调查（多选题）
        String jsonstry4 = "{\"askUnitNum\":3,\"sceneVersionId\":\"5ece18d33252c00001e934ea\",\"relationId\":\"5ece18d33252c00001e934eb\",\"unitName\":\"早餐调查\",\"title\":{\"titleId\":\"11a6b660ba2f4423a9e6e9242aa9a067\",\"optionId\":null,\"questionTypeForm\":3},\"id\":\"\",\"commType\":1,\"modelid\":\"\",\"modelname\":\"\"}";

        JSONObject json4 = JSONObject.parseObject(jsonstry4);
        json4.replace("sceneVersionId", sceneVersionId);
        json4.replace("relationId", relationId);
        String jsonStr4 = json4.toString();


        //第五单元-满意度调查（矩阵题）
        String jsonstry5 = "{\"askUnitNum\":4,\"sceneVersionId\":\"5ece18d33252c00001e934ea\",\"relationId\":\"5ece18d33252c00001e934eb\",\"unitName\":\"满意度调查\",\"title\":{\"titleId\":\"7dc89810046b4f77a68a7a1249a2c600\",\"optionId\":\"e41d96d887014afcb08f35daa91cdf94\",\"questionTypeForm\":4},\"id\":\"\",\"commType\":1,\"modelid\":\"\",\"modelname\":\"\"}";
        JSONObject json5 = JSONObject.parseObject(jsonstry5);
        json5.replace("sceneVersionId", sceneVersionId);
        json5.replace("relationId", relationId);
        String jsonStr5 = json5.toString();

        //第六单元-医院意见（填空题）
        String jsonstry6 = "{\"askUnitNum\":5,\"sceneVersionId\":\"5ece18d33252c00001e934ea\",\"relationId\":\"5ece18d33252c00001e934eb\",\"unitName\":\"医院意见\",\"title\":{\"titleId\":\"258ac7891a19403591d97b8e343024f3\",\"optionId\":\"e41d96d887014afcb08f35daa91cdf94\",\"questionTypeForm\":1},\"id\":\"\",\"commType\":1,\"modelid\":\"\",\"modelname\":\"\"}";
        JSONObject json6 = JSONObject.parseObject(jsonstry6);
        json6.replace("sceneVersionId", sceneVersionId);
        json6.replace("relationId", relationId);
        String jsonStr6 = json6.toString();


        Object[][] data = {
                {jsonStr1,"0","success",sceneVersionId},
                {jsonStr2,"0","success",sceneVersionId},
                {jsonStr3,"0","success",sceneVersionId},
                {jsonStr4,"0","success",sceneVersionId},
                {jsonStr5,"0","success",sceneVersionId},
                {jsonStr6,"0","success",sceneVersionId}

        };
        return data;


    }


}
