package com.example.liuhui.codex.utils;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.provider.CalendarContract;

/**
 * Created by liuhui on 2019/1/25.
 * 用途 ：向系统内日历添加提醒的方法
 */

public class CalendarAlarmUtil {

    private static String CALENDER_URL = "content://com.android.calendar/calendars";
    private static String CALENDER_EVENT_URL = "content://com.android.calendar/events";
    private static String CALENDER_REMINDER_URL = "content://com.android.calendar/reminders";

    private static String CALENDARS_NAME = "CodeX";
    private static String CALENDARS_ACCOUNT_NAME = "coodx@coodx.com";
    private static String CALENDARS_ACCOUNT_TYPE = "com.android.codex";
    private static String CALENDARS_DISPLAY_NAME = "CodeX账户";

    /**
     * 检查是否已经添加了日历账户，如果没有添加则先添加一个日历账户再查询
     * 获取账户成功返回账户id，否则返回-1
     * @param context
     * @return
     */
    private static int checkAndAddCalendarAccount(Context context){
        int accountId = checkCalendarAccount(context);
        if (accountId >= 0){
            return accountId;
        }else {
            long addId = addCalendarAccount(context);
            if (addId >= 0){
                return checkCalendarAccount(context);
            }else {
                return -1;
            }
        }
    }

    /**
     * 检查是否存下现有账户，存在则返回账户id，否则返回-1
     * @param context
     * @return
     */
    private static int checkCalendarAccount(Context context) {

        Cursor cursor = context.getContentResolver().query(Uri.parse(CALENDER_URL),null,null,null,null);
        if (cursor == null){
            return -1;
        }
        int count = cursor.getCount();
        if (count > 0){
            cursor.moveToFirst();
            cursor.getInt(cursor.getColumnIndex(CalendarContract.Calendars._ID));
        }else {
            return -1;
        }

        return 0;
    }

    /**
     * 添加日历账户，账户创建成功返回账户id，否则返回-1
     * @param context
     * @return
     */
    private static long addCalendarAccount(Context context) {
        TimeZone timeZone = TimeZone.getDefault();
        ContentValues value = new ContentValues();
        value.put(CalendarContract.Calendars.NAME , CALENDARS_NAME);
        value.put(CalendarContract.Calendars.ACCOUNT_NAME , CALENDARS_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE , CALENDARS_ACCOUNT_TYPE);
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME , CALENDARS_DISPLAY_NAME);
        value.put(CalendarContract.Calendars.VISIBLE , 1);
        value.put(CalendarContract.Calendars.CALENDAR_COLOR , Color.BLUE);
        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL , CalendarContract.Calendars.CAL_ACCESS_OWNER);
        value.put(CalendarContract.Calendars.SYNC_EVENTS , 1);
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE , timeZone.getID());
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT , CALENDARS_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND , 0);

        Uri calendarUri = Uri.parse(CALENDER_URL);
        calendarUri = calendarUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER,"true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME,CALENDARS_ACCOUNT_NAME)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE,CALENDARS_ACCOUNT_TYPE)
                .build();
        Uri result = context.getContentResolver().insert(calendarUri,value);
        long id = result == null ? -1 : ContentUris.parseId(result);
        return id;
    }

    /**
     * 添加日历事件
     * @param context
     * @param title
     * @param description
     * @param reminderTime
     * @param previousDate
     */
    public static void addCalendarEvent(Context context,String title,String description,long reminderTime,int previousDate){
        if (context == null){
            return;
        }

        int calId = checkAndAddCalendarAccount(context); //获取日历账户的id
        if (calId < 0){  //获取账户id失败，直接返回，添加日历事件失败
            return;
        }
    }

}
