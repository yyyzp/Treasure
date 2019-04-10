package com.sohu.auto.treasure.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.entry.Treasure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aiyou on 2019/4/10.
 */

public class TreasureToAddAdapter extends RecyclerView.Adapter {

    private List<Treasure> data = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TreasureViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_treasure_to_add, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TreasureViewHolder)holder).initWithData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Treasure> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addItem(Treasure treasure) {
        data.add(treasure);
        notifyItemInserted(data.size() - 1);
    }

    public List<Treasure> getCurrentTreasure() {
        return data;
    }

    private class TreasureViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTreasureDetail;

        public TreasureViewHolder(View itemView) {
            super(itemView);
            tvTreasureDetail = itemView.findViewById(R.id.tv_treasure_detail);
        }

        private void initWithData(Treasure treasure) {
            tvTreasureDetail.setText(treasure.location + "," + (treasure.text == null ? "" : treasure.text));
        }
    }
}
