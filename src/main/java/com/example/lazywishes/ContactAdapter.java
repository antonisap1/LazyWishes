package com.example.lazywishes;

import static android.provider.Settings.System.getString;
import static com.example.lazywishes.ContactListView1.phoneContacts;
import static com.example.lazywishes.MessageDbHelper.getMessage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private ArrayList<Contact> contacts;
    private Context mContext;



    public ContactAdapter(Context context, ArrayList<Contact> contacts)
    {
        super(context, 0, contacts);
        this.contacts = contacts;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_item, parent, false);
        }
        String name ="";
        try{
            name= phoneContacts.get(0);
        }
        catch (Exception e){
            Toast.makeText(getContext(), "den uparxei", Toast.LENGTH_SHORT).show();

        }
        String validName =  name;
        Contact contact = getItem(position);

        TextView nameTextView = convertView.findViewById(R.id.name_text_view);
        nameTextView.setText(contact.getName());

        TextView phoneNumberTextView = convertView.findViewById(R.id.phone_text_view);
        phoneNumberTextView.setText(contact.getPhoneNumber());

        ImageButton callButton = convertView.findViewById(R.id.quick_call_button);

        mContext = getContext();
        callButton.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + contact.getPhoneNumber()));
            ContextCompat.startActivity(mContext,callIntent,null);
        });
        ImageButton textButton = convertView.findViewById(R.id.quick_text_button);
        textButton.setOnClickListener(v -> {
            String updatedMessage =MessageData.getInstance().getMessage();
            if(updatedMessage==null)
            {
                updatedMessage="No message selected";
            }

            Intent textIntent = new Intent(Intent.ACTION_SENDTO);
            textIntent.putExtra("sms_body", updatedMessage + " " + validName);
            textIntent.setType("vnd.android-dir/mms-sms");
            textIntent.setData(Uri.parse("smsto:" + contact.getPhoneNumber()));
            ContextCompat.startActivity(mContext,textIntent,null);
        });




        return convertView;
    }


}