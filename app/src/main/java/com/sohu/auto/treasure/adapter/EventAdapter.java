package com.sohu.auto.treasure.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.entry.EventHistory;

/**
 * Created by aiyou on 2019/4/11.
 */

public class EventAdapter extends BaseAdapter<EventHistory> {

    @Override
    public BaseViewHolder<EventHistory> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventViewHolder(R.layout.item_event_history, parent, false);
    }

    private class EventViewHolder extends BaseViewHolder<EventHistory> {
        private ImageView ivCover;
        private TextView tvTitle;

        public EventViewHolder(int resource, @Nullable ViewGroup root, boolean attachToRoot) {
            super(resource, root, attachToRoot);
            ivCover = itemView.findViewById(R.id.iv_cover);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }

        @Override
        public void setData(EventHistory event, int position) {
            tvTitle.setText(event.title);
        }
    }
}
