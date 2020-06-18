package cn.jishibrain.ai.sceneconfig.sceneconfiglist;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.sceneconfig.sceneconfiglist.ConversationAdd;
import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.ExcelDataUtil;
import com.jishibrain.ai.utils.MapConvertStr;
import net.sf.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunxiufang on 2020/4/8 10:02
 */
public class ConversationAddTest {

    @Test(dataProvider = "addConDatas")
    public void test001_addCon(String ceseName,String jsonStr,String assertResValue,String assertMsgValue){

        if(ceseName.equals("新增话术（文字+患者姓名+录音）")){
            RecordUpTest recordUpTest = new RecordUpTest();
            String data = recordUpTest.test001_upRecord();

            String jsonArrStr = "{\n" +
                    "\t\"text\": \"文字\"\n" +
                    "}, {\n" +
                    "\t\"column\": \"patientName\"\n" +
                    "},"+data;

            JSONArray jsonArray = new JSONArray();
            jsonArray.add(jsonArrStr);

            JSONObject jsonObjectPara = JSONObject.parseObject(jsonStr);
            jsonObjectPara.replace("conversationJson",jsonArray);

        }


        ConversationAdd conversationAdd = new ConversationAdd();
        Response response = conversationAdd.addCon(jsonStr);
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
    public Object[][] addConDatas() throws Exception {

        String file="src/main/resources/data/ApiTestData.xlsx";
        ExcelDataUtil excel = new ExcelDataUtil(file);
        Object[][] ob = excel.getTestData("新增话术");

        excel.close();
        return ob;
    }

}
