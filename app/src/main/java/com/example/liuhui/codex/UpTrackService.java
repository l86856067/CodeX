package com.example.liuhui.codex;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.amap.api.track.AMapTrackClient;
import com.amap.api.track.ErrorCode;
import com.amap.api.track.OnTrackLifecycleListener;
import com.amap.api.track.TrackParam;
import com.amap.api.track.query.model.AddTerminalRequest;
import com.amap.api.track.query.model.AddTerminalResponse;
import com.amap.api.track.query.model.AddTrackResponse;
import com.amap.api.track.query.model.DistanceResponse;
import com.amap.api.track.query.model.HistoryTrackResponse;
import com.amap.api.track.query.model.LatestPointResponse;
import com.amap.api.track.query.model.OnTrackListener;
import com.amap.api.track.query.model.ParamErrorResponse;
import com.amap.api.track.query.model.QueryTerminalRequest;
import com.amap.api.track.query.model.QueryTerminalResponse;
import com.amap.api.track.query.model.QueryTrackResponse;

/**
 * Created by liuhui on 2018/11/2.
 * 上报用户轨迹的服务，服务基于高德猎鹰SDK
 */

public class UpTrackService extends Service {

    private static final String TAG = "UpTrackService";
    private static final String CHANNEL_TRACK = "channel_upTrack";
    private long serviceId = 11214;
    private String trackName = "user_test";

    AMapTrackClient aMapTrackClient;

    OnTrackLifecycleListener onTrackLifecycleListener = new OnTrackLifecycleListener() {
        @Override
        public void onBindServiceCallback(int i, String s) {

        }

        @Override
        public void onStartGatherCallback(int i, String s) {
            if (i == ErrorCode.TrackListen.START_GATHER_SUCEE || i == ErrorCode.TrackListen.START_GATHER_ALREADY_STARTED){
                Log.d(TAG, "onStartGatherCallback: 定位采集开启成功");
            }else {
                Log.d(TAG, "onStartGatherCallback: 定位采集开启异常 ： " + s);
            }
        }

        @Override
        public void onStartTrackCallback(int i, String s) {
            if (i == ErrorCode.TrackListen.START_TRACK_SUCEE || i == ErrorCode.TrackListen.START_TRACK_SUCEE_NO_NETWORK || i == ErrorCode.TrackListen.START_TRACK_ALREADY_STARTED){
                Log.d(TAG, "onStartTrackCallback: 服务启动成功，继续开启收集上报");
                aMapTrackClient.startGather(this);
            }else {
                Log.d(TAG, "onStartTrackCallback: 轨迹上报服务启动异常 ： " + s);
            }
        }

        @Override
        public void onStopGatherCallback(int i, String s) {

        }

        @Override
        public void onStopTrackCallback(int i, String s) {

        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "====    onBind    ====");
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "====    onCreate    ====");
        super.onCreate();
        aMapTrackClient = new AMapTrackClient(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        Log.d(TAG, "====    onStartCommand    ====");
        aMapTrackClient.setInterval(10,60);
        aMapTrackClient.setCacheSize(30);
        startUpTrack();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "====    onDestroy    ====");
        super.onDestroy();
    }

    private void startUpTrack() {
        aMapTrackClient.queryTerminal(new QueryTerminalRequest(serviceId,trackName), new OnTrackListener() {
            @Override
            public void onQueryTerminalCallback(QueryTerminalResponse queryTerminalResponse) {
                if (queryTerminalResponse.isSuccess()){
                    if (queryTerminalResponse.getTid() <= 0){
                        Log.d(TAG, "onQueryTerminalCallback: terminal还不存在，先创建");
                        aMapTrackClient.addTerminal(new AddTerminalRequest(trackName, serviceId), new OnTrackListener() {
                            @Override
                            public void onQueryTerminalCallback(QueryTerminalResponse queryTerminalResponse) {

                            }

                            @Override
                            public void onCreateTerminalCallback(AddTerminalResponse addTerminalResponse) {
                                if (addTerminalResponse.isSuccess()){
                                    long terminalId = addTerminalResponse.getTid();
                                    Log.d(TAG, "onCreateTerminalCallback: 创建成功 ： " + terminalId);
                                    TrackParam trackParam = new TrackParam(serviceId,terminalId);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                        trackParam.setNotification(createNotification());
                                    }
                                    aMapTrackClient.startTrack(trackParam,onTrackLifecycleListener);
                                }else {
                                    Log.d(TAG, "onCreateTerminalCallback: 请求失败 : " + addTerminalResponse.getErrorMsg());
                                }
                            }

                            @Override
                            public void onDistanceCallback(DistanceResponse distanceResponse) {

                            }

                            @Override
                            public void onLatestPointCallback(LatestPointResponse latestPointResponse) {

                            }

                            @Override
                            public void onHistoryTrackCallback(HistoryTrackResponse historyTrackResponse) {

                            }

                            @Override
                            public void onQueryTrackCallback(QueryTrackResponse queryTrackResponse) {

                            }

                            @Override
                            public void onAddTrackCallback(AddTrackResponse addTrackResponse) {

                            }

                            @Override
                            public void onParamErrorCallback(ParamErrorResponse paramErrorResponse) {

                            }
                        });
                    }else {
                        long tid = queryTerminalResponse.getTid();
                        Log.d(TAG, "onQueryTerminalCallback: terminal已经存在，直接开启猎鹰服务 : " + tid);
                        TrackParam trackParam = new TrackParam(serviceId,tid);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                            trackParam.setNotification(createNotification());
                        }
                        aMapTrackClient.startTrack(trackParam,onTrackLifecycleListener);
                    }
                }else {
                    Log.d(TAG, "onQueryTerminalCallback: 请求失败 : " + queryTerminalResponse.getErrorMsg());
                }
            }

            @Override
            public void onCreateTerminalCallback(AddTerminalResponse addTerminalResponse) {

            }

            @Override
            public void onDistanceCallback(DistanceResponse distanceResponse) {

            }

            @Override
            public void onLatestPointCallback(LatestPointResponse latestPointResponse) {

            }

            @Override
            public void onHistoryTrackCallback(HistoryTrackResponse historyTrackResponse) {

            }

            @Override
            public void onQueryTrackCallback(QueryTrackResponse queryTrackResponse) {

            }

            @Override
            public void onAddTrackCallback(AddTrackResponse addTrackResponse) {

            }

            @Override
            public void onParamErrorCallback(ParamErrorResponse paramErrorResponse) {

            }
        });
    }

    private Notification createNotification(){

        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(CHANNEL_TRACK,"app service",NotificationManager.IMPORTANCE_LOW);
            nm.createNotificationChannel(channel);
            builder = new Notification.Builder(getApplicationContext(),CHANNEL_TRACK);
        }else {
            builder = new Notification.Builder(getApplicationContext());
        }
        Intent nfIntent = new Intent(this,UpTrackService.class);
        nfIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        builder.setContentIntent(PendingIntent.getService(this,0,nfIntent,0))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("目不转睛")
                .setContentText("我正在看着你看着你...");
        Notification notification = builder.build();
        return notification;

    }

}
