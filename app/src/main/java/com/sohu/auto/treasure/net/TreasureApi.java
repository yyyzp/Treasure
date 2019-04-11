package com.sohu.auto.treasure.net;

import com.sohu.auto.treasure.entry.TreasureEvent;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by zhipengyang on 2019/4/10.
 */

public class TreasureApi {

    private final static String BASE_URL = "http://10.2.154.227:8088/api/";

    private TreasureApi() {
    }

    public static Api getInstance() {
        return ServiceFactory.createService(BASE_URL, Api.class);
    }

    public interface Api {
        @GET
        Observable<Void> getBaidu(@Url String url);

        @Headers({"Content-Type: application/json"})
        @POST("activity/create")
        Observable<Response<Void>> createTreasure(@Header("Authorization") String authToken, @Body TreasureEvent requestBody);
    }
}
