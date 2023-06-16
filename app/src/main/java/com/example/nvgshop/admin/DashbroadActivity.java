package com.example.nvgshop.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.nvgshop.R;
import com.google.android.material.navigation.NavigationView;

public class DashbroadActivity extends AppCompatActivity {
    private BaseAdminActivity baseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbroad);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);

        baseActivity = new BaseAdminActivity(this);
        baseActivity.setupNavigationView();
    }
}
