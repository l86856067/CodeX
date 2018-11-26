package com.example.liuhui.codex;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetSMSActivity extends AppCompatActivity {

    private static final String TAG = "GetSMSActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_sms);

        getSmsList();

    }

    private void getSmsList() {
        ContentResolver contentResolver = getContentResolver();
        Cursor query = null;
        List<SmsBean> list = new ArrayList<>();
        String[] projection = new String[] { "_id", "address", "person",
                "body", "date", "type", };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            query = null;
        }else {
            query = contentResolver.query(Uri.parse("content://sms/"),null,null,null,"date");
        }
        if (query != null){
            while (query.moveToNext()){
                SmsBean smsBean = new SmsBean();
                String address = query.getString(query.getColumnIndex("address"));
                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                long date1 = query.getLong(query.getColumnIndex("date"));
                String date = sim.format(new Date(date1));
                String body = query.getString(query.getColumnIndex("body"));
                String person = query.getString(query.getColumnIndex("person"));
                String thread_id = query.getString(query.getColumnIndex("thread_id"));
                int type = query.getInt(query.getColumnIndex("type"));
                String typeStr = "";
                if (type == 1){
                    typeStr = "接收";
                }else if (type == 2){
                    typeStr = "发送";
                }else {
                    typeStr = null;
                }
                smsBean.setAddress(address);
                smsBean.setThread_id(thread_id);
                smsBean.setDate(date1);
                smsBean.setType(type);
                smsBean.setBody(body);
                list.add(smsBean);
//                Log.d(TAG, "SMS: "+ person + " ; " + address + " ; " + date + " ; " + typeStr + " ; " + body);
            }
        }

        List<SmsBean> nlist = new ArrayList<>();

        if (list.size() > 10){
            nlist = list.subList(list.size() - 10, list.size());
        }else {
            nlist = list;
        }

        UpSmsBean upsms = new UpSmsBean();
        upsms.setList(nlist);
        Gson gson = new Gson();
        String s = gson.toJson(upsms);
        Log.d(TAG, "getSmsList: " + s);

    }

}
