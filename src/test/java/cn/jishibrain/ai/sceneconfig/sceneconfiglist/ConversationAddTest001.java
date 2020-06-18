package cn.jishibrain.ai.sceneconfig.sceneconfiglist;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.sceneconfig.sceneconfiglist.ConversationAdd;
import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.MapConvertStr;
import net.sf.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunxiufang on 2020/5/29 12:01
 */
public class ConversationAddTest001 {


    @Test(dataProvider = "addConDatas")
    public void test001_addCon(String jsonStr,String assertResValue,String assertMsgValue){
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
    public Object[][] addConDatas(){

        //第一单元问题话术
        String conversationJson1 = "[{text: '现在方便做个回访调查吗'}," +
                "{column: 'patientName'}, " +
                "{column: 'age'}]";
        JSONArray jsonArray1 = JSONArray.fromObject(conversationJson1);

        Map<String,Object> paras1 = new HashMap<>();
        paras1.put("conversationName","现在方便做个回访调查吗");  //话术名称
        paras1.put("conversationDesc","现在方便做个回访调查吗");  //话术描述
        paras1.put("conversationType",1);                        //话术类型-1表示问题话术,2表示异常话术,3表示补充话术
        paras1.put("breakFlag",1);                               //是否打断	0:可以打断，1：不能打断
        paras1.put("responseTime","0.75");   //响应时间
        paras1.put("priority",0);            //话术优先级 默认0：公共话术优先 1：补充话术优先
        paras1.put("commType",1);            //通信类型 是不是问答单元的 1 是问答单元话术
        paras1.put("conversationJson",jsonArray1);  //话术内容

        String jsonStr1 = MapConvertStr.getStrByMap(paras1);


        //第二单元问题话术
        String conversationJson2 = "[{text: '现在身体怎么样呢'}," +
                "{column: 'patientName'}]";
        JSONArray jsonArray2 = JSONArray.fromObject(conversationJson2);

        Map<String,Object> paras2 = new HashMap<>();
        paras2.put("conversationName","现在身体怎么样呢");  //话术名称
        paras2.put("conversationDesc","现在身体怎么样呢");  //话术描述
        paras2.put("conversationType",1);                        //话术类型-1表示问题话术,2表示异常话术,3表示补充话术
        paras2.put("breakFlag",1);                               //是否打断	0:可以打断，1：不能打断
        paras2.put("responseTime","0.75");   //响应时间
        paras2.put("priority",0);            //话术优先级 默认0：公共话术优先 1：补充话术优先
        paras2.put("commType",1);            //通信类型 是不是问答单元的 1 是问答单元话术
        paras2.put("conversationJson",jsonArray2);  //话术内容

        String jsonStr2 = MapConvertStr.getStrByMap(paras2);


        Object[][] data = {
                {jsonStr1,"0","success"},
                {jsonStr2,"0","success"}
        };
        return data;
    }


}
