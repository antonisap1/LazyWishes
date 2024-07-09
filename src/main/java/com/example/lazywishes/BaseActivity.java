package com.example.lazywishes;

import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.bottonnav_home:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.bottonnav_contacts:
                startActivity(new Intent(this, ContactListView1.class));
                break;
            case R.id.bottonnav_add:
                startActivity(new Intent(this,Local_Notification.class));

        }
        return true;

    }

}
