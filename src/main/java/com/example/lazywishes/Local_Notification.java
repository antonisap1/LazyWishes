package com.example.lazywishes;

import static com.example.lazywishes.NotificationReceiver.NotificationID;
import static com.example.lazywishes.NotificationReceiver.channelID;
import static com.example.lazywishes.NotificationReceiver.messageExtra;
import static com.example.lazywishes.NotificationReceiver.titleExtra;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.example.lazywishes.databinding.ActivityLocalNotifcationBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Local_Notification extends BaseActivity  {
    private ActivityLocalNotifcationBinding binding;
    NotificationHandler notificationHandler;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocalNotifcationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bottomNavigationView = findViewById(R.id.bottonnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        createNotificationChannel();
        binding.submitButton.setOnClickListener(view -> scheduleNotification());
    }

    private  void scheduleNotification() {
        Intent intent = new Intent(this, NotificationReceiver.class);
        String title = Objects.requireNonNull(binding.titleET.getText()).toString();
        String message = binding.messageET.getText().toString();
        intent.putExtra(titleExtra, title);
        intent.putExtra(messageExtra, message);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, NotificationID, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        long time = getTime();
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        showAlert(time, title, message);
    }
    private void showAlert(long time, String title, String message) {
        Date date = new Date(time);
        DateFormat dateFormat = android.text.format.DateFormat.getLongDateFormat(this);
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(this);

        new AlertDialog.Builder(this)
                .setTitle("Notification Scheduled")
                .setMessage("Title: " + title + "\nMessage: " + message + "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date))
                .setPositiveButton("Okay",(dialog,which) ->{})
                .show();
    }
    private long getTime() {
        int minute = binding.timePicker.getMinute();
        int hour = binding.timePicker.getHour();
        int day = binding.datePicker.getDayOfMonth();
        int month = binding.datePicker.getMonth();
        int year = binding.datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        return calendar.getTimeInMillis();
    }
    private void createNotificationChannel()
    {
        String name = "Notif Channel";
        String desc = "A Description";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(channelID,name,importance);
        channel.setDescription(desc);
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    }