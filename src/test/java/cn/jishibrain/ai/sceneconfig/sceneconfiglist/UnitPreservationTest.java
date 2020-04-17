package cn.jishibrain.ai.sceneconfig.sceneconfiglist;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.sceneconfig.sceneconfiglist.UnitPreservation;
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
 * 保存单元
 * Created by sunxiufang on 2020/4/8 11:11
 */
public class UnitPreservationTest {

    @Test(dataProvider = "preUnitDatas")
    public void test001_preUnit(String jsonStr,String assertResValue,String assertMsgValue,String sceneVersionId){
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
    public Object[][] preUnitDatas(){

        Connection connection = JdbcUtil.getConnection();
        Object[][] data1 = JdbcUtil.getData(connection, "SELECT id FROM t_scene WHERE scene_name = '场景名称IDEA（使用院内表单）' AND is_delete = '0'");
        String sceneId = (String) data1[0][0];
        Object[][] data2 = JdbcUtil.getData(connection,"SELECT id FROM t_scene_version WHERE scene_id = '"+ sceneId +"'");
        String sceneVersionId = (String) data2[0][0];
        //第一单元问题话术
        Object[][] data3 = JdbcUtil.getData(connection, "SELECT id,conversation_name from t_conversation WHERE  conversation_name = '现在方便做个回访调查吗' AND is_delete = '0' ORDER BY create_time DESC limit 0,1");
        String conversationId1 = (String) data3[0][0];
        String conversationName1 = (String) data3[0][1];
        //第一单元unitId
        Object[][] data4 = JdbcUtil.getData(connection,
                "SELECT id from t_ask_unit WHERE unit_name = '问候语' ORDER BY create_time DESC limit 0,1");
        String unitId1 = (String)data4[0][0];

        //第二单元问题话术
        Object[][] data5 = JdbcUtil.getData(connection, "SELECT id,conversation_name from t_conversation WHERE  conversation_name = '现在身体怎么样呢' AND is_delete = '0' ORDER BY create_time DESC limit 0,1");
        String conversationId2 = (String) data5[0][0];
        String conversationName2 = (String) data5[0][1];
        //第二单元unitId
        Object[][] data6 = JdbcUtil.getData(connection,
                "SELECT id from t_ask_unit WHERE unit_name = '身体调查' ORDER BY create_time DESC limit 0,1");
        String unitId2 = (String)data6[0][0];
/**
        [       {"repeatLimit":1,               //次数限制
                "intentionPriority":2,          //意图优先级
                "aboveIntentionId":"",
                "aboveIntentionName":"",
                "titleJson":"",                 //关联题目选项
                "quoteTitle":"",
                "intentionName":"方便",           //意图名称
                "intentionContent":"方便;可以",     //意图关键词
                "intentionBlackContent":"",         //意图黑名单关键词
                "titleBindFlag":false,              //是否关联题目
                "terminateIntentionFlag":true,      //是否结束本单元
                "nextUnitFlag":true,                //是否有跳转
                "nextUnitId":"5e8c4842cfc4190001bd5f7e"}    ]    //跳转的问答单元ID
*/
        String intentionJson = "[{askUnitId:'5e8c4841cfc4190001bd5f7c'}," + "{createTime: '2020-04-08 14:25:55'}," +
                "{dialogUnitFlag:false},"+"{id:'5e8d67e3cfc4190001bd6176'},"+"{order:0},"+
                "{createTime:'2020-04-08 14:25:55'},"+ "{repeatLimit:1}," + "{intentionPriority: 2}," +
                "{intentionName: '方便'},"+ "{intentionContent: '方便;可以;sun'},"+"{titleBindFlag: false},"+
                "{terminateIntentionFlag: true},"+ "{nextUnitFlag: true},"+ "{nextUnitId:'5e8c4842cfc4190001bd5f7e'}]";

        //第一单元意图
        //,titleJson:{questionnaireId:'5c5650b44555438e9bf261356c8353f2',answers:[{questionAnswer:'984d97886c3242faa1d76db3cc903c5b',questionId:'ad9d314413154f3c8d9ee928967056bf',question:'现在方便做个回访调查吗',answer:'方便',except:'false',questionType:2,quoteQuestionId:'',quoteQuestionAnswer:'',children:{selectionId:'984d97886c3242faa1d76db3cc903c5b'}}]}
        String intentionJson1 =  "[{repeatLimit:3,intentionPriority: 2,intentionName: '方便',intentionContent: '方便;可以;sun',intentionBlackContent:'不;没时间',titleBindFlag: false,terminateIntentionFlag: true}," +
                "{repeatLimit:3,intentionPriority: 2,intentionName: '不方便',intentionContent: '不方便;没时间',intentionBlackContent:'you',titleBindFlag: false,terminateIntentionFlag: true}]";

        JSONArray jsonArray1 = JSONArray.fromObject(intentionJson1);

        //第一单元
        Map<String,Object> paras1 = new HashMap<>();
        paras1.put("abnormalTimes",0);       //异常话术次数
        paras1.put("afterPublicTimes",0);    //接公共话术次数
        paras1.put("askUnitNum","00");       //问答单元序号  00、01、02排列
        paras1.put("breakDialogFlag",0);
        paras1.put("commType",3);        //通信渠道 1电话,2微信,3两者都可以
        paras1.put("conversationId",conversationId1);     //正常话术ID
        paras1.put("conversationName",conversationName1); //话术名称
        paras1.put("conversionBreakFlag",1);
        paras1.put("createTime","2020-04-08 13:30:41");
        paras1.put("editor","xfsun");
        paras1.put("id",unitId1);         //问答单元id
        paras1.put("isDelete",false);
        paras1.put("sceneVersionId",sceneVersionId);
        paras1.put("titleBindFlag",false);  //是否绑定题目
        paras1.put("unitName","问候语");
        paras1.put("updateTime","2020-04-08 13:30:41");
        paras1.put("intentions",jsonArray1);

        String jsonStr1 = MapConvertStr.getStrByMap(paras1);



        //第二单元意图
        String intentionJson2 =  "[{repeatLimit:3,intentionPriority: 2,intentionName: '挺好的',intentionContent: '恢复好;身体不错;身体很好',titleBindFlag: false,terminateIntentionFlag: true}," +
                "{repeatLimit:3,intentionPriority: 2,intentionName: '不太好',intentionContent: '很差;不好',intentionBlackContent:'不错;好',titleBindFlag: false,terminateIntentionFlag: true}]";

        JSONArray jsonArray2 = JSONArray.fromObject(intentionJson2);

        //第二单元
        Map<String,Object> paras2 = new HashMap<>();
        paras2.put("abnormalTimes",0);       //异常话术次数
        paras2.put("afterPublicTimes",0);    //接公共话术次数
        paras2.put("askUnitNum","01");       //问答单元序号  00、01、02排列
        paras2.put("breakDialogFlag",0);
        paras2.put("commType",3);        //通信渠道 1电话,2微信,3两者都可以
        paras2.put("conversationId",conversationId2);     //正常话术ID
        paras2.put("conversationName",conversationName2); //话术名称
        paras2.put("conversionBreakFlag",1);
        paras2.put("createTime","2020-04-08 13:30:41");
        paras2.put("editor","xfsun");
        paras2.put("id",unitId2);         //问答单元id
        paras2.put("isDelete",false);
        paras2.put("sceneVersionId",sceneVersionId);
        paras2.put("titleBindFlag",false);  //是否绑定题目
        paras2.put("unitName","身体调查");
        paras2.put("updateTime","2020-04-08 13:30:41");
        paras2.put("intentions",jsonArray2);

        String jsonStr2 = MapConvertStr.getStrByMap(paras2);


        Object[][] data = {
                {jsonStr1,"0","success",sceneVersionId},
                {jsonStr2,"0","success",sceneVersionId}
        };
        return data;

    }

}
