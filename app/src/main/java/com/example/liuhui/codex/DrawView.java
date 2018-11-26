package com.example.liuhui.codex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by liuhui on 2018/10/17.
 * 功能  ：  自定义View 画一个圆并手指拖动圆移动
 */

public class DrawView extends View implements ViewPagerNum {

    private static final String TAG = "DrawView";
    public float currentX = 100;
    public float currentY = 100;

    public DrawView(Context context) {
        this(context,null);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawCircle(currentX,currentY,50,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX= event.getX();
        currentY=event.getY();
        invalidate();
        return true;
    }

    @Override
    public void getNum(int num) {
        Log.d(TAG, "getNum: " + num);
        switch (num){
            case 0:
                currentX = 100;
                currentY = 100;
                hhhh();
                break;
            case 1:
                currentX = 200;
                currentY = 100;
                hhhh();
                break;
            case 2:
                currentX = 300;
                currentY = 100;
                hhhh();
                break;
            case 3:
                currentX = 400;
                currentY = 100;
                hhhh();
                break;
        }
    }

    private void hhhh() {
        Log.d(TAG, "hhhh: ");
        invalidate();
    }
}
