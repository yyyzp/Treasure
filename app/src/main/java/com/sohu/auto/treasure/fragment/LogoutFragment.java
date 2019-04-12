package com.sohu.auto.treasure.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.entry.LoginParam;
import com.sohu.auto.treasure.entry.User;
import com.sohu.auto.treasure.net.NetError;
import com.sohu.auto.treasure.net.NetSubscriber;
import com.sohu.auto.treasure.net.TreasureApi;
import com.sohu.auto.treasure.utils.TransformUtils;
import com.sohu.auto.treasure.widget.Session;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhipengyang on 2019/4/9.
 * <p>
 * 退出状态
 */

public class LogoutFragment extends LazyLoadBaseFragment {

    TextView tv_login;
    TextView iv_pic;
    EditText ed_account;
    EditText ed_password;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_me_logout;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        ed_account = (EditText) findViewById(R.id.ed_account);
        ed_password = (EditText) findViewById(R.id.ed_password);
        tv_login = rootView.findViewById(R.id.tv_login);
        tv_login.setOnClickListener(v -> {
            login();
        });
    }

    private void login() {
        if (!TextUtils.isEmpty(ed_account.getText()) && !TextUtils.isEmpty(ed_password.getText())) {
            String name = ed_account.getText().toString();
            String passWord = ed_password.getText().toString();
            LoginParam param = new LoginParam(name, passWord);
            TreasureApi.getInstance()
                    .login(param)
                    .compose(TransformUtils.defaultNetConfig((RxAppCompatActivity) getContext()))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new NetSubscriber<User>() {
                        @Override
                        public void onSuccess(User user) {
                            Session.getInstance().setUserAndToken(user);
                            MeFragment fragment = (MeFragment) getParentFragment();
                            fragment.checkIsLogin(true);
                        }

                        @Override
                        public void onFailure(NetError error) {
                            Toast.makeText(getContext(), "登录失败", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    @Override
    protected void lazyLoad() {

    }
}
