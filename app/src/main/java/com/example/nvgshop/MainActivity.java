package com.example.nvgshop;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    private BaseActivity baseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);

        baseActivity = new BaseActivity(this); // Truyền vào tham số Activity hiện tại
        baseActivity.setupNavigationView();
        baseActivity.setGreetingText();
    }
}


