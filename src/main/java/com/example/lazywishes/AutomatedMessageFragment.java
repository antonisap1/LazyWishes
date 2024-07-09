package com.example.lazywishes;

import static com.example.lazywishes.ContactListView1.phoneContacts;
import static com.example.lazywishes.MessageDbHelper.getMessage;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AutomatedMessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AutomatedMessageFragment extends Fragment  {
    private Button refreshButton;
    private Button chooseButton;
    private TextView messageTextView;



    MessageDbHelper dbHelper;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public AutomatedMessageFragment() {

    }

    public static AutomatedMessageFragment newInstance(String param1, String param2) {
        AutomatedMessageFragment fragment = new AutomatedMessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        refreshButton = view.findViewById(R.id.pick_button);
        chooseButton = view.findViewById(R.id.automate_button);
        messageTextView = view.findViewById(R.id.message_text_view);
        dbHelper= new MessageDbHelper(getContext());
        String message = "";
        String name="";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = getMessage(db);
        try {
             name = phoneContacts.get(0);
        }
        catch (Exception e)
        {

        }
        try {
            message = cursor.getString(cursor.getColumnIndex("text"));
        }
        catch (Exception e)
        {

        }

        String finalName = name;
        String finalMessage=message;



        refreshButton.setOnClickListener(v ->
        {
            String UpdatedMessage=refreshMessage(finalMessage);
            messageTextView = view.findViewById(R.id.message_text_view);
            messageTextView.setText(finalName + " " + UpdatedMessage);
        });

        chooseButton.setOnClickListener(v ->
        {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            ChooseMessageFragment chooseMessageFragment = new ChooseMessageFragment();
            fragmentTransaction.replace(R.id.fragment_container, chooseMessageFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        });

        return view;
    }
    @SuppressLint("Range")
    public String refreshMessage(String message)
    {
        dbHelper= new MessageDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = getMessage(db);
        message = cursor.getString(cursor.getColumnIndex("text"));
        dbHelper.close();
        MessageData.getInstance().setMessage(message);


        return message;
    }




}
