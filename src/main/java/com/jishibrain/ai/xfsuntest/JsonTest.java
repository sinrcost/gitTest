package com.jishibrain.ai.xfsuntest;

import com.alibaba.fastjson.JSONObject;

/**
 * 修改Excel中json请求参数的某个字段的值
 * Created by sunxiufang on 2020/5/26 9:47
 */
public class JsonTest {

    public static void main(String[] args) {

        String param = "{\n" +
                " \"entity\": {\n" +
                "  \"name\": \"沙漠\",\n" +
                "  \"customer_id\": 5,\n" +
                "  \"mobile\": \"18098909090\",\n" +
                "  \"telephone\": \"01023456789\",\n" +
                "  \"email\": \"shddhdd@163.com\",\n" +
                "  \"post\": \"总经理\",\n" +
                "  \"address\": \"北京霍营\",\n" +
                "  \"next_time\": \"2020-04-23 16:03:47\",\n" +
                "  \"remark\": \"这是备注\"\n" +
                " }\n" +
                "}";


        JSONObject jsonObject = JSONObject.parseObject(param);
        JSONObject entity = jsonObject.getJSONObject("entity");
        entity.replace("customer_id", "10");
        String s = entity.toJSONString();
        System.out.println(s);

        String newparam = " {\"entity\": "+ s + "}" ;
        System.out.println(newparam);

    }

}
