package com.example.lazywishes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MessageDbHelper extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MESSAGES_DB";
    private static final String TABLE_NAME = "MESSAGES";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TEXT = "text";


    public MessageDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TEXT + " TEXT NOT NULL);";

        db.execSQL(query);
    }
    public void addMsg(String message)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEXT,message);
        db.insert(TABLE_NAME,null,values);
        // db.close();


    }
    public static Cursor getMessage(SQLiteDatabase db)
    {
        Random random = new Random();
        Cursor cursorRandom = db.rawQuery("SELECT COUNT(*) FROM MESSAGES", null);
        cursorRandom.moveToFirst();
        int count = cursorRandom.getInt(0);
        int randomNumber = random.nextInt(count) + 1;



        Cursor cursor = db.rawQuery("SELECT * FROM MESSAGES WHERE ID = ?", new String[] {String.valueOf(randomNumber)});
        if (cursor.moveToFirst()) {


            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("text"));

        }

        return cursor;


    }
    public static Cursor getMessageList(SQLiteDatabase db)
    {
        String query = "SELECT * FROM MESSAGES";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
        {
            cursor.moveToFirst();
        }

        return cursor;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}






