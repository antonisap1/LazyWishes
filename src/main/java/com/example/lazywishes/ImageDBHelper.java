package com.example.lazywishes;






import static android.app.PendingIntent.readPendingIntentOrNullFromParcel;
import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;

public class ImageDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "IMAGE_DB";
    public static final String TABLE_NAME = "IMAGES";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PICTURE = "picture";
    private Context context;





    public ImageDBHelper(@Nullable Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        String query  = " CREATE TABLE " + TABLE_NAME + " ("
                +COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COLUMN_NAME+" TEXT, "
                + COLUMN_PICTURE +" BLOB) ";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);

    }
    public void addImage(String name, byte[] image)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,name);
        values.put(COLUMN_PICTURE, image);
        db.insert(TABLE_NAME,null,values);
    }
    @SuppressLint("Range")
    public Bitmap getImage(SQLiteDatabase db) {
        Bitmap bitmap = null;
        Cursor cursor = db.rawQuery("SELECT picture FROM IMAGES", null);
        byte[] pictureData = null;
        if (cursor.moveToFirst()) {
             pictureData = cursor.getBlob(cursor.getColumnIndex(ImageDBHelper.COLUMN_PICTURE));

            bitmap = BitmapFactory.decodeByteArray(pictureData, 0, pictureData.length);
        }
        cursor.close();
        return bitmap;
    }
    public Bitmap getBitmapFromBlob(Cursor cursor) {

        byte[] bytes = cursor.getBlob(cursor.getColumnIndexOrThrow("picture"));
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    public String saveImage(Bitmap bitmap) {
        String fileName = "image_" + System.currentTimeMillis() + ".png";
        File file = new File(context.getFilesDir(), fileName);

        try (FileOutputStream out = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return file.getAbsolutePath();
    }
    public Bitmap getSavedImage(String filePath) {
        Bitmap bitmap = null;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            bitmap = BitmapFactory.decodeStream(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}
