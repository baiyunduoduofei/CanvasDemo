package com.zhouyunfei.cavansdemo;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView mImg;
    private JoinDrawable mJoinDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImg = findViewById(R.id.join_img);
        mJoinDrawable = new JoinDrawable(this, R.mipmap.unselect, R.mipmap.select);
        mImg.setImageDrawable(mJoinDrawable);
        startAnimator();

    }


    /**
     * 开启动画
     */
    public void startAnimator() {
        ValueAnimator mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.setDuration(2000);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mJoinDrawable.setLevel((int) (animation.getAnimatedFraction() * 10000));
            }
        });
        mAnimator.start();

    }
}
