package com.sohu.auto.treasure.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sohu.auto.treasure.R;


/**
 * desc : 通用actionar    img tv title tv img  形式
 * <p>
 * from 2017.8.8 以后 替换toolbar
 * 旧逻辑 ：baseapptheme 设置整个app 沉浸式 不需要的使用 {@style/ActivityStatusBarColor} fitSystemwindow
 * 沉浸式需设置+25 statusbar高度  由value 版本控制 。而且现在版本中对颜色控制有许多不合理的地方，不完全透明版本有待考虑是否该半透明沉浸
 * <p>
 * 新逻辑：直接替换即可
 * 合理逻辑 整体不使用沉浸式 只在需要的xml设置对应status属性。
 * <p>
 * <p>
 * <p>
 * * Created by : leoyin
 */

public class SHAutoActionbar extends FrameLayout {
    private View mTitleLine;
    private Context context;
    private TextView mTitleTv;
    private TextView mLeftTv;
    private ImageView mLeftIv;
    private CharSequence mTitle;
    private TextView mRightTv;
    private ImageView mRightIv;
    private ImageView mRightIv1;
    private ImageView mCloseIv;
    private CharSequence mLeftTx;
    private int mLeftImg;
    private CharSequence mRightTx;
    private int mRightImg;
    private int mRightImg1;

    private ActionBarType mType;
    private ActionBarListener mListener;
    private int typeIndex;
    private boolean mInterruptBackEvent;

    private static final ActionBarType[] sTypeArray = {
            ActionBarType.COMMON,
            ActionBarType.COMMON_WEB,
            ActionBarType.TRANSPARENT_SHARE,
            ActionBarType.DOWNLOAD_PIC,
            ActionBarType.COMMON_FROM_BOTTOM,
            ActionBarType.RIGHT_ICON,
            ActionBarType.DOWNLOAD_PIC_SHARE
    };

    public SHAutoActionbar(Context context) {
        this(context, null);
    }

    public SHAutoActionbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SHAutoActionbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.sh_actionbar_common_pinned, this, true);
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mLeftTv = (TextView) findViewById(R.id.left_btn_tv);
        mLeftIv = (ImageView) findViewById(R.id.left_btn_iv);
        mRightTv = (TextView) findViewById(R.id.right_btn_tv);
        mRightIv = (ImageView) findViewById(R.id.right_btn_iv);
        mRightIv1 = (ImageView) findViewById(R.id.right_btn_iv1);
        mCloseIv = (ImageView) findViewById(R.id.left_close_iv);
        mTitleLine = findViewById(R.id.title_line);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SHAutoActionBar);

        mTitle = ta.getText(R.styleable.SHAutoActionBar_actionbar_title);
        mLeftTx = ta.getText(R.styleable.SHAutoActionBar_actionbar_left_tv);
        mLeftImg = ta.getResourceId(R.styleable.SHAutoActionBar_actionbar_left_iv, R.drawable.v_system_back);
        mRightTx = ta.getText(R.styleable.SHAutoActionBar_actionbar_right_tv);
        mRightImg = ta.getResourceId(R.styleable.SHAutoActionBar_actionbar_right_iv, 0);
        mRightImg1 = ta.getResourceId(R.styleable.SHAutoActionBar_actionbar_right_1_iv, 0);
        typeIndex = ta.getInt(R.styleable.SHAutoActionBar_actionbar_type, 0);
        ta.recycle();

        mInterruptBackEvent = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(
                (int) getResources().getDimension(R.dimen.action_bar_height), MeasureSpec.EXACTLY));
    }

    /**
     * 内容设置结束.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setTitle(mTitle);
        setLeftTx(mLeftTx);
        setRightTx(mRightTx);
        if (TextUtils.isEmpty(mLeftTx)) {
            mLeftIv.setVisibility(View.VISIBLE);
            mLeftIv.setImageResource(mLeftImg);
            if (context instanceof Activity) {
                mLeftIv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((Activity) context).finish();
                    }
                });
            }
        } else {
            mLeftIv.setVisibility(View.GONE);
        }
        setRightImg(mRightImg);
        setRightImg1(mRightImg1);
        if (typeIndex >= 0) {
            setType(sTypeArray[typeIndex]);
        }
    }

    public void setLeftTxColor(@ColorInt int color) {
        mLeftTv.setTextColor(color);
    }

    private void setLeftTx(CharSequence text) {
        if (!TextUtils.isEmpty(text) && mLeftTv != null) {
            mLeftTv.setVisibility(View.VISIBLE);
            mLeftTv.setText(text);
        }
    }

    public void setRightTvColor(@ColorInt int color) {
        mRightTv.setTextColor(color);
    }

    public void setRightTx(CharSequence text) {
        if (!TextUtils.isEmpty(text) && mRightTv != null) {
            mRightTv.setVisibility(View.VISIBLE);
            mRightTv.setText(text);
        }
    }

    public void hideRightTx() {
        mRightTv.setVisibility(View.GONE);
    }

    public void showRightTx() {
        if (mRightTv != null)
            mRightTv.setVisibility(VISIBLE);
    }

    public void setRightTxEnabled(boolean enabled) {
        if (mRightTv != null)
            mRightTv.setEnabled(enabled);
    }

    public int getRightTxVisibility() {
        return mRightTv.getVisibility();
    }

    public void setRightImg(int id) {
        if (id != 0 && mRightIv != null) {
            mRightIv.setVisibility(View.VISIBLE);
            mRightIv.setImageResource(id);
        }
    }

    public void setRightImg1(int id) {
        if (id != 0 && mRightIv1 != null) {
            mRightIv1.setVisibility(View.VISIBLE);
            mRightIv1.setImageResource(id);
        }
    }

    public void setTitle(CharSequence title) {
        if (null != mTitleTv) {
            mTitle = title;
            mTitleTv.setText(title);
        }
    }

    public void setTitleLineVisible(boolean visible) {
        if (null != mTitleLine) {
            if (visible) {
                mTitleLine.setVisibility(VISIBLE);
            } else {
                mTitleLine.setVisibility(GONE);
            }
        }
    }
    public void performclick(ActionBarEvent event) {
        if (mListener == null)
            return;
        mListener.onEvent(event);
    }

    public void setListener(final ActionBarListener listener) {
        if (listener == null) {
            return;
        }
        mListener = listener;

        mRightTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEvent(ActionBarEvent.RIGHT_TEXT_CLICK);
            }
        });

        mCloseIv.setOnClickListener(v -> {
            listener.onEvent(ActionBarEvent.CLOSE_CLICK);
        });

        mLeftTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEvent(ActionBarEvent.LEFT_TEXT_CLICK);
            }
        });

        mRightIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEvent(ActionBarEvent.RIGHT_IMG_CLICK);
            }
        });

        mRightIv1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEvent(ActionBarEvent.RIGHT_IMG_CLICK1);
            }
        });

        mLeftIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 目前所有场景都是finish，后续如果有新的需求需要不同逻辑，可以根据mType进行判断
                if (context instanceof Activity && !mInterruptBackEvent) {
//                    if (((FragmentActivity) context).getSupportFragmentManager().getBackStackEntryCount() > 1) {
//                        ((FragmentActivity) context).getSupportFragmentManager().popBackStack();
//                    } else {
//                        ((FragmentActivity) context).finish();
//                    }
                    ((Activity) context).onBackPressed();
                } else {
                    listener.onEvent(ActionBarEvent.LEFT_IMG_CLICK);
                }
            }
        });
    }

    public void setType(ActionBarType type) {
        if (type == null) {
            throw new NullPointerException();
        }

        if (mType != type) {
            mType = type;
            updateType();
        }
    }

    public void setInterruptBackEvent(boolean interrupt) {
        mInterruptBackEvent = interrupt;
    }

    /**
     * 布局可见的仅有mLeftIv和mTitleTv，类型只是定一些大概通用的样式，比如如果需要红底，和mRightTv，用COMMON_RED即可，
     * 然后再单独设置右Text，对应的setRightTx方法会将其设为可见
     *
     * @see #setRightTx(CharSequence)
     */
    private void updateType() {
        switch (mType) {
            case COMMON:
                setBackgroundColor(getResources().getColor(R.color.white));
                mTitleTv.setTextColor(getResources().getColor(R.color.cG1));
                mLeftIv.setImageResource(mLeftImg);
                mCloseIv.setVisibility(GONE);
                break;
            case COMMON_WEB:
                setBackgroundColor(getResources().getColor(R.color.white));
                mTitleTv.setTextColor(getResources().getColor(R.color.cG1));
                mTitleTv.setVisibility(View.VISIBLE);
                mLeftIv.setVisibility(View.VISIBLE);
                mLeftIv.setImageResource(R.drawable.v_system_back);
                mCloseIv.setVisibility(VISIBLE);
                break;
            case COMMON_FROM_BOTTOM:
            default:
                setBackgroundColor(getResources().getColor(R.color.white));
                mTitleTv.setTextColor(getResources().getColor(R.color.cG1));
                mLeftIv.setImageResource(R.drawable.v_system_close);
                mCloseIv.setVisibility(GONE);
                break;
        }
    }

    public enum ActionBarType {
        COMMON,
        COMMON_WEB,// 带返回和X的Web样式
        TRANSPARENT_SHARE,// 背景是透明的分享样式
        DOWNLOAD_PIC,// 下载图片
        COMMON_FROM_BOTTOM,//从底部滑出的样式
        RIGHT_ICON,//右侧按钮点击
        DOWNLOAD_PIC_SHARE// 下载图片
    }

    public interface ActionBarListener {
        /**
         * 布局点击回调接口
         *
         * @param event 使用ActionBarEvent枚举分类，客户端通过event进行相应回调事件调用
         */
        void onEvent(final ActionBarEvent event);
    }

    public enum ActionBarEvent {
        LEFT_TEXT_CLICK,
        LEFT_IMG_CLICK,
        RIGHT_TEXT_CLICK,
        RIGHT_IMG_CLICK,
        RIGHT_IMG_CLICK1,
        CLOSE_CLICK
    }
}