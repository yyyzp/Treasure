package com.sohu.auto.treasure.net;

import com.sohu.auto.treasure.entry.LoginParam;
import com.sohu.auto.treasure.entry.TreasureEvent;
import com.sohu.auto.treasure.entry.TreasureListEntity;
import com.sohu.auto.treasure.entry.TreasureListParam;
import com.sohu.auto.treasure.entry.User;
import com.sohu.auto.treasure.net.response.EventFeedResponse;
import com.sohu.auto.treasure.net.response.TreasureHistoryResponse;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
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

        @GET("activity/list")
        Observable<Response<EventFeedResponse>> getEventList();

        @POST("activity/create")
        Observable<Response<Void>> createTreasure(@Body TreasureEvent requestBody);

        @POST("login")
        Observable<Response<User>> login(@Body LoginParam param);

        @GET("activitylist/published")
        Observable<Response<EventFeedResponse>> getMyPublished();

        @GET("treasurelist/published/{activityId}")
        Observable<Response<TreasureHistoryResponse>> getPublishedTreasure(@Path("activityId") String activityId);

        @GET("treasurelist/joined/{activityId}")
        Observable<Response<TreasureHistoryResponse>> getJoinedTreasure(@Path("activityId") String activityId);

        @GET("activitylist/joined")
        Observable<Response<EventFeedResponse>> getMyJoined();

        @POST("treasurelist/{activity_id}")
        Observable<Response<TreasureListEntity>> getTreasurelist(@Path("activity_id") String activityId, @Body TreasureListParam param);

        @PUT("treasure/{treasure_id}")
        Observable<Response<Void>> openTreasure(@Path("treasure_id") String treasureId);
    }
}