package com.example.liuhui.codex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.liuhui.codex.MyGlide.GlideUtils;

public class GlideImageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button glide_btn1;
    private Button glide_btn2;
    private Button glide_btn3;
    private ImageView glide_image1;
    private ImageView glide_image2;
    private String url = "http://107.150.124.41:8008/openapi/v1/image/0dfb8db9-da36-42b8-99d1-043aa57f7150";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_image);

        initView();

        setListener();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.glide_btn1:
                GlideUtils.loadImage(this,url,glide_image1);
                break;
            case R.id.glide_btn2:
//                GlideUtils.loadImageNoCache(this,url,glide_image2);
                break;
            case R.id.glide_btn3:
                break;
        }
    }

    private void setListener() {
        glide_btn1.setOnClickListener(this);
        glide_btn2.setOnClickListener(this);
        glide_btn3.setOnClickListener(this);
    }

    private void initView() {
        glide_btn1 = (Button) findViewById(R.id.glide_btn1);
        glide_btn2 = (Button) findViewById(R.id.glide_btn2);
        glide_btn3 = (Button) findViewById(R.id.glide_btn3);
        glide_image1 = (ImageView) findViewById(R.id.glide_image1);
        glide_image2 = (ImageView) findViewById(R.id.glide_image2);
    }

}
