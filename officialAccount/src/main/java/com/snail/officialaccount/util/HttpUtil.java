package com.snail.officialaccount.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Author jiaSong.pi
 * @Date 2022/9/20 23:44
 */
@Slf4j
public class HttpUtil {
    private volatile static OkHttpClient okHttpClient;
    private HttpUtil() {}

    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            synchronized (HttpUtil.class) {
                Dispatcher dispatcher = new Dispatcher();
                dispatcher.setMaxRequestsPerHost(64);
                dispatcher.setMaxRequests(128);
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .connectionPool(new ConnectionPool())
                            .dispatcher(dispatcher)
                            .build();
                }
            }
        }
        return okHttpClient;
    }
}
