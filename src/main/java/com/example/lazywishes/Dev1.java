package com.example.lazywishes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Dev1 extends AppCompatActivity {

    private EditText onomaAgiouEdt, meraEortisEdt,minasEortisEdt ,perigrafiZwhsAgiouEdt, addMessage  ;
    private Button addEortiBtn,addMessageBtn;
    private Eortes_Database dbHandler;
    private MessageDbHelper dbHelper;
    private ImageDBHelper imageDBHelper;

    @SuppressLint({"MissingInflatedId", "WrongThread"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dev1);
        onomaAgiouEdt = findViewById(R.id.idEortiName);
        meraEortisEdt = findViewById(R.id.idEortiDay);
        minasEortisEdt = findViewById(R.id.idEortiMonth);
        perigrafiZwhsAgiouEdt = findViewById(R.id.idPerigrafiAgiou);
        addMessage = findViewById(R.id.idMessages);
        addEortiBtn = findViewById(R.id.idBtnAddEorti);
        addMessageBtn = findViewById(R.id.idBtnAddMessage);
        dbHandler = new Eortes_Database(Dev1.this);
        dbHelper = new MessageDbHelper(Dev1.this);
        imageDBHelper = new ImageDBHelper(Dev1.this);


        addEortiBtn.setOnClickListener(v -> {


            String eortiName = onomaAgiouEdt.getText().toString();
            String EortiDay = meraEortisEdt.getText().toString();
            String EortiMonth = minasEortisEdt.getText().toString();
            String description = perigrafiZwhsAgiouEdt.getText().toString();




            if (eortiName.isEmpty() && EortiDay.isEmpty() && EortiMonth.isEmpty() && description.isEmpty()) {
                Toast.makeText(Dev1.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                return;
            }


            dbHandler.addEorti(eortiName, EortiDay, EortiMonth,description);


            Toast.makeText(Dev1.this, "Eorti has been added.", Toast.LENGTH_SHORT).show();
            onomaAgiouEdt.setText("");
            meraEortisEdt.setText("");
            minasEortisEdt.setText("");
            perigrafiZwhsAgiouEdt.setText("");


        });
        addMessageBtn.setOnClickListener(v -> {

            String message = addMessage.getText().toString();
            if (message.isEmpty())
            {
                Toast.makeText(Dev1.this,"Please enter a message",Toast.LENGTH_SHORT).show();
                return;

            }
            dbHelper.addMsg(message);
            Toast.makeText(Dev1.this, "Message has been added.", Toast.LENGTH_SHORT).show();
            addMessage.setText("");


        });


    }
}