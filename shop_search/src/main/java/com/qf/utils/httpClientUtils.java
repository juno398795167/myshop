package com.qf.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class httpClientUtils {

    public static String sendJsonPost(String url,String json)  {
        CloseableHttpClient build = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setHeader(new BasicHeader("Content-Type","application/json"));
            httpPost.setEntity(new StringEntity(json,"utf-8"));
            CloseableHttpResponse execute = build.execute(httpPost);
            HttpEntity entity = execute.getEntity();
            String s = EntityUtils.toString(entity);
            System.out.println("111111111111111111111111111111111111111");
            return s;
        }catch (Exception e){
            e.printStackTrace();

        }finally {
            try {
                if (build!=null) {
                    build.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }

}
