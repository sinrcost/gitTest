package com.jishibrain.ai.utils;

/**
 * Created by sunxiufang on 2020/4/1 16:10
 */


import com.jishibrain.ai.response.Response;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;

public class HttpRequestsUtil {

    private static final Logger logger= Logger.getLogger(HttpRequestsUtil.class);
    private static final CloseableHttpClient httpClient = HttpClientConfigUtil.gethttpClient();
    private static final RequestConfig config = HttpClientConfigUtil.getRequestConfig();

    /**
     * 执行无参的GET请求
     * @param url
     * @param header
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static Response doGet(String url, Map<String,String> header) throws ClientProtocolException, IOException {

        logger.info("url："+url);
        //创建HTTP GET请求
        HttpGet get = new HttpGet(url);
        //给请求设置配置信息
        get.setConfig(config);

        //设置请求头
        if(header != null && !header.isEmpty()) {
            for(Map.Entry<String, String> entry : header.entrySet()) {
                logger.info("hraders："+entry.getKey()+":"+entry.getValue());
                get.addHeader(entry.getKey(),entry.getValue());
            }
        }

        CloseableHttpResponse res = null;
        Response response = null;
        try {
            //执行请求并获取响应
            res = httpClient.execute(get);
            response = HttpResponseUtil.processResponse(res);
            String responseStr = response.getResponseStr();
            logger.info(responseStr);
        }finally {
            if(response != null) {
                res.close();
            }
            get.releaseConnection();
        }
        return response;
    }



    /**
     * 利用URIBuilder执行有参的GRT请求
     * @param urlInitial
     * @param header
     * @param params
     * @return
     */
    public static Response doGetWithBuilder(String urlInitial, Map<String,String> header, Map<String,String> params){

        //设置请求参数
        URIBuilder builder = null;
        HttpGet get = null;
        try {
            //设置请求参数
            builder = new URIBuilder(urlInitial);
            if(params != null && !params.isEmpty()){
                for(Map.Entry<String,String> entry : params.entrySet()){
                    builder.setParameter(entry.getKey(),entry.getValue());
                }
            }

            //创建GET请求
            get = new HttpGet(builder.build());
            //给请求设置配置信息
            get.setConfig(config);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        //设置请求头
        if(header != null && !header.isEmpty()) {
            for(Map.Entry<String, String> entry : header.entrySet()) {
                get.addHeader(entry.getKey(),entry.getValue());
            }

        }


        CloseableHttpResponse response = null;
        Response res = null;
        try {
            //执行请求
            response = httpClient.execute(get);
            res = HttpResponseUtil.processResponse(response);


        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            get.releaseConnection();
        }

        return res;

    }


    /**
     * 执行有参的GET请求通过拼接URL
     * url格式：http://host/api?params
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static Response doGetWithParams(String urlInitial, Map<String,String> header, Map<String,String> params) throws ClientProtocolException, IOException {

        //获取完整URL
        String url = CreateUrlUtil.getUrlWithPara1(urlInitial, params);
        logger.info("完整url："+url);

        //创建HTTP GET请求
        HttpGet get = new HttpGet(url);
        //给请求设置配置信息
        get.setConfig(config);

        //设置请求头
        if(header != null && !header.isEmpty()) {
            for(Map.Entry<String, String> entry : header.entrySet()) {
                logger.info("headers:"+entry.getKey()+":"+entry.getValue());
                get.addHeader(entry.getKey(),entry.getValue());
            }

        }

        CloseableHttpResponse res = null;
        Response response = null;
        try {
            //执行请求并获取响应
            res = httpClient.execute(get);
            response = HttpResponseUtil.processResponse(res);
            logger.info(response.getResponseStr());

        }finally {
            if(response != null) {
                res.close();
            }
            get.releaseConnection();
        }

        return response;

    }



    /**
     * 以原生字符串形式执行POST请求
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static Response doPostWithString(String url, Map<String,String> header, String jsonStr) throws ClientProtocolException, IOException {

        logger.info("请求url:"+url);
        Reporter.log("请求url:"+url);
        //创建POST请求
        HttpPost post = new HttpPost(url);
        //给请求设置配置信息
        post.setConfig(config);

        //设置请求头
        if(header != null && !header.isEmpty()) {
            for(Map.Entry<String, String> entry : header.entrySet()) {
                logger.info("请求headers:"+entry.getKey()+":"+entry.getValue());
                Reporter.log("请求headers:"+entry.getKey()+":"+entry.getValue());
                post.addHeader(entry.getKey(),entry.getValue());
            }

        }

        //接口参数
        StringEntity entity = new StringEntity(jsonStr,"utf-8");
        entity.setContentType("application/json");
        //注入请求参数
        post.setEntity(entity);

        CloseableHttpResponse res = null;
        Response response = null;
        try {
            //执行请求并获取响应
            res = httpClient.execute(post);
            response = HttpResponseUtil.processResponse(res);
            logger.info("响应数据："+response.getResponseStr());
            Reporter.log("响应数据："+response.getResponseStr());

        }finally {
            if(response != null) {
                res.close();
            }

            post.releaseConnection();
        }

        return response;

    }

    /**
     * 以url带参形式执行POST请求
     * @throws URISyntaxException
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static Response doPostWithUrlParams(String url, Map<String,String> header, Map<String,String> params) throws URISyntaxException, ClientProtocolException, IOException {

        //设置请求参数
        URIBuilder builder = new URIBuilder(url);
        if(params != null && !params.isEmpty()) {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                builder.setParameter(entry.getKey(), entry.getValue());
                logger.info(entry.getKey());
            }
        }

        //创建POST请求
        HttpPost post = new HttpPost(builder.build());
        //给请求设置配置信息
        post.setConfig(config);

        //设置请求头
        if(header != null && !header.isEmpty()) {
            for(Map.Entry<String, String> entry : header.entrySet()) {
                post.addHeader(entry.getKey(),entry.getValue());
            }

        }

        CloseableHttpResponse res = null;
        Response response = null;
        try {
            //执行请求并获取响应
            res = httpClient.execute(post);
            response = HttpResponseUtil.processResponse(res);

        }finally {
            if(response != null) {
                res.close();
            }

            post.releaseConnection();
        }

        return response;

    }

    /**
     * 以form表单键值对形式执行POST请求
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static Response doPostWithForm(String url, Map<String, String> header, Map<String, String> paramOfBody) throws ClientProtocolException, IOException {

        //创建POST请求
        HttpPost post = new HttpPost(url);
        //给请求设置配置信息
        post.setConfig(config);

        //设置请求头
        if(header != null && !header.isEmpty()) {
            for(Map.Entry<String, String> entry : header.entrySet()) {
                post.addHeader(entry.getKey(),entry.getValue());
            }
        }

        //装填参数
        //声明List集合，封装表单中的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(paramOfBody != null  && !paramOfBody.isEmpty()){
            for (Map.Entry<String, String> entry : paramOfBody.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //创建表单的Entity对象
        UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(nvps, "UTF-8");
        //设置表单的Entity对象到POST请求中
        post.setEntity(formEntiry);

        CloseableHttpResponse res = null;
        Response response = null;
        try {
            //执行请求并获取响应
            res = httpClient.execute(post);
            response = HttpResponseUtil.processResponse(res);

        }finally {
            if(response != null) {
                res.close();
            }

            post.releaseConnection();
        }
        return response;

    }



    /**
     * 用OkHttpClient框架发送上传文件的请求
     * @param url
     * @param token
     * @param file
     * @return
     * @throws Exception
     */
    public static ResponseBody doPostMultipartFile1(String url,String token, File file) throws Exception {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "cjexcel.xlsx",
                        RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", token)
                .post(requestBody)
                .build();

        okhttp3.Response response = client.newCall(request).execute();

        ResponseBody responseBody = response.body();



        return responseBody;
    }



    /**
     * 通过POST请求上传文件
     * @param url
     * @param header
     * @param params
     * @param files
     * @param boundary
     * @param mimeType
     * @return
     * @throws Exception
     */
    public static Response doPostMultipartFile(String url, Map<String, String> header, Map<String, String> params, List<File> files, String boundary, String mimeType) throws Exception {
        //创建HTTP POST请求
        HttpPost post = new HttpPost(url);
        //给请求设置配置信息
        post.setConfig(config);

        //设置请求头
        if(boundary==null){
            post.addHeader("Content-Type","multipart/form-data;charset=utf-8");
        }else {
            post.addHeader("Content-Type","multipart/form-data;charset=utf-8;boundary="+boundary);
        }

        if(header != null && !header.isEmpty()){
            for(Map.Entry<String,String> entry:header.entrySet()){
                post.addHeader(entry.getKey(),entry.getValue());
            }
        }

        //参数设置
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        multipartEntityBuilder.setCharset(Charset.forName("UTF-8"));
        //普通参数 如mimeType:text/plain
        ContentType contentType = ContentType.create(mimeType, Charset.forName("UTF-8"));
        if(params !=null && !params.isEmpty() ){
            Set<String> keys = params.keySet();
            for(String key:keys){
                multipartEntityBuilder.addTextBody(key,params.get(key),contentType);
            }
        }
        //二进制参数
        if(files!=null && files.size()>0){
            for(File file:files){
                multipartEntityBuilder.addBinaryBody("file",file);
            }
        }
        post.setEntity(multipartEntityBuilder.build());


        CloseableHttpResponse res = null;
        Response response = null;

        try {
            // 执行请求
            res = httpClient.execute(post);
            response = HttpResponseUtil.processResponse(res);

        } finally {
            if (res != null) {
                res.close();
            }

            post.releaseConnection();
        }

        return response;
    }

    /**
     * 以原生字符串形式执行PUT请求
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static Response doPutWithString(String url, Map<String,String> header, String jsonStr) throws ClientProtocolException, IOException {

        //创建PUT请求
        HttpPut put = new HttpPut(url);
        //给请求设置配置信息
        put.setConfig(config);

        //设置请求头
        if (header != null && !header.isEmpty()) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                put.addHeader(entry.getKey(), entry.getValue());
            }

        }

        // 接口参数
        StringEntity entity = new StringEntity(jsonStr, "utf-8");
        entity.setContentType("application/json");
        // 注入请求参数
        put.setEntity(entity);


        CloseableHttpResponse res = null;
        Response response = null;
        try {
            // 执行请求并获取响应
            res = httpClient.execute(put);
            response = HttpResponseUtil.processResponse(res);

        } finally {
            if (response != null) {
                res.close();
            }

            put.releaseConnection();
        }

        return response;
    }

    /**
     * 以URL带参形式执行PUT请求
     * @throws URISyntaxException
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static Response doPutWithUrlParams(String url, Map<String,String> header, Map<String,String> params) throws URISyntaxException, ClientProtocolException, IOException {

        //设置请求参数
        URIBuilder builder = new URIBuilder(url);
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.setParameter(entry.getKey(), entry.getValue());

            }
        }

        // 创建PUT请求
        HttpPut put = new HttpPut(builder.build());
        //给请求设置配置信息
        put.setConfig(config);


        // 设置请求头
        if (header != null && !header.isEmpty()) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                put.addHeader(entry.getKey(), entry.getValue());
            }

        }


        CloseableHttpResponse res = null;
        Response response = null;
        try {
            // 执行请求并获取响应
            res = httpClient.execute(put);
            response = HttpResponseUtil.processResponse(res);

        } finally {
            if (response != null) {
                res.close();
            }

            put.releaseConnection();
        }

        return response;

    }

    /**
     * DELETE请求
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static Response delete(String url, Map<String,String> header) throws ClientProtocolException, IOException {
        //创建HTTP DELETE请求
        HttpDelete delete = new HttpDelete(url);
        //给请求设置配置信息
        delete.setConfig(config);

        // 设置请求头
        if (header != null && !header.isEmpty()) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                delete.addHeader(entry.getKey(), entry.getValue());
            }

        }



        CloseableHttpResponse res = null;
        Response response = null;
        try {
            // 执行请求并获取响应
            res = httpClient.execute(delete);
            response = HttpResponseUtil.processResponse(res);

        } finally {
            if (response != null) {
                res.close();
            }

            delete.releaseConnection();
        }

        return response;

    }
}


