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
        return new EventViewHolder(R.layout.item_event_history, parent, false);
    }

    private class EventViewHolder extends BaseViewHolder<EventFeed> {
        private ImageView ivCover;
        private TextView tvTitle;

        public EventViewHolder(int resource, @Nullable ViewGroup root, boolean attachToRoot) {
            super(resource, root, attachToRoot);
            ivCover = itemView.findViewById(R.id.iv_cover);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }

        @Override
        public void setData(EventFeed event, int position) {
            tvTitle.setText(event.title);
        }
    }
}
