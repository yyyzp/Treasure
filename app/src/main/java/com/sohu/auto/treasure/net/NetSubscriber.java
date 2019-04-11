package com.sohu.auto.treasure.net;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Subscriber;

/**
 * http://docs.aves.auto.sohuno.com/ 文档内接口返回内容均采用该类封装
 * Created by chasonchen on 2018/2/1.
 */
public abstract class NetSubscriber<T> extends Subscriber<Response<T>> {

    protected Response<T> mResponse;

    public abstract void onSuccess(T t);

    /**
     * 当NetError返回对象code类型为unknown_error时，需要通过Throwable重新分析错误详情。
     *
     * @param error
     */
    public abstract void onFailure(NetError error);

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable error) {

        NetError netError = new NetError();
        netError.throwable = error;

        try {
            NetError tempError = new Gson().fromJson(error.getMessage(),NetError.class);
            if (null != tempError.status && tempError.status>=500){
                netError.code = NetErrorCode.SERVER_ERROR;
                netError.message = "服务器异常";
                netError.throwable = error;
            }else {
                netError.code = tempError.code;
                netError.message = tempError.message;
            }
        } catch (Exception e) {
            netError.code = NetErrorCode.UNKNOWN_ERROR;
            netError.message = "未知异常";
            netError.throwable = error;
        }
        onFailure(netError);
    }

    @Override
    public void onNext(Response<T> tResponse) {
        mResponse = tResponse;
        if (tResponse.isSuccessful()) {
            onSuccess(tResponse.body());
        } else {
            onError(getThrowable(tResponse.errorBody()));
        }
    }

    public Response<T> getResponse() {
        return mResponse;
    }

    public Throwable getThrowable(ResponseBody errorBody) {
        if (null == errorBody) return null;

        try {
            return new Throwable(errorBody.string());
        } catch (IOException e) {
            return e;
        }
    }
}
