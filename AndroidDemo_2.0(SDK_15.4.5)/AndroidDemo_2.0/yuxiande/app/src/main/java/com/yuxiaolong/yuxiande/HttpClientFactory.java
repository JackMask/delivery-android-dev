package com.yuxiaolong.yuxiande;

/**
 * Created by jackmask on 2018/3/12.
 */

public class HttpClientFactory {


    private static HttpAsyncClient httpAsyncClient = new HttpAsyncClient();


    private HttpClientFactory() {
    }

    private static HttpClientFactory httpClientFactory = new HttpClientFactory();

    public static HttpClientFactory getInstance() {

        return httpClientFactory;

    }

    public HttpAsyncClient getHttpAsyncClientPool() {
        return httpAsyncClient;
    }



}
