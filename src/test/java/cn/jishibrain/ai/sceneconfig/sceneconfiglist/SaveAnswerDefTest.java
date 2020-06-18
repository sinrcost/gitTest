package cn.jishibrain.ai.sceneconfig.sceneconfiglist;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishibrain.ai.actions.sceneconfig.sceneconfiglist.SaveAnswerDef;
import com.jishibrain.ai.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by sunxiufang on 2020/5/27 16:16
 */
public class SaveAnswerDefTest {

    @Test(dataProvider = "saveAnsData")
    public void test001_saveAns(String jsonStr,String assertResValue,String assertMsgValue){

        SaveAnswerDef answerDef = new SaveAnswerDef();
        Response response = answerDef.ansDef(jsonStr);
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
    public Object[][] saveAnsData(){
        //relationId和questionnaireId都是表单ID
        String jsonStr = "{\"relationId\":\"5c5650b44555438e9bf261356c8353f2\",\"titleJson\":\"{\\\"questionnaireId\\\":\\\"5c5650b44555438e9bf261356c8353f2\\\",\\\"answers\\\":[{\\\"questionId\\\":\\\"ad9d314413154f3c8d9ee928967056bf\\\",\\\"question\\\":\\\"现在方便做个回访调查吗\\\",\\\"questionAnswer\\\":\\\"984d97886c3242faa1d76db3cc903c5b\\\",\\\"answer\\\":\\\"方便\\\",\\\"except\\\":false,\\\"questionType\\\":2,\\\"quoteQuestionId\\\":\\\"\\\",\\\"quoteQuestionAnswer\\\":\\\"\\\",\\\"other\\\":true,\\\"otherContent\\\":\\\"\\\",\\\"titleNo\\\":1},{\\\"questionId\\\":\\\"f5ecc50d22cd40d58c2e3fd9834eb167\\\",\\\"question\\\":\\\"就诊后身体怎么样呢\\\",\\\"questionAnswer\\\":\\\"a5f38fc94c324f038b8cd4d2aee99007\\\",\\\"answer\\\":\\\"挺好的\\\",\\\"except\\\":false,\\\"questionType\\\":2,\\\"quoteQuestionId\\\":\\\"\\\",\\\"quoteQuestionAnswer\\\":\\\"\\\",\\\"other\\\":true,\\\"otherContent\\\":\\\"\\\",\\\"titleNo\\\":2},{\\\"questionId\\\":\\\"d0fa1de9e05d4d39a7f0ead4f2bc9bf1\\\",\\\"question\\\":\\\"医生态度评价\\\",\\\"questionAnswer\\\":\\\"ee810011ecd046d08cab36357b1ba346\\\",\\\"answer\\\":\\\"挺好的\\\",\\\"except\\\":false,\\\"questionType\\\":2,\\\"quoteQuestionId\\\":\\\"\\\",\\\"quoteQuestionAnswer\\\":\\\"\\\",\\\"other\\\":true,\\\"otherContent\\\":\\\"\\\",\\\"titleNo\\\":3},{\\\"questionId\\\":\\\"11a6b660ba2f4423a9e6e9242aa9a067\\\",\\\"question\\\":\\\"早餐调查\\\",\\\"questionAnswer\\\":[\\\"72ec20adb5e440138d57870c10b9cb10\\\",\\\"ce0a2934780e4ee8a14371e5a9528ea3\\\"],\\\"answer\\\":[\\\"鸡蛋\\\",\\\"鸡蛋\\\",\\\"粗粮\\\"],\\\"except\\\":\\\"false\\\",\\\"questionType\\\":3,\\\"quoteQuestionId\\\":\\\"\\\",\\\"quoteQuestionAnswer\\\":\\\"\\\",\\\"titleNo\\\":4},{\\\"questionId\\\":\\\"7dc89810046b4f77a68a7a1249a2c600\\\",\\\"question\\\":\\\"满意度调查\\\",\\\"questionAnswer\\\":\\\"db1c1e83c436493d9694ee317dd64d95\\\",\\\"optionId\\\":\\\"e41d96d887014afcb08f35daa91cdf94\\\",\\\"answer\\\":\\\"\\\",\\\"except\\\":false,\\\"questionType\\\":4,\\\"quoteQuestionId\\\":\\\"\\\",\\\"quoteQuestionAnswer\\\":\\\"\\\"},{\\\"questionId\\\":\\\"258ac7891a19403591d97b8e343024f3\\\",\\\"question\\\":\\\"对我院有什么意见\\\",\\\"questionAnswer\\\":\\\"都很好\\\",\\\"answer\\\":\\\"都很好\\\",\\\"except\\\":false,\\\"questionType\\\":1,\\\"quoteQuestionId\\\":\\\"\\\",\\\"quoteQuestionAnswer\\\":\\\"\\\",\\\"titleNo\\\":6}]}\"}";


        Object[][] data = {
                {jsonStr,"0","success"}

        };
        return data;

    }

}
