package com.example.liuhui.codex;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.google.i18n.phonenumbers.Phonenumber;

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

    public static int APP_REQUEST_CODE = 99;

    String[] persission = new String[]{Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_CALENDAR,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,};

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
                break;
            case R.id.main_btn2:
                startActivity(new Intent(this,MyCameraActivity.class));
                break;
            case R.id.main_btn3:
                startActivity(new Intent(this,ThreePartyLoginActivity.class));
                break;
            case R.id.main_btn4:
                startActivity(new Intent(this,GetSMSActivity.class));
                break;
            case R.id.main_btn5:
                startActivity(new Intent(this,GlideImageActivity.class));
                break;
            case R.id.main_btn6:
                startActivity(new Intent(this,MyViewActivity.class));
                break;
            case R.id.main_btn7:
                startService(new Intent(this,UpTrackService.class));
                break;
            case R.id.main_btn8:
                stopService(new Intent(this,UpTrackService.class));
                break;
            case R.id.main_btn9:
                startFacebookKit();
                break;
            case R.id.main_btn10:
                getFacebookKitPhone();
                break;
        }
    }

    private void getFacebookKitPhone() {
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                Log.d(TAG, "onSuccess: " + account.getPhoneNumber());
            }

            @Override
            public void onError(AccountKitError accountKitError) {
                Log.d(TAG, "onError: " + accountKitError.getUserFacingMessage());
                Log.d(TAG, "onError: " + accountKitError.getErrorType());
                Log.d(TAG, "onError: " + accountKitError.getDetailErrorCode());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE){
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null){
                toastMessage = loginResult.getError().getErrorType().getMessage();
            }else if (loginResult.wasCancelled()){
                toastMessage = "login cancelled";
            }else {
                if (loginResult.getAccessToken() != null){
                    toastMessage = "success : " + loginResult.getAccessToken().getAccountId();
                }else {
                    toastMessage = String.format("Success:%s...", loginResult.getAuthorizationCode().substring(0,10));
                }
            }

            Log.d(TAG, "toastMessage: " + toastMessage);
        }

    }

    private void startFacebookKit() {
        Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration accountKitConfiguration = new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE,AccountKitActivity.ResponseType.TOKEN)
                .setDefaultCountryCode("TH")
                .build();
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,accountKitConfiguration);
        startActivityForResult(intent,APP_REQUEST_CODE);
    }

    private void setListener() {
        main_btn1.setOnClickListener(this);
        main_btn2.setOnClickListener(this);
        main_btn3.setOnClickListener(this);
        main_btn4.setOnClickListener(this);
        main_btn5.setOnClickListener(this);
        main_btn6.setOnClickListener(this);
        main_btn7.setOnClickListener(this);
        main_btn8.setOnClickListener(this);
        main_btn9.setOnClickListener(this);
        main_btn10.setOnClickListener(this);
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
        main_btn1 = (Button) findViewById(R.id.main_btn1);
        main_btn2 = (Button) findViewById(R.id.main_btn2);
        main_btn3 = (Button) findViewById(R.id.main_btn3);
        main_btn4 = (Button) findViewById(R.id.main_btn4);
        main_btn5 = (Button) findViewById(R.id.main_btn5);
        main_btn6 = (Button) findViewById(R.id.main_btn6);
        main_btn7 = (Button) findViewById(R.id.main_btn7);
        main_btn8 = (Button) findViewById(R.id.main_btn8);
        main_btn9 = (Button) findViewById(R.id.main_btn9);
        main_btn10 = (Button) findViewById(R.id.main_btn10);
    }

}
