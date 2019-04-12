package com.sohu.auto.treasure.viewholder;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.adapter.BaseAdapter;
import com.sohu.auto.treasure.entry.EventFeed;

/**
 * Created by zhipengyang on 2019/4/9.
 */

public class UserActionViewHolder extends BaseAdapter.BaseViewHolder<EventFeed> {

    TextView text;
    TextView tvNum;
    TextView tvCreateTime;

    public UserActionViewHolder(int resource, @Nullable ViewGroup root, boolean attachToRoot) {
        super(resource, root, attachToRoot);
        text = itemView.findViewById(R.id.text);
        tvNum = itemView.findViewById(R.id.tv_num);
        tvCreateTime = itemView.findViewById(R.id.tv_time);
    }

    @Override
    public void setData(EventFeed feed, int position) {
        text.setText(feed.title);
        tvCreateTime.setText(feed.createdAt.substring(0, 10));
        tvNum.setText((feed.boxIds == null ? 0 : feed.boxIds.size()) + "个宝藏");
    }
}
