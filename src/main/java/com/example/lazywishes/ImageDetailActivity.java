package com.example.lazywishes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class ImageDetailActivity extends AppCompatActivity {
    private ImageView mImageView;
    private TextView mDescriptionTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        mImageView = findViewById(R.id.image_view_details);
        mDescriptionTextView = findViewById(R.id.image_description_details);
        ImageDBHelper dbHelper = new ImageDBHelper(this);

        String filepath = getIntent().getStringExtra("filepath");
        String filename = getIntent().getStringExtra("filename");
        File file = new File(filepath,filename);
        Bitmap bitmap = dbHelper.getSavedImage(file.getAbsolutePath());
        String description = getIntent().getStringExtra("description");
        mImageView.setImageBitmap(bitmap);

        mDescriptionTextView.setText(description);
    }
}