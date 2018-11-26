package com.example.liuhui.codex;

import android.content.Context;
import android.graphics.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by liuhui on 2018/8/22.
 */

public class IdCardCamera extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "IdCardCamera";

    private Context mContext;        //上下文
    private SurfaceHolder holder;    //
    private Camera camera;           //相机

    private int mScreenWidth;
    private int mScreenHeight;

    public IdCardCamera(Context context) {
        this(context,null);
    }

    public IdCardCamera(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IdCardCamera(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
