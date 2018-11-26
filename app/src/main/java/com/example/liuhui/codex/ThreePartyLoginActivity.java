package com.example.liuhui.codex;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.linecorp.linesdk.LineApiResponseCode;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Set;

public class ThreePartyLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ThreePartyLoginActivity";

    private Button three_btn1;
    private Button three_btn2;

    private static final int LINELOGIN_CODE = 101;
    private static final String LINELOGIN_CHANNEL_ID = "1602878305";

    CallbackManager callbackManager;
    ProfileTracker profileTracker;
    LoginManager loginManager;
    boolean isLogin;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_party_login);

        initView();
        setListener();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.three_btn1:
                lineLogin();
                break;
            case R.id.three_btn2:
                if (isLogin){
                    loginManager.logOut();
                    isLogin = false;
                }else {
                    facebookLogin();
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LINELOGIN_CODE){

            LineLoginResult lineResult = LineLoginApi.getLoginResultFromIntent(data);

            switch (lineResult.getResponseCode()){
                case SUCCESS: // Login successful

                    Log.d(TAG, "SUCCESS: " + lineResult.getLineCredential().getAccessToken());
                    Log.d(TAG, "SUCCESS: " + lineResult.getLineCredential().getAccessToken().getAccessToken());
                    Log.d(TAG, "SUCCESS: " + lineResult.getLineProfile().getDisplayName());
                    Log.d(TAG, "SUCCESS: " + lineResult.getLineProfile().getStatusMessage());
                    Log.d(TAG, "SUCCESS: " + lineResult.getLineProfile().getUserId());
                    Log.d(TAG, "SUCCESS: " + lineResult.getLineProfile().getPictureUrl());

                    break;

                case CANCEL:  // Login canceled by user

                    Log.d(TAG, "CANCEL: 用户取消了操作");

                    break;

                default:  // Login canceled due to other error

                    Log.e(TAG, "default: " + lineResult.getErrorData().toString());

            }

        }
    }

    private void facebookLogin(){
        loginManager.logInWithReadPermissions(this,Arrays.asList("public_profile","email"));
    }

    private void lineLogin() {
        Intent lineIntent = LineLoginApi.getLoginIntent(this, LINELOGIN_CHANNEL_ID);
        startActivityForResult(lineIntent,LINELOGIN_CODE);
    }

    private void setListener(){
        three_btn1.setOnClickListener(this);
        three_btn2.setOnClickListener(this);
    }

    private void initView(){
        three_btn1 = (Button) findViewById(R.id.three_btn1);
        three_btn2 = (Button) findViewById(R.id.three_btn2);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        isLogin = accessToken != null && !accessToken.isExpired();
        Log.d(TAG, "isLogin: " + isLogin);

        callbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess: " + loginResult.getAccessToken());
                getFriendsList();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: ");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError: " + error.getMessage());
            }
        });
        profileTracker = new ProfileTracker(){
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if (currentProfile != null){
                    Log.d(TAG, "current用户name: " + currentProfile.getFirstName());
                    Log.d(TAG, "current用户id: " + currentProfile.getId());
                    Log.d(TAG, "current用户name: " + currentProfile.getLastName());
                    Log.d(TAG, "current用户name: " + currentProfile.getMiddleName());
                    Log.d(TAG, "current用户name: " + currentProfile.getName());
                    Log.d(TAG, "current用户link: " + currentProfile.getLinkUri());
                    Log.d(TAG, "current用户头像: " + currentProfile.getProfilePictureUri(200,200));
//                    AlertDialog.Builder dialog = new AlertDialog.Builder(ThreePartyLoginActivity.this);
//                    dialog.setMessage(currentProfile.getId());
//                    dialog.show();
                }else if (oldProfile != null){
                    Log.d(TAG, "old用户资料: " + oldProfile.getFirstName());
                    Log.d(TAG, "old用户资料: " + oldProfile.getId());
                    Log.d(TAG, "old用户资料: " + oldProfile.getLastName());
                    Log.d(TAG, "old用户资料: " + oldProfile.getMiddleName());
                    Log.d(TAG, "old用户资料: " + oldProfile.getName());
                    Log.d(TAG, "old用户资料: " + oldProfile.getLinkUri());
                    Log.d(TAG, "old用户资料: " + oldProfile.getLinkUri());
                    Log.d(TAG, "old用户资料: " + oldProfile.getProfilePictureUri(200,200));
//                    AlertDialog.Builder dialog = new AlertDialog.Builder(ThreePartyLoginActivity.this);
//                    dialog.setMessage(currentProfile.getId());
//                    dialog.show();
                }
            }
        };
    }

    private void getFriendsList() {
//        GraphRequest request = GraphRequest.newMyFriendsRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONArrayCallback() {
//            @Override
//            public void onCompleted(JSONArray objects, GraphResponse response) {
//                Log.d(TAG, "onCompleted: " + response.toString() + " ; " + response.getJSONObject().toString());
//            }
//        });
//        Bundle parameters = new Bundle();
//        parameters.putString("user_friends", "name");
//        request.setParameters(parameters);
//        request.executeAsync();


        new GraphRequest(
                AccessToken.getCurrentAccessToken(), "/{friend-list-id}", null, HttpMethod.GET, new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.d(TAG, "onCompleted: " + response.toString());
                    }
                }
        ).executeAsync();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
    }
}
