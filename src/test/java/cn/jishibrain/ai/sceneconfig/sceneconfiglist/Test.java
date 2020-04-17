package cn.jishibrain.ai.sceneconfig.sceneconfiglist;

import com.jishibrain.ai.utils.JdbcUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sunxiufang on 2020/4/8 13:12
 */
public class Test {

    public static void main(String[] args) throws SQLException {

        //判断所用表单有没有被使用
        Connection connection = JdbcUtil.getConnection();
        ResultSet resultSetByQuery = JdbcUtil.getResultSetByQuery("SELECT * from t_mapping_scene_form  WHERE form_id = '5c5650b44555438e9bf261356c8353f2' and is_delete = '0' ");

        int i = 0;
        if (resultSetByQuery.next()){
            i = JdbcUtil.executeUpdate(connection, "UPDATE t_mapping_scene_form SET is_delete = '1' WHERE form_id = '5c5650b44555438e9bf261356c8353f2' and is_delete = '0' ");
        }

        System.out.println(i);
    }





}
