package com.example.nvgshop.portal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.nvgshop.Data.DatabaseHelper;
import com.example.nvgshop.Fragment.ChangePasswordDialogFragment;
import com.example.nvgshop.R;

public class AccountActivity extends AppCompatActivity implements ChangePasswordDialogFragment.OnPasswordChangedListener {
    private TextView nameTextView, emailTextView;
    private DatabaseHelper databaseHelper;
    private BaseActivity baseActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        baseActivity = new BaseActivity(this);
        baseActivity.setupNavigationView();
        baseActivity.setGreetingText();
        // Khởi tạo đối tượng DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Lấy thông tin tài khoản từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("username", "");

        // Truy vấn cơ sở dữ liệu để lấy thông tin tài khoản dựa trên tên người dùng
        String email = databaseHelper.getAccountEmail(userName);

        // Hiển thị thông tin tài khoản trên giao diện
        nameTextView.setText(userName);
        emailTextView.setText(email);

        Button changePasswordButton = findViewById(R.id.changePasswordButton);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangePasswordDialog();
            }
        });
    }

    private void showChangePasswordDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ChangePasswordDialogFragment dialogFragment = ChangePasswordDialogFragment.newInstance();
        dialogFragment.setOnPasswordChangedListener(this); // Sử dụng AccountActivity làm listener
        dialogFragment.show(fragmentManager, "change_password_dialog");
    }

    @Override
    public void onPasswordChanged(String oldPassword, String newPassword) {
        // Thực hiện kiểm tra mật khẩu cũ và cập nhật mật khẩu mới trong cơ sở dữ liệu
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("username", "");
        boolean isPasswordCorrect = databaseHelper.checkLogin(userName, oldPassword);
        if (isPasswordCorrect) {
            boolean isUpdated = databaseHelper.changePassword(userName, oldPassword, newPassword);
            if (isUpdated) {
                Toast.makeText(AccountActivity.this, "Đã thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AccountActivity.this, "Không thể thay đổi mật khẩu", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AccountActivity.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
        }
    }
}





