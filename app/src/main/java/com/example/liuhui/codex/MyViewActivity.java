package com.example.liuhui.codex;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MyViewActivity extends AppCompatActivity {

    private static final String TAG = "MyViewActivity";

    private ViewPager myView_viewPager;
    private MyViewViewPagerAdapter adapter;
    private DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view);

        initView();

        setListener();
    }

    private void setListener() {
        myView_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + position);
                drawView.getNum(position);
                drawView.postInvalidate();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        myView_viewPager = (ViewPager) findViewById(R.id.myView_viewPager);
        adapter = new MyViewViewPagerAdapter();
        myView_viewPager.setAdapter(adapter);

        drawView = new DrawView(this);

    }
}
