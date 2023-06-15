package com.example.nvgshop;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Lấy username từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        // Thiết lập greeting trong NavigationView
        Menu menu = navigationView.getMenu();
        MenuItem greetingMenuItem = menu.findItem(R.id.nav_greeting);
        greetingMenuItem.setTitle("Xin chào: " + username);

        // Thiết lập sự kiện khi chọn mục trong NavigationView
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Xử lý sự kiện khi chọn mục trong NavigationView
                switch (menuItem.getItemId()) {
                    case R.id.nav_item1:
                        // Điều hướng đến màn hình Home
                        Snackbar.make(drawerLayout, "Home được chọn", Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_item2:
                        // Điều hướng đến màn hình Profile
                        Snackbar.make(drawerLayout, "Profile được chọn", Snackbar.LENGTH_SHORT).show();
                        break;
                    //Thêm các mục khác tại đây
                }

                // Đóng NavigationView sau khi chọn mục
                drawerLayout.closeDrawers();

                return true;
            }
        });
    }
}
