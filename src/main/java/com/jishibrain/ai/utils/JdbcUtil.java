package com.jishibrain.ai.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunxiufang on 2020/4/1 15:06
 */
public class JdbcUtil {

    /**
     * 获取数据库链接
     * @return 连接对象
     */
    public static Connection getConnection(){
        Connection connection = null;
        PropertyUtil propertyUtil = new PropertyUtil();
        String url = propertyUtil.readPropertiesPara("jdbc.properties", "url");
        String user = propertyUtil.readPropertiesPara("jdbc.properties", "username");
        String password = propertyUtil.readPropertiesPara("jdbc.properties", "password");

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;

    }


    public static void closeConn(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Object[][] getData(Connection conn, String query) {
        Object[][] data = null;

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int column = meta.getColumnCount();
            List<Object[]> list = new ArrayList<Object[]>();
            while (rs.next()) {
                Object[] oa = new Object[column];
                for (int i = 1; i <= column; i++) {
                    oa[i - 1] = rs.getObject(i);
                }
                list.add(oa);
            }
            if (list.size() > 0) {
                data = new Object[list.size()][column];
                for (int i = 0; i < list.size(); i++) {
                    data[i] = list.get(i);
                }
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    // executeUpdate方法可以执行新增、更新、删除三种sql语句
    public static int executeUpdate1(Connection conn, String sql) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            int updateCount = stmt.getUpdateCount();
            return updateCount;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    /**
     * 更新数据库操作
     * @param sql  删除 更新数据
     * @return
     */
    public static int executeUpdate(Connection connection,String sql) throws SQLException {
        PreparedStatement preparedStatement = null;
        //  ResultSet resultSet = null;
        int count = 0;

        try {
            //获取执行sql的对象 Statement
            preparedStatement = connection.prepareStatement(sql);
            //执行sql
            count = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            preparedStatement.close();
            connection.close();
        }

        return count;

    }


    /**
     * 查询指定参数的数据
     * @param sql
     * @param para
     * @return
     */
    public static ResultSet getResultSetByQuery(String sql){

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //调用方法获取连接
            connection = getConnection();
            //获取执行sql的对象 Statement
            preparedStatement = connection.prepareStatement(sql);
            //执行sql
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;

    }



    public static void main(String[] args) throws SQLException {

        Connection connection = JdbcUtil.getConnection();

        String sql =
                "UPDATE t_intention_category SET intention_type_name = '新类别002#￥%'  WHERE intention_type_name = '新类别002#￥%bs'";
        JdbcUtil.executeUpdate(connection,sql);
        /*try{
            Object[][] data = JdbcUtil.getData(connection,
                    "SELECT intention_type_name from t_intention_category ORDER BY create_time DESC limit 0,1");

            *//*for (int i = 0; i < data.length; i++) {
                Object[] obl = data[i];
                System.out.println("");
                for (int j = 0; j < obl.length; j++) {
                    System.out.print(obl[j] + "\t");
                }
            }*//*

            System.out.println(data[0][0]);

        }finally {
            JdbcUtil.closeConn(connection);
        }
*/



    }

}
