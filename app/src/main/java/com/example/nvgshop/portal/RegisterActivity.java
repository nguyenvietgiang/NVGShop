package com.example.nvgshop.portal;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nvgshop.Data.DatabaseHelper;
import com.example.nvgshop.R;

public class RegisterActivity extends AppCompatActivity {

    EditText username, email, password;
    Button signin;
    TextView signup;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.edt_userName);
        email = findViewById(R.id.edt_userEmail);
        password = findViewById(R.id.edt_password);
        signin = findViewById(R.id.btn_signIn);
        signup = findViewById(R.id.tv_login);
        databaseHelper = new DatabaseHelper(this);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString().trim();
                String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();

                // Kiểm tra xem các trường không được để trống
                if (userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem tài khoản đã tồn tại trong CSDL chưa
                if (databaseHelper.checkAccountExists(userName, userEmail)) {
                    Toast.makeText(RegisterActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Thêm tài khoản vào CSDL
                databaseHelper.addAccount(userName, userEmail, userPassword);
                Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                // Chuyển đến LoginActivity sau khi đăng ký thành công
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}
