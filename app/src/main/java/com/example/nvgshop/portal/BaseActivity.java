package com.example.nvgshop.portal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;

import androidx.drawerlayout.widget.DrawerLayout;

import com.example.nvgshop.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class BaseActivity {
    protected NavigationView navigationView;
    protected Activity activity;

    public BaseActivity(Activity activity) {
        this.activity = activity;
    }

    protected void setupNavigationView() {
        DrawerLayout drawerLayout = activity.findViewById(R.id.drawer_layout);
        navigationView = activity.findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Xử lý sự kiện khi chọn mục trong NavigationView
                switch (menuItem.getItemId()) {
                    case R.id.nav_greeting:
                        Intent greetingIntent = new Intent(activity, AccountActivity.class);
                        activity.startActivity(greetingIntent);
                        break;
                    case R.id.nav_home:
                        Intent homeIntent = new Intent(activity, MainActivity.class);
                        activity.startActivity(homeIntent);
                        break;
                    case R.id.nav_contact:
                        Intent feedbackIntent = new Intent(activity, FeedbackActivity.class);
                        activity.startActivity(feedbackIntent);
                        break;
                    case R.id.nav_card:
                        Snackbar.make(drawerLayout, "Sản phẩm được chọn", Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_logout:
                        // Hiển thị hộp thoại xác nhận đăng xuất
                        showLogoutConfirmationDialog();
                        break;
                }

                // Đóng NavigationView sau khi chọn mục
                drawerLayout.closeDrawers();

                return true;
            }

        });
    }

    protected void setGreetingText() {
        // Lấy username từ SharedPreferences
        SharedPreferences sharedPreferences = activity.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        Menu menu = navigationView.getMenu();
        MenuItem greetingMenuItem = menu.findItem(R.id.nav_greeting);
        greetingMenuItem.setTitle("Xin chào: " + username);
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Đăng xuất");
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Xóa SharedPreferences và quay về LoginActivity
                SharedPreferences sharedPreferences = activity.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(activity, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(intent);
            }
        });
        builder.setNegativeButton("Không", null);
        builder.create().show();
    }

}


