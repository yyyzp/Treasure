package com.sohu.auto.treasure.viewholder;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.adapter.BaseAdapter;

/**
 * Created by zhipengyang on 2019/4/9.
 */

public class UserActionViewHolder extends BaseAdapter.BaseViewHolder<String> {

    TextView text;

    public UserActionViewHolder(int resource, @Nullable ViewGroup root, boolean attachToRoot) {
        super(resource, root, attachToRoot);
        text = itemView.findViewById(R.id.text);
    }

    @Override
    public void setData(String feed, int position) {
        text.setText(feed);
    }
}
