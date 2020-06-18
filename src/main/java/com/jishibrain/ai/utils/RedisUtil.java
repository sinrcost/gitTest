package com.jishibrain.ai.utils;


/**
 * Created by sunxiufang on 2020/6/9 17:58
 */

import redis.clients.jedis.Jedis;

public class RedisUtil {

    public static String getValue(String ip,String key) {
        Jedis jedis=new Jedis(ip);
        return jedis.get(key);
    }
    public static void main(String[] args) {
        Jedis jedis=new Jedis("localhost");
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
        jedis.append("18729399607", "4567");  //向redis存数据
        jedis.append("username", "张三");
        jedis.mset("username", "xfsun");  //修改数据
        System.out.println(jedis.get("18729399607"));
        System.out.println(jedis.get("username"));
        String string2 = jedis.get("72CRM_USER_ADMIN_TOKEN3");
        System.out.println(string2);

        jedis.close();
    }

}
