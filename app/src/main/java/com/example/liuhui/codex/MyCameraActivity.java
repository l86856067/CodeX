package com.example.liuhui.codex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MyCameraActivity extends AppCompatActivity implements View.OnClickListener {

    public Button camera_btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_camera);

        initView();
        setListener();

    }

    private void initView() {
        camera_btn1 = (Button) findViewById(R.id.camera_btn1);
    }

    private void setListener() {
        camera_btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camera_btn1:
                startActivity(new Intent(this,IdCardActivity.class));
                break;
        }
    }
}
