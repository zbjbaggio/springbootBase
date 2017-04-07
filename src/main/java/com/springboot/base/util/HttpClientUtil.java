package com.springboot.base.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by mentongwu on 16-1-11.
 *
 */
public class HttpClientUtil {

    private static int socketTimeout = -1;

    //传输超时时间，默认30秒
    private static int connectTimeout = 60000;

    private static final Logger LOG = LoggerFactory.getLogger(HttpClientUtil.class);

    private static Executor executor = Executors.newCachedThreadPool();

//    public static void postAsync(String url, String json){
//        executor.execute(() -> post( url,  json));
//    }

    public static void async(Runnable runnable){
        executor.execute(runnable);
    }

    public static String post(String url, String jsonObject) {
        LOG.info("【請求結算接口-{}】【开始】【参数：{}】, 开始时间:【{}】", url, jsonObject, DateUtil.formatTime(new Date()));
        String result = null;
        CloseableHttpClient httpClient = HttpClients.custom().build();
        HttpPost httpPost = new HttpPost(url);

        //解决XStream对出现双下划线的bug
      /*  XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

        //将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = xStreamForRequestPostData.toXML(xmlObj);*/

        /*Util.log("API，POST过去的数据是：");
        Util.log(postDataXML);*/

        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(jsonObject, "UTF-8");
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        httpPost.setEntity(postEntity);
        //设置请求器的配置
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        httpPost.setConfig(requestConfig);
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            LOG.error(url + "http请求错误", e);
        } finally {
            httpPost.abort();
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOG.info("【請求結算接口-{}】【返回参数:{}】", url, result);
        return result;
    }

    public static <T> T postJson(String url, Object param, Class<? extends T> responseClazz){
        String requestJsonStr = JSON.toJSONString(param);
        String responseJsonStr = post(url, requestJsonStr);
        return JSON.parseObject(responseJsonStr, responseClazz);
    }
}
