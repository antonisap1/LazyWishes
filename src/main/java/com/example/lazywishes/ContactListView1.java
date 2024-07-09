package com.example.lazywishes;

import static com.example.lazywishes.Eortes_Database.getCurrentDayData;
import static com.example.lazywishes.Eortes_Database.getMatchingName;
import static java.lang.Integer.getInteger;
import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Objects;

public class ContactListView1 extends BaseActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private static  Eortes_Database dbHandler;
    public static ArrayList<Contact> contacts;
    public static ArrayList<String> phoneContacts;
    Context context = this;
    Eortes_Database helper = new Eortes_Database(context);
    private BottomNavigationView bottomNavigationView;


    private void getContacts() {

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri, projection, null, null, null);//fullname display


        if (cursor != null)
        {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Contact contact = new Contact(name, number);
                contacts.add(contact);
            }
            cursor.close();
        }


    }
    public void getCelebratingContacts()
    {
        contacts = new ArrayList<>();
        phoneContacts = new ArrayList<>();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection1 = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        ContentResolver contentResolver = getContentResolver();

        Cursor contactCursor = contentResolver.query(uri, projection1, null, null, null);//name display

        if (contactCursor != null) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursorName = getMatchingName(db);
            @SuppressLint("Range") String name = cursorName.getString(cursorName.getColumnIndex("name"));
            while(contactCursor.moveToNext()) {


                int nameColumnIndex = contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String displayName = contactCursor.getString(nameColumnIndex);
                @SuppressLint("Range") String number = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String firstName = displayName.split(" ")[0];
                Contact contact = new Contact(displayName, number);
                if(Objects.equals(name, firstName))
                {
                    phoneContacts.add(firstName);

                    contacts.add(contact);
                }

            }
            contactCursor.close();


        }
    }

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SQLiteDatabase db = helper.getReadableDatabase();

        super.onCreate(savedInstanceState);

        try{
            getCelebratingContacts();
        }
        catch (Exception e)
        {
            getContacts();
        }

        setContentView(R.layout.activity_list_view);
        bottomNavigationView = findViewById(R.id.bottonnav);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        listView = findViewById(R.id.list_view);
        Cursor cursor = getCurrentDayData(db);
        Cursor cursorName = getMatchingName(db);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(new ContactAdapter(this, contacts));

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            AutomatedMessageFragment automatedMessageFragment = new AutomatedMessageFragment();
            fragmentTransaction.add(R.id.fragment_container, automatedMessageFragment);
            fragmentTransaction.commit();


    }






}