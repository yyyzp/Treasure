package com.sohu.auto.treasure.net;

import com.sohu.auto.treasure.entry.LoginParam;
import com.sohu.auto.treasure.entry.TreasureListEntity;
import com.sohu.auto.treasure.entry.TreasureListParam;
import com.sohu.auto.treasure.entry.User;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by zhipengyang on 2019/4/10.
 */

public class TreasureApi {

    private final static String BASE_URL = "http://10.2.154.227:8088/";

    private TreasureApi() {
    }

    public static Api getInstance() {
        return ServiceFactory.createService(BASE_URL, Api.class);
    }

    public interface Api {

        @POST("api/login")
        Observable<Response<User>> login(@Body LoginParam param);

        @POST("api/treasurelist/{activity_id}")
        Observable<Response<TreasureListEntity>> getTreasurelist(@Path("activity_id") String activityId, @Body TreasureListParam param);

    }
}
