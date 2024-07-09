package com.example.lazywishes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Struct;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentImage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentImage extends Fragment {



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageView mImageView;
    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    ImageDBHelper dbHelper;
    Eortes_Database dbHandler;



    private String mParam1;
    private String mParam2;

    public FragmentImage() {

    }


    public static FragmentImage newInstance(String param1, String param2) {
        FragmentImage fragment = new FragmentImage();
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

    @SuppressLint({"WrongThread", "Range"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image, container, false);

        mImageView = view.findViewById(R.id.image_view);

        mDescriptionTextView = view.findViewById(R.id.image_description);

        dbHelper=new ImageDBHelper(getContext());
        dbHandler = new Eortes_Database(getContext());

        String name = "";
        String png = ".png";
        String description = "";
        try {
            SQLiteDatabase db = dbHandler.getReadableDatabase();

            Cursor cursor = Eortes_Database.getMatchingName(db);
            cursor.moveToFirst();


            name = cursor.getString(cursor.getColumnIndex("name"));


            cursor = Eortes_Database.getDescription(db);


            description = cursor.getString(cursor.getColumnIndex("description"));
            cursor.close();
        }
        catch(Exception e)
        {
            Toast.makeText(getContext(), "No one celebrating", Toast.LENGTH_SHORT).show();
        }
         String finalDescription = description;

        File directory = requireContext().getFilesDir();
        String filename = name+png;
        File file = new File(directory,filename);
        Bitmap bitmap = dbHelper.getSavedImage(file.getAbsolutePath());
        mImageView.setImageBitmap(bitmap);
        mDescriptionTextView.setText(finalDescription);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageDetailActivity.class);
                intent.putExtra("filepath", String.valueOf(directory));
                intent.putExtra("filename",filename);
                intent.putExtra("description", finalDescription);
                startActivity(intent);
            }
        });


        return view;
    }
}