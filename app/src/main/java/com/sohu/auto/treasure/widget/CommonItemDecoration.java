package com.sohu.auto.treasure.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sohu.auto.treasure.R;

/**
 * Created by aiyou on 2019/4/10.
 */

public class CommonItemDecoration extends RecyclerView.ItemDecoration {
    private final static int DEFAULT_HEIGHT = 1;
    private Paint mPaint;
    private int height;

    public CommonItemDecoration(Context context) {
        this(context, DEFAULT_HEIGHT);
    }

    public CommonItemDecoration(Context context, int height) {
        super();
        this.height = height;
        mPaint = new Paint();
        mPaint.setColor(context.getResources().getColor(R.color.cG3));
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int childCount = parent.getChildCount();
        for(int i = 0; i < childCount - 1; i++) {
            final View child = parent.getChildAt(i);
            c.drawRect(getDrawRectBottom(parent, child), mPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int totalCount = parent.getAdapter().getItemCount();
        outRect.set(0, 0, 0, position == totalCount - 1 ? 0 : height);
    }

    public Rect getDrawRectBottom(RecyclerView parent, View child) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        final RecyclerView.LayoutParams params =
                (RecyclerView.LayoutParams) child.getLayoutParams();
        int top = child.getBottom() + params.bottomMargin;
        int bottom = top + height;
        int leftChild = left + child.getPaddingLeft();
        int rightChild = right - child.getPaddingRight();
        return new Rect(leftChild, top, rightChild, bottom);
    }
}
