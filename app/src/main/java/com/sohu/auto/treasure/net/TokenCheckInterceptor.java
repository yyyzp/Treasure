package com.sohu.auto.treasure.net;

import com.sohu.auto.treasure.widget.Session;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 为每个网络请求加Token和UserAgent
 */
public class TokenCheckInterceptor implements Interceptor {

    private final static String CONTENT_TYPE = "Content-Type";


    private final static String AUTHORIZATION = "Authorization";

    interface ErrorCodeConst {
        String INVALID_TOKEN = "invalid_auth_token";
        String MISSING_TOKEN = "missing_auth_token";
        String TOKEN_EXPIRED = "auth_token_expired";

        String BAD_USER_CREDENTIAL = "bad_user_credential";
        String INVALID_SMS_CODE = "invalid_smscode";

        String INVALID_REFRESH_TOKEN = "invalid_refresh_token";
        String REFRESH_TOKEN_EXPIRED = "refresh_token_expired";
    }

    public TokenCheckInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

        builder.header(CONTENT_TYPE, "application/json");
        if(Session.getInstance().mAuthToken!=null){
            builder.header(AUTHORIZATION, Session.getInstance().getAuthToken());
        }

        Request modifyRequest = builder.build();
        Response response = chain.proceed(modifyRequest);
//        if (response.code() != 403 && response.code() != 401) {
        return response;
//        }

//        return checkAuth(chain, response, request);
    }

//    private Response checkAuth(Chain chain, Response response, Request request) throws IOException {
//        try {
//            String message = CommonUtils.readStringFromBody(response.body());
//            JsonObject jsonError = new JsonParser().parse(message).getAsJsonObject();
//            String errorCode = jsonError.get("code").getAsString();
//            if (needReLogin(errorCode)) {
//                Session.getInstance().logout();
//                // 清除MAIN之上的Activity
//                RouterManager.getInstance().startActivity(RouterConstant.MAIN_MAIN);
//
//                new Handler(Looper.getMainLooper()).post(() -> {
//                    ToastUtils.show(BaseApplication.getBaseApplication(), "登录已过期，请重新登录!");
//                });
//                return response;
//            } else if (needRefreshToken(errorCode)) {
//                // 延后检查，如果之前的请求重置了refreshToken，则此次请求刷新authToken的要求无效
//                if (Session.getInstance().getRefreshToken() == null)
//                    return response;
//
//                HelperToken helperToken = refreshToken(Session.getInstance().getRefreshToken());
//
//                if (helperToken == null)
//                    return response;
//                Request tokenRequest = chain.request();
//                Request.Builder tokenBuilder = tokenRequest.newBuilder();
//                tokenBuilder.header(ACCESS_TOKEN, Session.getInstance().getAuthToken());
//                return chain.proceed(tokenBuilder.build());
//            }
//        } catch (Exception e) {
//            //错误信息解析失败时不做处理
//            e.printStackTrace();
//            return response;
//        }
//        return chain.proceed(response.request());
//    }
//
//    private HelperToken refreshToken(String refreshToken) throws IOException {
//        AuthParamBuilder paramBuilder = new AuthParamBuilder();
//        paramBuilder.withGrantType(AuthApi.GrantType.REFRESH_TOKEN).withToken(refreshToken);
//        Call<HelperToken> tokenCall = AuthApi.getInstance().refreshToken(paramBuilder.build());
//        retrofit2.Response<HelperToken> response = tokenCall.execute();
//        if (!response.isSuccessful())
//            return null;
//
//        HelperToken token = response.body();
//        Session.getInstance().updateAuthToken(token.authToken);
//
//        Intent intent = new Intent(Constant.UPDATE_SESSION_ACTION);
//        intent.putExtra(Constant.TOKEN, Session.getInstance().getAuthToken());
//        BaseApplication.getBaseApplication().sendBroadcast(intent);
//
//        return token;
//    }
//
//    private boolean needRefreshToken(String errorCode) {
//        return ErrorCodeConst.INVALID_TOKEN.equalsIgnoreCase(errorCode) ||
//                ErrorCodeConst.TOKEN_EXPIRED.equalsIgnoreCase(errorCode);
//    }
//
//    private boolean needReLogin(String errorCode) {
//        return ErrorCodeConst.INVALID_REFRESH_TOKEN.equalsIgnoreCase(errorCode) ||
//                ErrorCodeConst.REFRESH_TOKEN_EXPIRED.equalsIgnoreCase(errorCode);
//    }
}
