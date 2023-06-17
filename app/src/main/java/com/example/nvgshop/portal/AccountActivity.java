package com.example.nvgshop.portal;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nvgshop.Data.DatabaseHelper;
import com.example.nvgshop.R;
import com.example.nvgshop.portal.BaseActivity;

public class AccountActivity extends AppCompatActivity {
    private BaseActivity baseActivity;
    private DatabaseHelper databaseHelper;
    private TextView nameTextView, emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        baseActivity = new BaseActivity(this); // Truyền vào tham số Activity hiện tại
        baseActivity.setupNavigationView();
        baseActivity.setGreetingText();

        // Khởi tạo đối tượng DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);

        // Lấy thông tin tài khoản từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("username", "");

        // Truy vấn cơ sở dữ liệu để lấy thông tin tài khoản dựa trên tên người dùng
        String email = databaseHelper.getAccountEmail(userName);

        // Hiển thị thông tin tài khoản trên giao diện
        nameTextView.setText(userName);
        emailTextView.setText(email);
    }
}



