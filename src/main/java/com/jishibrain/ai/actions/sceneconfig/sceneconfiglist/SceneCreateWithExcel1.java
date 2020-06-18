package com.jishibrain.ai.actions.sceneconfig.sceneconfiglist;

import com.jishibrain.ai.utils.ApiURLUtil;
import com.jishibrain.ai.utils.CreateUrlUtil;
import com.jishibrain.ai.utils.HttpRequestsUtil;
import com.jishibrain.ai.utils.TokenUtil;
import okhttp3.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by sunxiufang on 2020/5/27 9:36
 */
public class SceneCreateWithExcel1 {


    public static void main(String[] args) {

        ResponseBody responseBody = addSceneExcel();

    }

    public static ResponseBody addSceneExcel(){

        //http://192.168.3.68:2002/ai-web/ai/scene/excel2Scene?
        // sceneName=Excel转场景&hospCode=&hospName=&pushContentType=1
        // &formId=683d492eaf414b47b2c4e22bec7332d8&formRepeatId=94909563def945409fb82c001f3f8fb9
        // &pushContentName=%E9%AA%8C%E6%94%B6%E7%94%A8%E8%A1%A8%E5%8D%956&voiceType=baidu_1&volume=15&speed=5
        // &examine=true&sceneType=1&categoryId=5d1044c48c79ac2d6dc510c0&categoryName=1.1.0
        // &automaticFrequency=0&automaticTime=5&automaticPostBack=false&enableSms=false&dynamic=false&autoFill=false
        // &approach=1&importConversation=1


        String url = "http://192.168.3.68:2002/ai-web/ai/scene/excel2Scene?sceneName=Excel1&hospCode=&hospName=&pushContentType=1&formId=7c6bc37f932b4a8cb203b96f47ba858a&formRepeatId=283e435f6a6e46bb8df717887fefa724&pushContentName=xfsunsxf&voiceType=baidu_1&volume=15&speed=5&examine=true&sceneType=1&categoryId=5d1044c48c79ac2d6dc510c0&categoryName=1.1.0&automaticFrequency=0&automaticTime=5&automaticPostBack=false&enableSms=false&dynamic=false&autoFill=false&approach=1&importConversation=1";

        String urlInitial = "http://192.168.3.68:2002/ai-web/ai/scene/excel2Scene";
        Map<String,Object> urlparams = new HashMap<String, Object>();
        urlparams.put("sceneName","Excel1");
        urlparams.put("hospCode","");
        urlparams.put("hospName","");
        urlparams.put("pushContentType","1");                    //	关联内容类型 1、表单类型 3、提醒类型
        urlparams.put("formId","7c6bc37f932b4a8cb203b96f47ba858a");//
        urlparams.put("formRepeatId","283e435f6a6e46bb8df717887fefa724");
        urlparams.put("pushContentName","xfsunsxf");//	表单名称
        urlparams.put("speed",5);
        urlparams.put("voiceType","baidu_1");
        urlparams.put("volume",15);
        urlparams.put("examine","true");                  //是否审核
        urlparams.put("sceneType","1");                   //通信类型1:电话（默认） 2：微信
        urlparams.put("categoryId","5d1044c48c79ac2d6dc510c0");
        urlparams.put("categoryName","1.1.0");
        urlparams.put("automaticFrequency",0);
        urlparams.put("automaticTime",5);                 //自动执行时间间隔
        urlparams.put("automaticPostBack",true);
        urlparams.put("dynamic",false);                   //是否为动态场景
        urlparams.put("enableSms",false);                 //是否发送访后短信
        urlparams.put("autoFill",false);
        urlparams.put("approach",1);                  	//绑定途径-0表示通过医院,1表示通过知识库
        urlparams.put("importConversation",1);
      //  String urlWith = CreateUrlUtil.getUrlWithPara3(urlInitial, urlparams);

        //设置token
        ResourceBundle bundle = ResourceBundle.getBundle("host");
        String token = bundle.getString("token");

        //发送请求
        File file = new File("src/main/resources/data/cjexcel.xlsx");
        ResponseBody responseBody = null;
        try {
            responseBody = HttpRequestsUtil.doPostMultipartFile1(url,token,file);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseBody;

    }



}
