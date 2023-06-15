package com.example.nvgshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button signin;
    TextView signup;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.edt_userName);
        password = findViewById(R.id.edt_password);
        signin = findViewById(R.id.btn_signIn);
        signup = findViewById(R.id.tv_register);
        databaseHelper = new DatabaseHelper(this);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString();
                String userPassword = password.getText().toString();
                // Kiểm tra đăng nhập
                if (databaseHelper.checkLogin(userName, userPassword)) {
                    // Đăng nhập thành công
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", userName);
                    editor.apply();

                    // Chuyển đến MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Để đóng LoginActivity sau khi chuyển đến MainActivity
                } else {
                    // Đăng nhập không thành công
                    Toast.makeText(LoginActivity.this, "Tên tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }

            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}

