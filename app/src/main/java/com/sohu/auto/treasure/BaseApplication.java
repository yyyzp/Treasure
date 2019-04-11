package com.sohu.auto.treasure;

import android.app.Application;
import android.content.Context;

import com.sohu.auto.treasure.widget.Session;

import java.io.Serializable;

public  class BaseApplication extends Application implements Serializable {

    private static Application mApplication;
    public static Application getBaseApplication() {
        return mApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        Session.getInstance();
    }


}
