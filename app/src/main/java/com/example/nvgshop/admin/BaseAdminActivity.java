package com.example.nvgshop.admin;
import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.nvgshop.portal.LoginActivity;
import com.example.nvgshop.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class BaseAdminActivity {
    protected NavigationView navigationView;
    protected Activity activity;

    public BaseAdminActivity(Activity activity) {
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
                    case R.id.nav_user:
                        Intent accountIntent = new Intent(activity, AccountManagerActivity.class);
                        activity.startActivity(accountIntent);
                        break;
                    case R.id.nav_product:
                        Intent productIntent = new Intent(activity, ProductManagerActivity.class);
                        activity.startActivity(productIntent);
                        break;
                    case R.id.nav_card:
                        Intent orderIntent = new Intent(activity, OrderManagerActivity.class);
                        activity.startActivity(orderIntent);
                        break;
                    case R.id.nav_contact:
                        Intent dashbroadIntent = new Intent(activity, DashbroadActivity.class);
                        activity.startActivity(dashbroadIntent);
                        break;
                    case R.id.nav_logout:
                        Intent loginIntent = new Intent(activity, LoginActivity.class);
                        activity.startActivity(loginIntent);
                        break;
                }
                // Đóng NavigationView sau khi chọn mục
                drawerLayout.closeDrawers();

                return true;
            }

        });
    }
}