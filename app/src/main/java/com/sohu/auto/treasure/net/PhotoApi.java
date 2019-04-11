package com.sohu.auto.treasure.net;

import com.sohu.auto.treasure.entry.RemotePicture;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by aiyou on 2019/4/11.
 */

public class PhotoApi {

    private final static String BASE_URL = "http://photo.auto.sohuno.com/";

    private PhotoApi() {}

    public static Api getInstance() {
        return ServiceFactory.createService(BASE_URL, Api.class);
    }

    public interface Api {
        @Multipart
        @POST("api/photos")
        Observable<Response<RemotePicture>> uploadImg(@Part("client_id") RequestBody clientId,
                                                      @Part MultipartBody.Part part);
    }
}
