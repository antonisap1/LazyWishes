package com.example.lazywishes;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lazywishes.Eortes_Database;
import com.example.lazywishes.NotificationReceiver;

import java.sql.Time;

public class NotificationHandler {
    private static final String titleExtra = "titleExtra";
    private static final String messageExtra = "messageExtra";
    private static final int NotificationID = 0;
    Eortes_Database dbHelper;

    private Context mContext;

    public NotificationHandler(Context context) {
        mContext = context;
    }

    public void staticNotification() {
        Intent intent = new Intent(mContext, NotificationReceiver.class);
        dbHelper = new Eortes_Database(mContext);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = Eortes_Database.getMatchingName(db);
        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
        String title = "Mia epafi sas giortazei !";
        String message = name;
        intent.putExtra(titleExtra, title);
        intent.putExtra(messageExtra, message);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, NotificationID, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);

        long time =getTime();
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);

    }

    private long getTime() {
        return System.currentTimeMillis();
    }


}
