package com.zgh.griditemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * 给 RecyclerView item 设置边距，
 * 构造函数需要传入 context，要设置的边距宽度（单位：dp），边距颜色
 * 使用示例：
 */

public abstract class GridItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;//画笔
    private int   lineWidth;//px 分割线宽

    //item边距的构造函数
    public GridItemDecoration(Context context, float lineWidthDp, @ColorInt int colorRGB) {
        /*TypedValue.applyDimension()方法的功能就是把非标准尺寸转换成标准尺寸 dp->px*/
        this.lineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineWidthDp, context.getResources().getDisplayMetrics());
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(colorRGB);
        mPaint.setStyle(Paint.Style.FILL);
    }

    //提供供外部调用的构造方法
    public GridItemDecoration(Context context, int lineWidthDp, @ColorInt int colorRGB) {
        this(context, (float) lineWidthDp, colorRGB);
    }

    @Override
    /*onDraw可以通过一系列c.drawXXX()方法在绘制itemView之前绘制我们需要的内容*/
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //left, top, right, bottom
        /*得到图片的数量*/
        int childCount1 = parent.getChildCount();
        for (int i = 0; i < childCount1; i++) {
            //得到指定位置的图片
            View child = parent.getChildAt(i);
            //适配器将此视图定位为最新的布局传递
            int itemPosition = ((RecyclerView.LayoutParams) child.getLayoutParams()).getViewLayoutPosition();

            boolean[] sideOffsetBooleans = getItemSidesIsHaveOffsets(itemPosition);
            if (sideOffsetBooleans[0]) {
                drawChildLeftVertical(child, c, parent);
            }
            if (sideOffsetBooleans[1]) {
                drawChildTopHorizontal(child, c, parent);
            }
            if (sideOffsetBooleans[2]) {
                drawChildRightVertical(child, c, parent);
            }
            if (sideOffsetBooleans[3]) {
                drawChildBottomHorizontal(child, c, parent);
            }
        }
    }
    //下边距
    private void drawChildBottomHorizontal(View child, Canvas c, RecyclerView parent) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        /*左边 = 图片左边 - 窗口左边 - 线宽*/
        int left = child.getLeft() - params.leftMargin - lineWidth;
        int right = child.getRight() + params.rightMargin + lineWidth;
        int top = child.getBottom() + params.bottomMargin;
        int bottom = top + lineWidth;

        c.drawRect(left, top, right, bottom, mPaint);
    }
    //上边距
    private void drawChildTopHorizontal(View child, Canvas c, RecyclerView parent) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        int left = child.getLeft() - params.leftMargin - lineWidth;
        int right = child.getRight() + params.rightMargin + lineWidth;
        int bottom = child.getTop() - params.topMargin;
        int top = bottom - lineWidth;

        c.drawRect(left, top, right, bottom, mPaint);
    }
    //左边距
    private void drawChildLeftVertical(View child, Canvas c, RecyclerView parent) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        int top = child.getTop() - params.topMargin - lineWidth;
        int bottom = child.getBottom() + params.bottomMargin + lineWidth;
        int right = child.getLeft() - params.leftMargin;
        int left = right - lineWidth;

        c.drawRect(left, top, right, bottom, mPaint);
    }
    //右边距
    private void drawChildRightVertical(View child, Canvas c, RecyclerView parent) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        int top = child.getTop() - params.topMargin - lineWidth;
        int bottom = child.getBottom() + params.bottomMargin + lineWidth;
        int left = child.getRight() + params.rightMargin;
        int right = left + lineWidth;

        c.drawRect(left, top, right, bottom, mPaint);
    }

    @Override
    /*getItemOffests可以通过outRect.set(l,t,r,b)设置指定itemview的
    paddingLeft，paddingTop， paddingRight， paddingBottom*/
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //outRect 看源码可知这里只是把Rect类型的outRect作为一个封装了left,right,top,bottom的数据结构,
        //作为传递left,right,top,bottom的偏移值来用的
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        boolean[] sideOffsetBooleans = getItemSidesIsHaveOffsets(itemPosition);

        //如果是设置左边或者右边的边距，就只设置成指定宽度的一半，
        // 因为这个项目中的 Grid 是一行二列，如果不除以二的话，那么中间的间距就会很宽，
        //可根据实际项目需要修改成合适的值
        int left = sideOffsetBooleans[0] ? lineWidth/1 : 0;
        int top = sideOffsetBooleans[1] ? lineWidth/1 : 0;
        int right = sideOffsetBooleans[2] ? lineWidth/1 : 0;
        int bottom = sideOffsetBooleans[3] ? lineWidth/1 : 0;

        outRect.set(left, top, right, bottom);
    }

    /**
     * 顺序:left, top, right, bottom
     *
     * @return boolean[4]
     */
    public abstract boolean[] getItemSidesIsHaveOffsets(int itemPosition);


}



