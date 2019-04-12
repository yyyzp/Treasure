package com.sohu.auto.treasure.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhipengyang on 2019/4/9.
 */

public abstract class BaseAdapter<E> extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder<E>> {

    OnItemClickListener onItemClickListener;

    protected List<E> mItems;

    public BaseAdapter() {
        super();
        mItems = new ArrayList<>();
    }

    public void setData(List list) {
        if (list != null) {
            mItems = list;
            notifyDataSetChanged();
        }
    }

    public E getItem(int position) {
        if (position >= getItemCount())
            return null;

        return mItems.get(position);
    }

    public void addData(List list) {
        if (list != null && list.size() > 0) {
            int pos = mItems.size();
            mItems.addAll(list);
            notifyItemChanged(pos);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<E> holder, int position) {
        E item = mItems.get(position);
        holder.setData(item, position);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View item, int position);
    }

    public static abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public BaseViewHolder(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot) {
            super(LayoutInflater.from(root.getContext()).inflate(resource, root, attachToRoot));
        }

        public abstract void setData(T feed, int position);

    }
}
