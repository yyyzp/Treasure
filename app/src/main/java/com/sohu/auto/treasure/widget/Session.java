package com.sohu.auto.treasure.widget;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.sohu.auto.treasure.BaseApplication;
import com.sohu.auto.treasure.entry.User;

import java.util.HashMap;

/**
 * Created by zhipengyang on 2019/4/11.
 */

public class Session {
    public static HashMap<String, String> userMap = new HashMap();
    private final static String SP_KEY_USER = "sp_key_user";
    private final static String SP_SESSION_INFO = "sp_session_info.sp";

    private Gson mGson;
    private SharedPreferences mPreference;
    public User mUser;
    public String mAvatar;
    public String mNickName;
    public String mAuthToken;


    static {
        userMap.put("souhu1", "123456");
        userMap.put("souhu2", "123456");
        userMap.put("souhu3", "123456");
        userMap.put("souhu4", "123456");
        userMap.put("souhu5", "123456");
        userMap.put("souhu6", "123456");
    }

    private Session() {
        mGson = new Gson();
        mPreference = BaseApplication
                .getBaseApplication()
                .getSharedPreferences(SP_SESSION_INFO, Context.MODE_PRIVATE);

        mUser = mGson.fromJson(mPreference.getString(SP_KEY_USER, null), User.class);
        if (mUser != null) {
            mAuthToken = mUser.getData().getToken();
            mAvatar = mUser.getData().getUser_info().getAvatar();
            mNickName = mUser.getData().getUser_info().getNickname();
        }
    }

    public static Session getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static class InstanceHolder {
        static Session INSTANCE = new Session();
    }

    public void setUserAndToken(User user) {
        mUser = user;
        mAuthToken = user.getData().getToken();
        mAvatar = mUser.getData().getUser_info().getAvatar();
        mNickName = mUser.getData().getUser_info().getNickname();
        mPreference
                .edit()
                .putString(SP_KEY_USER, mGson.toJson(user))
                .apply();
    }

    public String getAuthToken() {
        return "Bearer " + mAuthToken;
    }

    public boolean isLogin() {
        return mUser != null && mAuthToken != null;
    }

    public void logout() {
        mUser = null;
        mAuthToken = null;
        mAvatar = null;
        mNickName = null;
        mPreference
                .edit()
                .remove(SP_KEY_USER)
                .apply();
    }

}
