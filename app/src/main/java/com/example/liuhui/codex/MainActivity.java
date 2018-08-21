package com.example.liuhui.codex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button main_btn1;
    private Button main_btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        setListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_btn1:
                break;
            case R.id.main_btn2:

                break;
        }
    }

    private void setListener() {
        main_btn1.setOnClickListener(this);
        main_btn2.setOnClickListener(this);
    }

    private void initView() {
        main_btn1 = (Button) findViewById(R.id.main_btn1);
        main_btn2 = (Button) findViewById(R.id.main_btn2);
    }

}
