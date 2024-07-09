package com.example.lazywishes;


import static com.example.lazywishes.CalendarUtils.daysInMonth;
import static com.example.lazywishes.CalendarUtils.monthYearFromDate;
import static com.example.lazywishes.ContactListView1.phoneContacts;
import static com.example.lazywishes.Eortes_Database.getMatchingName;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainActivity extends BaseActivity implements  CalendarAdapter.OnItemListener
{
    private TextView monthYearText,saintName;
    private RecyclerView calendarRecyclerView;
    private BottomNavigationView bottomNavigationView;




    Eortes_Database dbHelper ;
    NotificationHandler notificationHandler;








    @SuppressLint({"WrongThread", "MissingInflatedId", "Range"})
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        notificationHandler = new NotificationHandler(getApplicationContext());
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();
        bottomNavigationView = findViewById(R.id.bottonnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        FragmentImage fragmentImage = new FragmentImage();
        saintName = findViewById(R.id.nameTextView);

        dbHelper = new Eortes_Database(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursorName = getMatchingName(db);
        String name = "";
        try{
            phoneContacts.get(0);


        }
        catch (Exception e)
        {
            //do something
        }
        try{
            notificationHandler.staticNotification();
        }
        catch (Exception e)
        {
            //do something
        }
        try{
             name = cursorName.getString(cursorName.getColumnIndex("name"));
        }
        catch (Exception e)
        {
            //do something
        }
        try{
            saintName.setText(name);        }
        catch (Exception e)
        {
            saintName.setText("Kaneis");
        }



        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_image, fragmentImage)
                .addToBackStack(null)
                .commit();



    }


    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate( CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonth( CalendarUtils.selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth,this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

    }


    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    public void PreviousMonthAction(View view)
    {
        CalendarUtils.selectedDate =  CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void NextMonthAction(View view)
    {
        CalendarUtils.selectedDate =  CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null )
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }

    }


    public void weeklyAction(View view)
    {

        startActivity(new Intent(this,WeekViewActivity.class));
    }

    public void devAction(View view)
    {
        startActivity(new Intent(this,Dev1.class));
    }

    public void notifyAction(View view)
    {
        startActivity(new Intent(this, Local_Notification.class));
    }



}

