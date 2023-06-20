package com.example.nvgshop.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import android.graphics.Color;
import android.os.Bundle;

import com.example.nvgshop.Data.DatabaseHelper;
import com.example.nvgshop.R;
import com.example.nvgshop.models.Feedback;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class DashbroadActivity extends AppCompatActivity {
    private BaseAdminActivity baseActivity;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbroad);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);

        baseActivity = new BaseAdminActivity(this);
        baseActivity.setupNavigationView();

        databaseHelper = new DatabaseHelper(this);
        List<Feedback> feedbackList = databaseHelper.getAllFeedback();

        // Tạo danh sách SliceValue để đại diện cho các trạng thái trong biểu đồ
        List<SliceValue> pieData = new ArrayList<>();

        int countPositive = 0;
        int countNeutral = 0;
        int countNegative = 0;

        // Đếm số lượng theo từng trạng thái
        for (Feedback feedback : feedbackList) {
            String status = feedback.getStatus();
            if (status.equals("Tốt")) {
                countPositive++;
            } else if (status.equals("Bình thường")) {
                countNeutral++;
            } else if (status.equals("Không tốt")) {
                countNegative++;
            }
        }

        // Thêm các giá trị và màu sắc cho biểu đồ
        if (countPositive > 0) {
            pieData.add(new SliceValue(countPositive, Color.GREEN).setLabel("Tốt"));
        }
        if (countNeutral > 0) {
            pieData.add(new SliceValue(countNeutral, Color.YELLOW).setLabel("Bình thường"));
        }
        if (countNegative > 0) {
            pieData.add(new SliceValue(countNegative, Color.RED).setLabel("Không tốt"));
        }

        // Tạo đối tượng PieChartData và cung cấp danh sách SliceValue
        PieChartData pieChartData = new PieChartData(pieData);

        // Đặt các tùy chọn cho biểu đồ
        pieChartData.setHasLabels(true); // Hiển thị nhãn trên các phần tử
        pieChartData.setHasLabelsOnlyForSelected(false); // Chỉ hiển thị nhãn cho phần tử được chọn
        pieChartData.setHasLabelsOutside(false); // Hiển thị nhãn bên ngoài biểu đồ

        // Tìm PieChartView trong layout
        PieChartView pieChartView = findViewById(R.id.pieChartView);

        // Gán PieChartData cho PieChartView
        pieChartView.setPieChartData(pieChartData);
    }
}

