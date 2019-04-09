package com.sohu.auto.treasure.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.viewholder.UserActionViewHolder;

import java.util.List;

/**
 * Created by zhipengyang on 2019/4/9.
 */

public class UserActionAdapter extends BaseAdapter<String> {


    @Override
    public BaseViewHolder<String> onCreateViewHolder(ViewGroup parent, int viewType) {

        return new UserActionViewHolder(R.layout.item_user_action,parent,false);
    }


}
