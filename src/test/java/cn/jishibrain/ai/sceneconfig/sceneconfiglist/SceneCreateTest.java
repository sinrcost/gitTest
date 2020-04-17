package cn.jishibrain.ai.sceneconfig.sceneconfiglist;
/**
 * 创建场景
 * 使用云端表单和院内表单新建两个场景
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.sceneconfig.sceneconfiglist.SceneCreate;
import com.jishibrain.ai.response.Response;
import com.jishibrain.ai.utils.ExcelDataUtil;
import com.jishibrain.ai.utils.JdbcUtil;
import com.jishibrain.ai.utils.MapConvertStr;
import com.sun.javafx.collections.MappingChange;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunxiufang on 2020/4/3 10:44
 */
public class SceneCreateTest {

    @Test(dataProvider = "creaSceDatas")
    public void test001_creaSce(String jsonStr,String assertResValue,String assertMsgValue) throws SQLException {

        //判断所用表单有没有被使用,如果被使用接触表单的关联关系
        Connection connection = JdbcUtil.getConnection();
        ResultSet resultSetByQuery = JdbcUtil.getResultSetByQuery("SELECT * from t_mapping_scene_form  WHERE form_id = '5c5650b44555438e9bf261356c8353f2' and is_delete = '0' ");

        if (resultSetByQuery.next()){
            JdbcUtil.executeUpdate(connection, "UPDATE t_mapping_scene_form SET is_delete = '1' WHERE form_id = '5c5650b44555438e9bf261356c8353f2' and is_delete = '0' ");
        }

        SceneCreate sceneCreate = new SceneCreate();
        Response response = sceneCreate.addScene(jsonStr);
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
    public Object[][] creaSceDatas() throws Exception {

        //获取场景类别id
        Connection connection = JdbcUtil.getConnection();
        Object[][] data = JdbcUtil.getData(connection,
                "SELECT id FROM t_scene_catagory where name = '接口自动化测试' and is_delete = '0' ORDER BY create_time DESC limit 0,1 ");
        String cateid = (String)data[0][0];

        //接口参数-（正常数据-云端表单）
        Map<String,Object> parasNormal = new HashMap<String, Object>();
        parasNormal.put("approach",1);                  	//绑定途径-0表示通过医院,1表示通过知识库
        parasNormal.put("automaticFrequency",1);            //
        parasNormal.put("automaticPostBack",true);
        parasNormal.put("automaticTime",5);                 //自动执行时间间隔
        parasNormal.put("dynamic",false);                   //是否为动态场景
        parasNormal.put("enableSms",false);                 //是否发送访后短信
        parasNormal.put("examine","true");                  //是否审核
        parasNormal.put("formId","8a09c4d91a7d4fa68f59d53f35e8642f");//
        parasNormal.put("formRepeatId","56932a8f36424aa78d6a832e2052f5d0");
        parasNormal.put("formVersion","");
        parasNormal.put("formVersionId","V1.0");
        parasNormal.put("formVersionNum","V1.0");
        parasNormal.put("hospCode","");
        parasNormal.put("hospName","");
        parasNormal.put("pushContentName","基础库表单xfsun接口专用（勿动勿用）");//	表单名称
        parasNormal.put("pushContentType","1");                                  //	关联内容类型 1、表单类型 3、提醒类型
        parasNormal.put("sceneCategory",cateid);                                 //场景类别id
        parasNormal.put("sceneName","场景名称IDEA（使用云端表单）");             //场景名称
        parasNormal.put("sceneTypeList","1");                                    //通信类型1:电话（默认） 2：微信
        parasNormal.put("speed",5);
        parasNormal.put("voiceType","baidu_1");
        parasNormal.put("volume",15);

        String jsonStr1 = MapConvertStr.getStrByMap(parasNormal);

        //接口参数-（正常数据-院内表单）
        Map<String,Object> parasNorma2 = new HashMap<String, Object>();
        parasNorma2.put("approach",0);      //绑定途径-0表示通过医院,1表示通过知识库
        parasNorma2.put("automaticFrequency",0);    //自动执行次数限制
        parasNorma2.put("automaticPostBack",false); //拨打失败是否自动关闭随访任务
        parasNorma2.put("automaticTime",5);     //自动执行时间间隔
        parasNorma2.put("dynamic",false);       //是否为动态场景
        parasNorma2.put("examine","true");      //是否审核
        parasNorma2.put("formId","5c5650b44555438e9bf261356c8353f2");
        parasNorma2.put("formRepeatId","e89ea16d1cdc4fcbb14c655977ec2870");
        parasNorma2.put("formVersion","");
        parasNorma2.put("formVersionNum","1.0");
        parasNorma2.put("hospCode","joinhealth166");
        parasNorma2.put("hospName","测试环境医院");
        parasNorma2.put("pushContentName","测试环境院内表单xfsun接口专用（勿动勿用）");
        parasNorma2.put("pushContentType","1");     //关联内容类型-1表示表单 3表示提醒
        parasNorma2.put("sceneCategory",cateid);
        parasNorma2.put("sceneName","场景名称IDEA（使用院内表单）");
        parasNorma2.put("sceneTypeList","1");       //通信类型	1:电话（默认） 2：微信  【1 2】都支持
        parasNorma2.put("smsId","null");        //访后短信id
        parasNorma2.put("enableSms",false);     //是否发送访后短信
        parasNorma2.put("speed",5);             //语速
        parasNorma2.put("voiceType","baidu_1"); //音色
        parasNorma2.put("volume",15);           //音量
        parasNorma2.put("templateSceneId","");      //场景模板id
        parasNorma2.put("templateSceneVersionId","");//场景模板版本id
        parasNorma2.put("autoFill",false);      //是否自动文本题
        parasNorma2.put("wechatFillNum",null);  //微信成功题目数量

        String jsonStr2 = MapConvertStr.getStrByMap(parasNorma2);

        Object[][] datas = {
                {jsonStr1,"0","success"},
                {jsonStr2,"0","success"}
        };

        return datas;


       /* String file="src/main/resources/data/ApiTestData.xlsx";
        ExcelDataUtil excel = new ExcelDataUtil(file);
        Object[][] ob = excel.getTestData("新建场景");

        excel.close();
        return ob;*/

    }



    @Test(dataProvider = "creaSceWithAbnormalData")
    public void test002_creaSceWithAbnormal(String jsonStr,String assertResValue,String assertMsgValue) throws SQLException {

        SceneCreate sceneCreate = new SceneCreate();
        Response response = sceneCreate.addScene(jsonStr);
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
    public Object[][] creaSceWithAbnormalData() throws Exception {

        //获取场景类别id
        Connection connection = JdbcUtil.getConnection();
        Object[][] data = JdbcUtil.getData(connection,
                "SELECT id FROM t_scene_catagory where name = '接口自动化测试' and is_delete = '0' ORDER BY create_time DESC limit 0,1 ");
        String cateid = (String)data[0][0];

        //接口参数-（场景名称为空）
        Map<String,Object> parasAbnormal = new HashMap<String, Object>();
        parasAbnormal.put("approach",1);                  	//绑定途径-0表示通过医院,1表示通过知识库
        parasAbnormal.put("automaticFrequency",1);            //
        parasAbnormal.put("automaticPostBack",true);
        parasAbnormal.put("automaticTime",5);                 //自动执行时间间隔
        parasAbnormal.put("dynamic",false);                   //是否为动态场景
        parasAbnormal.put("enableSms",false);                 //是否发送访后短信
        parasAbnormal.put("examine","true");                  //是否审核
        parasAbnormal.put("formId","a624a76cf6334e37af4a99ed8fedf522");//
        parasAbnormal.put("formRepeatId","fa8813ba4b3f4ba79f4fd8c5405de153");
        parasAbnormal.put("formVersion","");
        parasAbnormal.put("formVersionNum","V1.7");
        parasAbnormal.put("pushContentName","lx 矩阵评级");//	表单名称
        parasAbnormal.put("pushContentType","1");                                  //	关联内容类型 1、表单类型 3、提醒类型
        parasAbnormal.put("sceneCategory",cateid);                                 //场景类别id
        parasAbnormal.put("sceneName","");             //场景名称
        parasAbnormal.put("sceneTypeList","1");                                    //通信类型1:电话（默认） 2：微信
        parasAbnormal.put("speed",5);
        parasAbnormal.put("voiceType","baidu_1");
        parasAbnormal.put("volume",15);

        String jsonAbnormalStr1 = MapConvertStr.getStrByMap(parasAbnormal);

        //接口参数-（被使用的表单建场景）
        Map<String,Object> parasAbnorma2 = new HashMap<String, Object>();
        parasAbnorma2.put("approach",0);      //绑定途径-0表示通过医院,1表示通过知识库
        parasAbnorma2.put("automaticFrequency",0);    //自动执行次数限制
        parasAbnorma2.put("automaticPostBack",false); //拨打失败是否自动关闭随访任务
        parasAbnorma2.put("automaticTime",5);     //自动执行时间间隔
        parasAbnorma2.put("dynamic",false);       //是否为动态场景
        parasAbnorma2.put("examine","true");      //是否审核
        parasAbnorma2.put("formId","e39d146812964c948c039f94b71619f4");
        parasAbnorma2.put("formRepeatId","ffb70695d8e84db0b51cbf177ef89093");
        parasAbnorma2.put("formVersion","");
        parasAbnorma2.put("formVersionNum","1.0");
        parasAbnorma2.put("hospCode","joinhealth166");
        parasAbnorma2.put("hospName","测试环境医院");
        parasAbnorma2.put("pushContentName","AI-1.0.9");
        parasAbnorma2.put("pushContentType","1");     //关联内容类型-1表示表单 3表示提醒
        parasAbnorma2.put("sceneCategory",cateid);
        parasAbnorma2.put("sceneName","表单被使用的场景");
        parasAbnorma2.put("sceneTypeList","1");       //通信类型	1:电话（默认） 2：微信  【1 2】都支持
        parasAbnorma2.put("smsId","null");        //访后短信id
        parasAbnorma2.put("enableSms",false);     //是否发送访后短信
        parasAbnorma2.put("speed",5);             //语速
        parasAbnorma2.put("voiceType","baidu_1"); //音色
        parasAbnorma2.put("volume",15);           //音量
        parasAbnorma2.put("templateSceneId","");      //场景模板id
        parasAbnorma2.put("templateSceneVersionId","");//场景模板版本id
        parasAbnorma2.put("autoFill",false);      //是否自动文本题
        parasAbnorma2.put("wechatFillNum",null);  //微信成功题目数量

        String jsonAbnormalStr2 = MapConvertStr.getStrByMap(parasAbnorma2);

        Object[][] datas = {
                {jsonAbnormalStr1,"130001","参数不能为空"},
                {jsonAbnormalStr2,"130001","该表单已配置场景"}
        };

        return datas;


    }
}
