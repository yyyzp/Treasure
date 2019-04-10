package com.sohu.auto.treasure.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sohu.auto.treasure.R;

/**
 * Created by zhipengyang on 2019/4/9.
 */

public class TreasureDetailDialogFragment extends DialogFragment {

    private DismissListener mDismissListener;
    public  static  TreasureDetailDialogFragment newInstance() {
        TreasureDetailDialogFragment treasureDetailDialogFragment = new TreasureDetailDialogFragment();
        return treasureDetailDialogFragment;
    }
    public  static  TreasureDetailDialogFragment newInstance(DismissListener dismissListener) {
        TreasureDetailDialogFragment treasureDetailDialogFragment = new TreasureDetailDialogFragment();
        treasureDetailDialogFragment.mDismissListener = dismissListener;
        return treasureDetailDialogFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_treasure_detail, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: 2019/4/10  网络请求加载宝箱内容 
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mDismissListener!=null){
            mDismissListener.onDismiss();
        }
    }


    public interface DismissListener {
        void onDismiss();
    }

}
