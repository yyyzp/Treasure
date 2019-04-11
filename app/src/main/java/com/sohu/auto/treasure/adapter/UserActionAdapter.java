package com.sohu.auto.treasure.adapter;

import android.view.ViewGroup;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.entry.EventFeed;
import com.sohu.auto.treasure.viewholder.UserActionViewHolder;

/**
 * Created by zhipengyang on 2019/4/9.
 */

public class UserActionAdapter extends BaseAdapter<EventFeed> {


    @Override
    public BaseViewHolder<EventFeed> onCreateViewHolder(ViewGroup parent, int viewType) {

        return new UserActionViewHolder(R.layout.item_user_action,parent,false);
    }


}
