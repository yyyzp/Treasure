package com.sohu.auto.treasure.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.entry.Treasure;
import com.sohu.auto.treasure.net.NetError;
import com.sohu.auto.treasure.net.NetSubscriber;
import com.sohu.auto.treasure.net.TreasureApi;
import com.sohu.auto.treasure.utils.ToastUtils;
import com.sohu.auto.treasure.utils.TransformUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhipengyang on 2019/4/9.
 */

public class TreasureDetailDialogFragment extends DialogFragment {

    private DismissListener mDismissListener;
    private Treasure mTreasure;
    private TextView tvText;
    private ImageView ivPic;

    public static TreasureDetailDialogFragment newInstance(Treasure treasure) {
        TreasureDetailDialogFragment treasureDetailDialogFragment = new TreasureDetailDialogFragment();
        treasureDetailDialogFragment.mTreasure = treasure;
        return treasureDetailDialogFragment;
    }

    public static TreasureDetailDialogFragment newInstance(Treasure treasure, DismissListener dismissListener) {
        TreasureDetailDialogFragment treasureDetailDialogFragment = new TreasureDetailDialogFragment();
        treasureDetailDialogFragment.mDismissListener = dismissListener;
        treasureDetailDialogFragment.mTreasure = treasure;
        return treasureDetailDialogFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_treasure_detail, container, false);
        tvText = view.findViewById(R.id.tv_text);
        ivPic = view.findViewById(R.id.iv_pic);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: 2019/4/10  网络请求加载宝箱内容
        tvText.setText(mTreasure.content);
        Glide.with(this).load(mTreasure.imagePath).into(ivPic);
        openTreasure(mTreasure.id);
    }

    private void openTreasure(String treasureId) {
        TreasureApi.getInstance()
                .openTreasure(treasureId)
                .compose(TransformUtils.defaultNetConfig((RxAppCompatActivity) getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetSubscriber<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (getResponse().code() == 204) {
                            ToastUtils.show(getContext(), "开启宝藏成功");
                        }
                    }

                    @Override
                    public void onFailure(NetError error) {

                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDismissListener != null) {
            mDismissListener.onDismiss();
        }
    }


    public interface DismissListener {
        void onDismiss();
    }

}
