package com.jishibrain.ai.utils;
/**
 * 拼接URL的工具类
 */

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by sunxiufang on 2020/4/1 16:25
 */
public class CreateUrlUtil {

    /**
     * url = http://192.168.3.68:2002/ai-web/ai/intentions/categoryOrIntention?para1=值1&para2=值2
     * @param urlInitial
     * @param params
     * @return
     */
    public static String getUrlWithPara1(String urlInitial, Map<String,String> params) {
        String url = urlInitial;

        if(params == null || params.size() == 0) {
            return url;

        }else {
            StringBuffer sbuffer = new StringBuffer(url+"?");

            for(Map.Entry<String,String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                try {
                    value = URLEncoder.encode(value, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                sbuffer.append(key).append("=").append(value).append("&");
            }

            url = sbuffer.toString();
            url = url.substring(0, url.length()-1);
            return url;
        }

    }


    /**
     * url = http://192.168.3.68:2002/ai-web/ai/intentions/category/para
     * para为参数
     * @param urlInitial
     * @param value
     * @return
     */
    public static String getUrlWithPara2(String urlInitial, String value){

        String url = urlInitial;

        if(value == null ) {
            return url;
        }else {
            StringBuffer sbuffer = new StringBuffer(url+"/");
            sbuffer.append(value);
            url = sbuffer.toString();
          //  url = url.substring(0, url.length());
            return url;
        }

    }


    public static String getUrlWithPara3(String urlInitial, Map<String,Object> params) {
        String url = urlInitial;

        if(params == null || params.size() == 0) {
            return url;

        }else {
            StringBuffer sbuffer = new StringBuffer(url+"?");

            for(Map.Entry<String,Object> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = (String) entry.getValue();
                try {
                    value = URLEncoder.encode(value, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                sbuffer.append(key).append("=").append(value).append("&");
            }

            url = sbuffer.toString();
            url = url.substring(0, url.length()-1);
            return url;
        }

    }


    public static void main(String[] args) {
        String urlWithPara2 = getUrlWithPara2("http://192.168.3.68:2002/ai-web/ai/intentions/category", "5d9d583a14d847024c0408d8");
        System.out.println(urlWithPara2);
    }

}
