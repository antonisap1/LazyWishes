package com.example.lazywishes;

import static com.example.lazywishes.MessageDbHelper.getMessage;
import static com.example.lazywishes.MessageDbHelper.getMessageList;
;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseMessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseMessageFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private Button pickButton;
    private Button switchButton;

    private RecyclerView mRecyclerView;
    private ChooseMessageAdapter mAdapter;
    MessageDbHelper dbHelper;


    public ChooseMessageFragment() {

    }

    public static ChooseMessageFragment newInstance(String param1, String param2) {
        ChooseMessageFragment fragment = new ChooseMessageFragment();
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


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_choose_message, container, false);
        switchButton = view.findViewById(R.id.switchBtn);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.message_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ChooseMessageAdapter(getMessages(),getContext());
        mRecyclerView.setAdapter(mAdapter);




        switchButton.setOnClickListener(v -> {

            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            AutomatedMessageFragment automatedMessageFragment = new AutomatedMessageFragment();
            fragmentTransaction.replace(R.id.fragment_container, automatedMessageFragment);
            fragmentTransaction.commit();
        });
        return view;

    }
    @SuppressLint("Range")
    private List<String> getMessages() {
        // Fetch the messages from the database
        // Return a list of strings
        dbHelper = new MessageDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = getMessageList(db);
        @SuppressLint("Range") List<String> messages = new ArrayList<>();
        if(cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                String message = cursor.getString(cursor.getColumnIndex("text"));
                if(message!=null)
                {
                    messages.add(message);
                    cursor.moveToNext();
                }
            }
        }

        return messages;
    }



}