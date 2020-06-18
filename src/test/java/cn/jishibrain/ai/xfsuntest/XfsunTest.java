package cn.jishibrain.ai.xfsuntest;

import com.jishibrain.ai.utils.JdbcUtil;

import java.sql.Connection;

/**
 * Created by sunxiufang on 2020/5/29 18:07
 */
public class XfsunTest {


    public void test001(){

        Connection connection = JdbcUtil.getConnection();

        //第一单元问题话术
        Object[][] conver1 = JdbcUtil.getData(connection, "SELECT id,conversation_name from t_conversation WHERE  conversation_name = '现在方便做个回访调查吗' AND is_delete = '0' ORDER BY create_time DESC limit 0,1");
        String conversationId1 = (String) conver1[0][0];
        String conversationName1 = (String) conver1[0][1];
        //第一单元unitId
        Object[][] unit1 = JdbcUtil.getData(connection,
                "SELECT id from t_ask_unit WHERE unit_name = '现在方便做个回访调查吗' ORDER BY create_time DESC limit 0,1");
        String unitId1 = (String)unit1[0][0];

        //第二单元问题话术
        Object[][] conver2 = JdbcUtil.getData(connection, "SELECT id,conversation_name from t_conversation WHERE  conversation_name = '就诊后身体怎么样呢' AND is_delete = '0' ORDER BY create_time DESC limit 0,1");
        String conversationId2 = (String) conver2[0][0];
        String conversationName2 = (String) conver2[0][1];
        //第二单元unitId
        Object[][] unit2 = JdbcUtil.getData(connection,
                "SELECT id from t_ask_unit WHERE unit_name = '就诊后身体怎么样呢' ORDER BY create_time DESC limit 0,1");
        String unitId2 = (String)unit2[0][0];

        //第三单元问题话术
        Object[][] conver3 = JdbcUtil.getData(connection, "SELECT id,conversation_name from t_conversation WHERE  conversation_name = '就诊后身体怎么样呢' AND is_delete = '0' ORDER BY create_time DESC limit 0,1");
        String conversationId3 = (String) conver3[0][0];
        String conversationName3 = (String) conver3[0][1];
        //第三单元unitId
        Object[][] unit3 = JdbcUtil.getData(connection,
                "SELECT id from t_ask_unit WHERE unit_name = '就诊后身体怎么样呢' ORDER BY create_time DESC limit 0,1");
        String unitId3 = (String)unit3[0][0];


        //第四单元问题话术
        Object[][] conver4 = JdbcUtil.getData(connection, "SELECT id,conversation_name from t_conversation WHERE  conversation_name = '就诊后身体怎么样呢' AND is_delete = '0' ORDER BY create_time DESC limit 0,1");
        String conversationId4 = (String) conver4[0][0];
        String conversationName4 = (String) conver4[0][1];
        //第四单元unitId
        Object[][] unit4 = JdbcUtil.getData(connection,
                "SELECT id from t_ask_unit WHERE unit_name = '就诊后身体怎么样呢' ORDER BY create_time DESC limit 0,1");
        String unitId4 = (String)unit4[0][0];

        //第五单元问题话术
        Object[][] conver5 = JdbcUtil.getData(connection, "SELECT id,conversation_name from t_conversation WHERE  conversation_name = '就诊后身体怎么样呢' AND is_delete = '0' ORDER BY create_time DESC limit 0,1");
        String conversationId5 = (String) conver5[0][0];
        String conversationName5 = (String) conver5[0][1];
        //第五单元unitId
        Object[][] unit5 = JdbcUtil.getData(connection,
                "SELECT id from t_ask_unit WHERE unit_name = '就诊后身体怎么样呢' ORDER BY create_time DESC limit 0,1");
        String unitId5 = (String)unit5[0][0];


        //第六单元问题话术
        Object[][] conver6 = JdbcUtil.getData(connection, "SELECT id,conversation_name from t_conversation WHERE  conversation_name = '就诊后身体怎么样呢' AND is_delete = '0' ORDER BY create_time DESC limit 0,1");
        String conversationId6 = (String) conver6[0][0];
        String conversationName6 = (String) conver6[0][1];
        //第六单元unitId
        Object[][] unit6 = JdbcUtil.getData(connection,
                "SELECT id from t_ask_unit WHERE unit_name = '就诊后身体怎么样呢' ORDER BY create_time DESC limit 0,1");
        String unitId6 = (String)unit6[0][0];

    }

}
