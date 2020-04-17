package com.jishibrain.ai.utils;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sunxiufang on 2020/4/1 15:41
 */
public class PropertyUtil {

    private static final Logger logger= Logger.getLogger(PropertyUtil.class);

    public String readPropertiesPara(String file,String para){
        Properties properties = new Properties();
        InputStream inputStream;
        String param;
        try {
            inputStream = JdbcUtil.class.getClassLoader().getResourceAsStream(file);
            properties.load(inputStream);
            param = properties.getProperty(para);
            inputStream.close(); // 关闭流
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e.getMessage());
        }
        return param;
    }

/**
    public static void main(String[] args) {
        PropertyUtil pro = new PropertyUtil();
        String driverClassName = pro.readPropertiesPara("jdbc.properties", "driverClassName");
        logger.info(driverClassName);
    }
*/
}
