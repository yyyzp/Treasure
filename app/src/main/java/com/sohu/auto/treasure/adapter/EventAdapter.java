package com.sohu.auto.treasure.adapter;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.entry.EventFeed;

/**
 * Created by aiyou on 2019/4/11.
 */

public class EventAdapter extends BaseAdapter<EventFeed> {

    @Override
    public BaseViewHolder<EventFeed> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventViewHolder(R.layout.item_user_action, parent, false);
    }

    private class EventViewHolder extends BaseViewHolder<EventFeed> {
        private TextView tvTitle;
        private TextView tvTime;
        private TextView tvNum;

        public EventViewHolder(int resource, @Nullable ViewGroup root, boolean attachToRoot) {
            super(resource, root, attachToRoot);
            tvTitle = itemView.findViewById(R.id.text);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvNum = itemView.findViewById(R.id.tv_num);
        }

        @Override
        public void setData(EventFeed event, int position) {
            tvTitle.setText(event.title);
            tvTime.setText(event.createdAt.substring(0, 10));
            tvNum.setText((event.boxIds == null ? 0 : event.boxIds.size()) + "个宝藏");
        }
    }
}
