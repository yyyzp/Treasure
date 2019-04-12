package com.sohu.auto.treasure.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.entry.Treasure;
import com.sohu.auto.treasure.entry.TreasureHistory;
import com.sohu.auto.treasure.utils.CommonUtils;

/**
 * Created by aiyou on 2019/4/10.
 */

public class TreasureWatchAdapter extends BaseAdapter<BaseAdapter.BaseViewHolder<TreasureHistory>> {

    @Override
    public BaseAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TreasureWatchViewHolder(R.layout.item_treasure, parent, false);
    }

    private class TreasureWatchViewHolder extends BaseViewHolder<TreasureHistory> {

        private TextView tvTitle;
        private TextView tvOpenTime;
        private TextView tvOpenMan;

        public TreasureWatchViewHolder(int resource, @Nullable ViewGroup root, boolean attachToRoot) {
            super(resource, root, attachToRoot);
            tvTitle = itemView.findViewById(R.id.text);
            tvOpenTime = itemView.findViewById(R.id.tv_time);
            tvOpenMan = itemView.findViewById(R.id.tv_open_man);
        }

        @Override
        public void setData(TreasureHistory treasure, int position) {
            tvTitle.setText(treasure.locationStr);

            if (treasure.status == 0) {
                tvOpenTime.setText("未开启");
                tvOpenMan.setVisibility(View.GONE);
                return;
            }

            tvOpenTime.setText(treasure.updatedAt.substring(0, 10) + " " + treasure.updatedAt.substring(11, 16));

            if (treasure.creatorId.equals(treasure.hunterId)) {
                tvOpenMan.setVisibility(View.GONE);
            } else {
                tvOpenMan.setVisibility(View.VISIBLE);
                tvOpenMan.setText(treasure.hunterName);
            }
        }
    }
}
