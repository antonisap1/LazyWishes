package com.example.lazywishes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;


public class NotificationReceiver extends BroadcastReceiver
{
    public static final int NotificationID = 1;
    public static final String channelID = "channel1";
    public static final String titleExtra = "titleExtra";
    public static final String messageExtra = "messageExtra";





    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent activityIntent = new Intent(context, ContactListView1.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(intent.getStringExtra(titleExtra))
                .setContentText(intent.getStringExtra(messageExtra))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();


        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NotificationID,notification);

    }



}
