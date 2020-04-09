package com.sinocontact.qyweixin.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2017/5/22.
 */
public class MyHttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(MyHttpUtils.class);
    // get方式请求URL
    public static String sendGet(String url){
        logger.info("send get url:"+url);
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            //conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 建立实际的连接
            conn.connect();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine())!= null) {
                result += line;
            }
        }
        catch(Exception e) {
            logger.error("发送GET请求出现异常！", e);
        }
        // 使用finally块来关闭输入流
        finally {
            try{
                if (in != null){in.close();}
            }
            catch (Exception ex){
                logger.error("",ex);
            }
        }
        logger.info("send get result :"+result);
        return result;
    }



    // 组织get方式请求参数
    public static String getParms(String returnStr, String key, String value){
        if (StringUtils.isNotEmpty(returnStr)){
            if (returnStr.contains("?")){
                returnStr += "&"+key+"="+value;
            }else{
                returnStr += "?"+key+"="+value;
            }
        }
        return returnStr;
    }

    // post方式请求url
    public static String sendPostJson(String url, String pram) {
        String result="";
        try {
            URL realUrl = new URL(url);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) realUrl
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(pram);
            out.flush();
            out.close();
            // 读取响应
            int length = (int) connection.getContentLength();// 获取长度
            InputStream is = connection.getInputStream();
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                result= new String(data, "UTF-8"); // utf-8编码
            }
        } catch (Exception e) {
            logger.error("",e);
        }
        logger.info("send post json result:"+result);
        return result;
    }


    public static String postForm(String url, Map<String,String> map){
        String encoding = "UTF-8";
        String body = "";
        try{
            //创建httpclient对象
            CloseableHttpClient client = HttpClients.createDefault();
            //创建post方式请求对象
            HttpPost httpPost = new HttpPost(url);
            List<BasicNameValuePair> nvps = new ArrayList<>();
            if(map!=null){
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            //设置参数到请求对象中
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
            //设置连接超时时间 为3秒
            RequestConfig config = RequestConfig.custom().setConnectTimeout(3000).setConnectionRequestTimeout(3000).setSocketTimeout(5000).build();
            httpPost.setConfig(config);
            System.out.println("请求地址："+url);
            System.out.println("请求参数："+nvps.toString());
            //执行请求操作，并拿到结果（同步阻塞）
            CloseableHttpResponse response = client.execute(httpPost);
            //获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, encoding);
            }
            EntityUtils.consume(entity);
            //释放链接
            response.close();
        }catch (Exception e){
            logger.error("",e);
        }
        return body;
    }

    public static String postJson(String url, String jsonString) {
        String body = "";
        try{
            //创建httpclient对象
            CloseableHttpClient client = HttpClients.createDefault();
            //创建post方式请求对象
            HttpPost httpPost = new HttpPost(url);

            //装填参数
            StringEntity s = new StringEntity(jsonString, "UTF-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            //设置参数到请求对象中
            httpPost.setEntity(s);
            System.out.println("请求地址：" + url);
            //System.out.println("请求参数："+nvps.toString());

            //设置header信息
            //指定报文头【Content-type】、【User-Agent】
            //httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            httpPost.setHeader("Content-type", "application/json");
//            httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //执行请求操作，并拿到结果（同步阻塞）
            CloseableHttpResponse response = client.execute(httpPost);
            //获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, "UTF-8");
            }
            EntityUtils.consume(entity);
            //释放链接
            response.close();
        }catch (Exception e){
            logger.error("",e);
        }
        return body;
    }

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        List<String> list = new ArrayList<>();
        list.add("111");
        jsonObject.put("list",list);
        System.out.println(jsonObject.toJSONString());
//        sendPostJson("http://localhost:8080/api/test",jsonObject.toJSONString());
    }

}
