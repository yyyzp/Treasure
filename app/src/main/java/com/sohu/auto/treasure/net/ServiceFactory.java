package com.sohu.auto.treasure.net;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhipengyang on 2019/4/10.
 */

public class ServiceFactory {
    private static final GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(new GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create());

//    private static final String baseUrl = "http://www.baidu.com";
    private static OkHttpClient sOkHttpClient;
    private static final long CONNECTION_TIMEOUT = 30L;

    public static <T> T createService(String baseUrl, Class<T> serviceClass) {
        return getRetrofitBuilder(baseUrl, 0).build().create(serviceClass);
    }

    public static Retrofit.Builder getRetrofitBuilder(String baseUrl, int timeout) {
        return new Retrofit.Builder()
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(getOkHttpClient(timeout));
    }

    public static OkHttpClient getOkHttpClient(int timeOut) {
        if (sOkHttpClient == null) {
            OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();
            okClientBuilder.addInterceptor(getLoggingInterceptor());
            okClientBuilder.addInterceptor(new TokenCheckInterceptor());
            setTimeOut(timeOut,okClientBuilder);
            sOkHttpClient = okClientBuilder.build();
        }
        return sOkHttpClient;
    }

    private static void setTimeOut(int timeOut, OkHttpClient.Builder builder) {
        if (timeOut == 0) {
            builder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS).readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS).writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        } else {
            builder.connectTimeout(timeOut, TimeUnit.SECONDS).readTimeout(timeOut, TimeUnit.SECONDS).writeTimeout(timeOut, TimeUnit.SECONDS);
        }
    }

    private static HttpLoggingInterceptor getLoggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        return  httpLoggingInterceptor;

    }
}
