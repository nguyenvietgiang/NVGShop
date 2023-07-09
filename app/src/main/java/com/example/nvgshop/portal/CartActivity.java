package com.example.nvgshop.portal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nvgshop.Data.DatabaseHelper;
import com.example.nvgshop.R;

public class CartActivity extends AppCompatActivity {
    private BaseActivity baseActivity;
    private TextView textViewOrderDetail;
    private Button buttonPlaceOrder;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        textViewOrderDetail = findViewById(R.id.textViewOrderDetail);
        buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);

        baseActivity = new BaseActivity(this);
        baseActivity.setupNavigationView();
        baseActivity.setGreetingText();

        databaseHelper = new DatabaseHelper(this);

        SharedPreferences cartPreferences = getSharedPreferences("MyCart", Context.MODE_PRIVATE);
        final String orderDetail = cartPreferences.getString("order_detail", "");

        textViewOrderDetail.setText(orderDetail);

        buttonPlaceOrder.setOnClickListener(v -> {
            // Add order to the database
            databaseHelper.addOrder(orderDetail);
            Toast.makeText(CartActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();

            // Clear the cart (SharedPreferences)
            SharedPreferences.Editor editor = cartPreferences.edit();
            editor.clear();
            editor.apply();

            textViewOrderDetail.setText("Cảm ơn đã mua hàng của chúng tôi");

            // có thêm trang cảm ơn mua hàng thì mém vào đây
        });
    }
}
