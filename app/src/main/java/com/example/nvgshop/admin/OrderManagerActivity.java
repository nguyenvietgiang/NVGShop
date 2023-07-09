package com.example.nvgshop.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nvgshop.Data.DatabaseHelper;
import com.example.nvgshop.R;

public class OrderManagerActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private BaseAdminActivity baseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manager);
        baseActivity = new BaseAdminActivity(this);
        baseActivity.setupNavigationView();
    }
}