package com.sohu.auto.treasure.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.activity.CreateTreasureActivity;
import com.sohu.auto.treasure.activity.SearchTreasureActivity;
import com.sohu.auto.treasure.adapter.BaseAdapter;
import com.sohu.auto.treasure.adapter.UserActionAdapter;
import com.sohu.auto.treasure.entry.EventFeed;
import com.sohu.auto.treasure.net.NetError;
import com.sohu.auto.treasure.net.NetSubscriber;
import com.sohu.auto.treasure.net.TreasureApi;
import com.sohu.auto.treasure.net.response.EventFeedResponse;
import com.sohu.auto.treasure.utils.ToastUtils;
import com.sohu.auto.treasure.utils.TransformUtils;
import com.sohu.auto.treasure.widget.InputPasswordDialog;
import com.sohu.auto.treasure.widget.Session;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;


/**
 * Created by zhipengyang on 2019/4/8.
 */

public class TreasureFragment extends LazyLoadBaseFragment {

    TextView tvOfficialAction;
    TextView tvCreateAction;
    RecyclerView recyclerView;
    private UserActionAdapter mAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_treasure;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        tvOfficialAction = rootView.findViewById(R.id.tv_offical_action);
        tvCreateAction = rootView.findViewById(R.id.tv_create_action);
        recyclerView = rootView.findViewById(R.id.recyclerview);
        mAdapter = new UserActionAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        initData();
        initListener();
    }

    private void initData() {
        TreasureApi
                .getInstance()
                .getEventList()
                .compose(TransformUtils.defaultNetConfig((RxAppCompatActivity) getContext()))
                .subscribe(new NetSubscriber<EventFeedResponse>() {
                    @Override
                    public void onSuccess(EventFeedResponse eventFeedResponse) {
                        mAdapter.setData(eventFeedResponse.data);
                    }

                    @Override
                    public void onFailure(NetError error) {

                    }
                });
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View item, int position) {
                if (!Session.getInstance().isLogin()) {
                    ToastUtils.show(TreasureFragment.this.getContext(), "寻宝需要先登录");
                    return;
                }

                EventFeed event = mAdapter.getItem(position);
                if (!TextUtils.isEmpty(event.secret)) {
                    InputPasswordDialog dialog = new InputPasswordDialog(getContext());
                    dialog.withConfirmClickListener((v, inputText) -> {
                        if (event.secret.equals(inputText)) {
                            startActivity(new Intent(getActivity(), SearchTreasureActivity.class));
                        } else {
                            ToastUtils.show(getContext(), "密码错误！");
                        }
                        dialog.dismiss();
                    })
                            .withCancelClickListener((v, inputText) -> {
                                dialog.dismiss();
                            }).show();
                } else {
                    startActivity(new Intent(getActivity(), SearchTreasureActivity.class));
                }
            }
        });
    }

    private void initListener() {
        tvOfficialAction.setOnClickListener(v -> {
//            InputPasswordDialog dialog = new InputPasswordDialog(getContext());
//            dialog.withConfirmClickListener((view, inputText) -> {
//                if ("1234".equals(inputText)) {
//                    startActivity(new Intent(getActivity(), SearchTreasureActivity.class));
//                } else {
//                    ToastUtils.show(getContext(), "密码错误！");
//                }
//                dialog.dismiss();
//            })
//                    .withCancelClickListener((view, inputText) -> {
//                        dialog.dismiss();
//                    }).show();
            startActivity(new Intent(getActivity(), SearchTreasureActivity.class));
        });
        tvCreateAction.setOnClickListener(v -> {
            if (!Session.getInstance().isLogin()) {
                ToastUtils.show(TreasureFragment.this.getContext(), "要创建宝藏请先登录!");
                return;
            }

            startActivity(new Intent(getActivity(), CreateTreasureActivity.class));
        });
    }

    @Override
    protected void lazyLoad() {

    }


}
