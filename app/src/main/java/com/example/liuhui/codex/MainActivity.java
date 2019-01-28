package com.example.liuhui.codex;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private Button main_btn1;
    private Button main_btn2;
    private Button main_btn3;
    private Button main_btn4;
    private Button main_btn5;
    private Button main_btn6;
    private Button main_btn7;
    private Button main_btn8;
    private Button main_btn9;
    private Button main_btn10;
    private Button main_btn11;
    private Button main_btn12;

    String[] persission = new String[]{Manifest.permission.WRITE_CALENDAR};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        checkPermission();

        setListener();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_btn1:
                startActivity(new Intent(this,CalendarAlarmActivity.class));
                break;
        }
    }

    private void setListener() {
        main_btn1.setOnClickListener(this);
    }

    private void checkPermission() {
        List<String> mpersission = new ArrayList<>();

        for (int i = 0 ; i < persission.length ; i ++){
            if (ContextCompat.checkSelfPermission(this, persission[i]) != PackageManager.PERMISSION_GRANTED){
                mpersission.add(persission[i]);
            }
        }
        if (!mpersission.isEmpty()){
            String[] needPersisssion = mpersission.toArray(new String[mpersission.size()]);
            ActivityCompat.requestPermissions(this,needPersisssion,1);
        }
    }

    private void initView() {
        main_btn1 = findViewById(R.id.main_btn1);
    }

}
