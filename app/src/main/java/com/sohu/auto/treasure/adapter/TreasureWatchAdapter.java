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
        return new TreasureWatchViewHolder(R.layout.item_treasure_watch, parent, false);
    }

    private class TreasureWatchViewHolder extends BaseViewHolder<TreasureHistory> {

        private TextView tvTitle;
        private TextView tvOpenTime;
        private TextView tvOpenMan;

        public TreasureWatchViewHolder(int resource, @Nullable ViewGroup root, boolean attachToRoot) {
            super(resource, root, attachToRoot);
            tvTitle = itemView.findViewById(R.id.tv_treasure_title);
            tvOpenTime = itemView.findViewById(R.id.tv_open_time);
            tvOpenMan = itemView.findViewById(R.id.tv_open_man);
        }

        @Override
        public void setData(TreasureHistory treasure, int position) {
            tvTitle.setText(treasure.name + "," + treasure.location);
            tvOpenTime.setText("开启时间：" + CommonUtils.formatDate(treasure.openTime, CommonUtils.SDF_YYYY_MM_DD_HH_MM));
            tvOpenMan.setText("开启人：" + treasure.openMan);
        }
    }
}
