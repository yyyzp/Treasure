package com.sohu.auto.treasure.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.sohu.auto.treasure.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 波纹动画效果
 * Created by fdm on 2017/7/10.
 */
public class RippleAnimationView extends RelativeLayout {

    private int rippleType;
    private int rippleColor;
    private int rippleAmount;
    private float rippleScale;
    private float rippleRadius;
    private int rippleDuration;
    public Paint paint;
    public float rippleStrokeWidth;
    private TypedArray typedArray;

    private AnimatorSet animatorSet;
    private boolean animationRunning = false;
    private ArrayList<RippleCircleView> rippleViewList = new ArrayList<>();

    //默认实心圆圈
    private static final int DEFAULT_FILL_TYPE = 0;
    //默认伸缩大小
    private static final float DEFAULT_SCALE = 5.0f;
    //默认圆圈个数
    private static final int DEFAULT_RIPPLE_COUNT = 5;
    //默认扩散时间
    private static final int DEFAULT_DURATION_TIME = 2500;

    public RippleAnimationView(Context context) {
        super(context);
    }

    public RippleAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RippleAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(final Context context, final AttributeSet attrs) {

        //判断View当前是否处于 IDE 布局编辑（预览）状态，只有在编辑状态下才会返回true，
        //在编写只有在运行时才能看到绘制效果的自定义View时，可以使用该方法查看布局预览。
//        if (isInEditMode()) {
//            return;
//        }

        //加载自定义属性
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleAnimationView);
        rippleType = typedArray.getInt(R.styleable.RippleAnimationView_ripple_anim_type, DEFAULT_FILL_TYPE);
        rippleColor = typedArray.getColor(R.styleable.RippleAnimationView_ripple_anim_color, ContextCompat.getColor(context, R.color.rippleColor));
        rippleAmount = typedArray.getInt(R.styleable.RippleAnimationView_ripple_anim_amount, DEFAULT_RIPPLE_COUNT);
        rippleScale = typedArray.getFloat(R.styleable.RippleAnimationView_ripple_anim_scale, DEFAULT_SCALE);
        rippleRadius = typedArray.getDimension(R.styleable.RippleAnimationView_ripple_anim_radius, getResources().getDimension(R.dimen.rippleRadius));
        rippleDuration = typedArray.getInt(R.styleable.RippleAnimationView_ripple_anim_duration, DEFAULT_DURATION_TIME);
        rippleStrokeWidth = typedArray.getDimension(R.styleable.RippleAnimationView_ripple_anim_strokeWidth, getResources().getDimension(R.dimen.rippleStrokeWidth));
        //注意回收TypedArray
        typedArray.recycle();

        int rippleDelay = rippleDuration / rippleAmount;

        paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
        if (rippleType == DEFAULT_FILL_TYPE) {
            rippleStrokeWidth = 0;
            paint.setStyle(Paint.Style.FILL);
        } else {
            paint.setStyle(Paint.Style.STROKE);
        }
        paint.setColor(rippleColor);

        LayoutParams rippleParams = new LayoutParams((int) (2 * (rippleRadius + rippleStrokeWidth)), (int) (2 * (rippleRadius + rippleStrokeWidth)));
        rippleParams.addRule(CENTER_IN_PARENT, TRUE);

        //分析该动画后将其拆分为缩放、渐变
        ArrayList<Animator> animatorList = new ArrayList<>();
        for (int i = 0; i < rippleAmount; i++) {

            RippleCircleView rippleView = new RippleCircleView(this, context);
            rippleView.setAlpha(0.0f);
            addView(rippleView, rippleParams);
            rippleViewList.add(rippleView);
            //ScaleX缩放
            final ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleX", 1.0f, rippleScale);
            scaleXAnimator.setRepeatCount(ObjectAnimator.INFINITE);//无限重复
            scaleXAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleXAnimator.setStartDelay(i * rippleDelay);
            scaleXAnimator.setDuration(rippleDuration);
            animatorList.add(scaleXAnimator);
            //ScaleY缩放
            final ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleY", 1.0f, rippleScale);
            scaleYAnimator.setRepeatCount(ObjectAnimator.INFINITE);//无限重复
            scaleYAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleYAnimator.setStartDelay(i * rippleDelay);
            scaleYAnimator.setDuration(rippleDuration);
            animatorList.add(scaleYAnimator);
            //Alpha渐变
            final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(rippleView, "Alpha", 0.6f, 0f);
            alphaAnimator.setRepeatCount(ObjectAnimator.INFINITE);//无限重复
            alphaAnimator.setRepeatMode(ObjectAnimator.RESTART);
            alphaAnimator.setStartDelay(i * rippleDelay);
            alphaAnimator.setDuration(rippleDuration);
            animatorList.add(alphaAnimator);
        }

        animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(animatorList);
    }

    /**
     * 开始动画
     */
    public void startRippleAnimation() {
        if (!isRippleRunning()) {
            for (RippleCircleView rippleView : rippleViewList) {
                rippleView.setVisibility(VISIBLE);
            }
            animatorSet.start();
            animationRunning = true;
        }
    }

    /**
     * 停止动画
     */
    public void stopRippleAnimation() {
        if (isRippleRunning()) {
            animatorSet.end();
            animationRunning = false;
            Collections.reverse(rippleViewList);
            for (RippleCircleView rippleView : rippleViewList) {
                rippleView.setVisibility(INVISIBLE);
                rippleView.setScaleX(1.0f);
                rippleView.setScaleY(1.0f);
            }
        }
    }

    /**
     * 是否正在执行
     *
     * @return boolean isRippleRunning
     */
    public boolean isRippleRunning() {
        return animationRunning;
    }
}