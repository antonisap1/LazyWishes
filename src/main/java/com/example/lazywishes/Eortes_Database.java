package com.example.lazywishes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class Eortes_Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "EORTES_DB";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "EORTES";
    public static final String ID_COL = "id";
    public static final String NAME_COL = "name";
    public static final String DAY_COL = "day";
    public static final String MONTH_COL = "month";
    public static final String DESCRIPTION_COL = "description";

    public Eortes_Database(@Nullable Context context)
    {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        String query  = " CREATE TABLE " + TABLE_NAME + " ("
                +ID_COL+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +NAME_COL+" TEXT, "
                + DAY_COL +" TEXT, "
                + MONTH_COL +" TEXT, " +
                DESCRIPTION_COL + " TEXT) ";
        db.execSQL(query);
        Cursor cursor = Eortes_Database.getCurrentDayData(db);


    }
    public void addEorti(String eorti, String imera_eortis, String perigrafi , String description)
    {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NAME_COL,eorti);
            values.put(DAY_COL,imera_eortis);
            values.put(MONTH_COL,perigrafi);
            values.put(DESCRIPTION_COL,description);

            db.insert(TABLE_NAME,null,values);
           // db.close();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

            onCreate(db);
    }
    public static Cursor getCurrentDayData(SQLiteDatabase db)
    {
        Calendar calendar = Calendar.getInstance();
        String currentDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));


        Cursor cursor = db.rawQuery("SELECT * FROM EORTES WHERE day = ?", new String[] {currentDay});

        return cursor;
    }
    public static Cursor getMatchingName(SQLiteDatabase db)
    {
        Calendar calendar = Calendar.getInstance();
        String currentDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        int month = calendar.get(Calendar.MONTH) + 1;
        String currentMonth= String.valueOf(month);


        Cursor cursor = db.rawQuery("SELECT name FROM EORTES WHERE day = ? AND month = ?", new String[] {currentDay,currentMonth});
        cursor.moveToFirst();

        return cursor;
    }
    public static Cursor getDescription(SQLiteDatabase db)
    {
        Calendar calendar = Calendar.getInstance();
        String currentDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        int month = calendar.get(Calendar.MONTH) + 1;
        String currentMonth= String.valueOf(month);


        Cursor cursor = db.rawQuery("SELECT description FROM EORTES WHERE day = ? AND month = ?", new String[] {currentDay,currentMonth});
        cursor.moveToFirst();

        return cursor;
    }



}
