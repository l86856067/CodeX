package com.example.liuhui.codex;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by liuhui on 2018/10/17.
 */

public class MyViewViewPagerAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        imageView.setImageResource(R.mipmap.ic_launcher);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
