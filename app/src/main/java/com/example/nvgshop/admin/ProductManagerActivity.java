package com.example.nvgshop.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nvgshop.DatabaseHelper;
import com.example.nvgshop.Product;
import com.example.nvgshop.R;

import java.util.ArrayList;
import java.util.List;

public class ProductManagerActivity extends AppCompatActivity {

    private EditText editTextProductName;
    private EditText editTextProductPrice;
    private EditText editTextProductDescription;
    private Button buttonSaveProduct;
    private RecyclerView recyclerViewProductList;

    private List<Product> productList;
    private ProductAdapter productAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_manager);

        editTextProductName = findViewById(R.id.editTextProductName);
        editTextProductPrice = findViewById(R.id.editTextProductPrice);
        editTextProductDescription = findViewById(R.id.editTextProductDescription);
        buttonSaveProduct = findViewById(R.id.buttonSaveProduct);
        recyclerViewProductList = findViewById(R.id.recyclerViewProductList);

        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList);
        recyclerViewProductList.setAdapter(productAdapter);
        recyclerViewProductList.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);
        // Lấy danh sách sản phẩm từ database và cập nhật RecyclerView
        productList.addAll(databaseHelper.getAllProducts());
        productAdapter.notifyDataSetChanged();
        buttonSaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextProductName.getText().toString();
                String description = editTextProductDescription.getText().toString();
                double price = Double.parseDouble(editTextProductPrice.getText().toString());

                databaseHelper.addProduct(name, description, price);

                productList.clear();
                productList.addAll(databaseHelper.getAllProducts());
                productAdapter.notifyDataSetChanged();

                editTextProductName.setText("");
                editTextProductDescription.setText("");
                editTextProductPrice.setText("");
            }
        });
    }
}
