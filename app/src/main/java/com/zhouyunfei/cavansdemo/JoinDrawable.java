package com.zhouyunfei.cavansdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.Gravity;

/**
 * @author: zhouyunfei
 * @date: 2018/6/26
 * @desc:
 */
public class JoinDrawable extends Drawable {

    private final Rect mClipRect = new Rect();
    private Rect mBounds = new Rect();
    private Drawable mUnselectedDrawable;
    private Drawable mSelectedDrawable;
    private int mCurrentLevel = 0;

    /***
     * 构造函数
     * @param unselect 未选择图片（灰色五角星）
     * @param select 选择的图片（黄色五角星）
     */
    public JoinDrawable(Drawable unselect, Drawable select) {
        mUnselectedDrawable = unselect;
        mSelectedDrawable = select;
    }

    /***
     * 构造函数
     * @param context 上下文
     * @param unselectId 未选择图片ID（灰色五角星）
     * @param selectId 选择的图片ID（黄色五角星）
     */
    public JoinDrawable(Context context, int unselectId, int selectId) {
        mUnselectedDrawable = context.getResources().getDrawable(unselectId);
        mSelectedDrawable = context.getResources().getDrawable(selectId);
    }

    @Override
    public void draw(Canvas canvas) {
        //获得level
        mCurrentLevel = getLevel();
        //level为0设置画未选择图片
        if (0 == mCurrentLevel) {
            mUnselectedDrawable.draw(canvas);
        } else if (10000 == mCurrentLevel) {
            //level为最大值则显示选择图片
            mSelectedDrawable.draw(canvas);
        } else {
           /* 根据level大小对画布进行裁剪，设置不同图片*/

            //获得画布的上半部分
            Gravity.apply(Gravity.TOP, mBounds.width(), (int) (mBounds.height() * (mCurrentLevel / (10000f))), mBounds, mClipRect);
            //先保存画布
            canvas.save();
            //裁剪出上半部分
            canvas.clipRect(mClipRect);
            //画未选择图片，因为画布只有一半，所以只能显示一半
            mUnselectedDrawable.draw(canvas);
            //恢复画布，将其恢复到原来大小
            canvas.restore();
            //获得画布的下半部分
            Gravity.apply(Gravity.BOTTOM, mBounds.width(), (int) (mBounds.height() * (1 - mCurrentLevel / (10000f))), mBounds, mClipRect);
            //保存画布
            canvas.save();
            //裁剪出下半部分
            canvas.clipRect(mClipRect);
            //画选择图片，因为只有下半部分，所以只有显示一半
            mSelectedDrawable.draw(canvas);
            //恢复画布
            canvas.restore();

        }
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        mBounds = bounds;
        mUnselectedDrawable.setBounds(bounds);
        mSelectedDrawable.setBounds(bounds);
    }

    @Override
    public int getIntrinsicWidth() {
        return Math.max(mSelectedDrawable.getIntrinsicWidth(),
                mUnselectedDrawable.getIntrinsicWidth());
    }

    @Override
    public int getIntrinsicHeight() {
        return Math.max(mSelectedDrawable.getIntrinsicHeight(),
                mUnselectedDrawable.getIntrinsicHeight());
    }

    @Override
    protected boolean onLevelChange(int level) {
        invalidateSelf();//通知重新绘制
        return true;
    }


    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

}