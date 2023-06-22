package com.example.nvgshop.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PieChartView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.nvgshop.Data.DatabaseHelper;
import com.example.nvgshop.R;
import com.example.nvgshop.models.Account;
import com.example.nvgshop.models.Feedback;
import com.example.nvgshop.models.Product;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DashbroadActivity extends AppCompatActivity {
    private BaseAdminActivity baseActivity;
    private DatabaseHelper databaseHelper;
    private TextView tvAccountCount;
    private TextView tvProductCount;
    private TextView tvFeedbackCount;
    private TextView tvCartCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbroad);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        tvAccountCount = findViewById(R.id.tvFeedbackCount);
        tvProductCount = findViewById(R.id.tvProductCount);
        tvFeedbackCount = findViewById(R.id.tvFeedbackCount);
        tvCartCount = findViewById(R.id.tvCartCount);
        baseActivity = new BaseAdminActivity(this);
        baseActivity.setupNavigationView();

        databaseHelper = new DatabaseHelper(this);
        List<Feedback> feedbackList = databaseHelper.getAllFeedback();

        int feedbackCount = feedbackList.size();
        // Hiển thị số lượng phản hồi trong TextView
        tvFeedbackCount.setText("Số lượng phản hồi: " + feedbackCount);

        List<Product> productList = databaseHelper.getAllProducts();
        int productCount = productList.size();
        // Hiển thị số lượng sản phẩm trong TextView
        tvProductCount.setText("Số lượng sản phẩm: " + productCount);

        List<Account> accountListList = databaseHelper.getAllAccount();
        int accountCount = accountListList.size();
        // Hiển thị số lượng User trong TextView
        tvAccountCount.setText("Số lượng User: " + accountCount);

        addColumnChartData(productList);
        addPieChartData(feedbackList);
    }
         // column Chart
    private void addColumnChartData(List<Product> productList) {
        // Sắp xếp danh sách sản phẩm theo giá tăng dần
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Double.compare(p1.getPrice(), p2.getPrice());
            }
        });

        List<AxisValue> axisValues = new ArrayList<>();
        List<Column> columns = new ArrayList<>();

        int[] colors = {Color.GREEN, Color.YELLOW, Color.RED};
        String[] priceLabels = {"Dưới $50", "$50 - $100", "Trên $100"};

        int[] productCounts = new int[3];

        for (Product product : productList) {
            double price = product.getPrice();
            if (price < 50) {
                productCounts[0]++;
            } else if (price >= 50 && price <= 100) {
                productCounts[1]++;
            } else if (price > 100) {
                productCounts[2]++;
            }
        }

        for (int i = 0; i < priceLabels.length; i++) {
            axisValues.add(new AxisValue(i).setLabel(priceLabels[i]));
            List<SubcolumnValue> subcolumnValues = new ArrayList<>();
            subcolumnValues.add(new SubcolumnValue(productCounts[i], colors[i]));
            Column column = new Column(subcolumnValues);
            columns.add(column);
        }

        ColumnChartData columnChartData = new ColumnChartData(columns);
        Axis axis = new Axis(axisValues);
        axis.setTextSize(12);
        columnChartData.setAxisXBottom(axis);

        ColumnChartView columnChartView = findViewById(R.id.chart);
        columnChartView.setColumnChartData(columnChartData);
    }
    // pie Chart
    private void addPieChartData(List<Feedback> feedbackList) {
        List<SliceValue> pieData = new ArrayList<>();

        int countPositive = 0;
        int countNeutral = 0;
        int countNegative = 0;

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

        if (countPositive > 0) {
            pieData.add(new SliceValue(countPositive, Color.GREEN).setLabel("Tốt"));
        }
        if (countNeutral > 0) {
            pieData.add(new SliceValue(countNeutral, Color.YELLOW).setLabel("Bình thường"));
        }
        if (countNegative > 0) {
            pieData.add(new SliceValue(countNegative, Color.RED).setLabel("Không tốt"));
        }

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        pieChartData.setHasLabelsOnlyForSelected(false);
        pieChartData.setHasLabelsOutside(false);

        PieChartView pieChartView = findViewById(R.id.pieChartView);
        pieChartView.setPieChartData(pieChartData);
    }
}


