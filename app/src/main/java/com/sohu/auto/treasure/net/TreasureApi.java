package com.sohu.auto.treasure.net;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by zhipengyang on 2019/4/10.
 */

public class TreasureApi {

    private TreasureApi() {
    }

    public static Api getInstance() {
        return ServiceFactory.createService(Api.class);
    }

    public interface Api {
        @GET
        Observable<Void> getBaidu(@Url String url);
    }
}
