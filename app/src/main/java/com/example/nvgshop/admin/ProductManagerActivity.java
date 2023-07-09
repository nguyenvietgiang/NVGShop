package com.example.nvgshop.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nvgshop.Data.DatabaseHelper;
import com.example.nvgshop.Fragment.EditProductFragment;
import com.example.nvgshop.models.Product;
import com.example.nvgshop.R;
import com.example.nvgshop.portal.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductManagerActivity extends AppCompatActivity {

    private EditText editTextProductName;
    private EditText editTextProductPrice;
    private EditText editTextProductDescription;
    private Button buttonSaveProduct;
    private Button buttonOpenSave;
    private RecyclerView recyclerViewProductList;
    private boolean isAddMode = false;
    private List<Product> productList;
    private ProductAdapter productAdapter;
    private DatabaseHelper databaseHelper;
    private BaseAdminActivity baseActivity;
    private Spinner spinnerProductType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_manager);

        editTextProductName = findViewById(R.id.editTextProductName);
        editTextProductPrice = findViewById(R.id.editTextProductPrice);
        editTextProductDescription = findViewById(R.id.editTextProductDescription);
        buttonSaveProduct = findViewById(R.id.buttonSaveProduct);
        buttonOpenSave = findViewById(R.id.buttonOpenSave);
        spinnerProductType= findViewById(R.id.spinnerProductType);
        recyclerViewProductList = findViewById(R.id.recyclerViewProductList);
        EditText searchEditText = findViewById(R.id.searchEditText);
        Button searchButton = findViewById(R.id.searchButton);
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList);
        recyclerViewProductList.setAdapter(productAdapter);
        recyclerViewProductList.setLayoutManager(new LinearLayoutManager(this));

        baseActivity = new BaseAdminActivity(this); // Truyền vào tham số Activity hiện tại
        baseActivity.setupNavigationView();

        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList);
        recyclerViewProductList.setAdapter(productAdapter);
        recyclerViewProductList.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);
        // Lấy danh sách sản phẩm từ database và cập nhật RecyclerView
        productList.addAll(databaseHelper.getAllProducts());
        productAdapter.setOnDeleteClickListener(new ProductAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                Product product = productList.get(position);
                showDeleteConfirmationDialog(product);
            }
        });

        productAdapter.setOnEditClickListener(new ProductAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(int position) {
                // lấy thon tin san pham
                Product product = productList.get(position);
                // Gọi phương thức để hiển thị thông tin sản phẩm trong EditProductFragment
                showEditProductFragment(product);
            }
        });

        //tìm kiếm
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchEditText.getText().toString();
                performSearch(query);
            }
        });


        buttonOpenSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAddMode = !isAddMode; // Đảo ngược trạng thái
                if (isAddMode) {
                    buttonSaveProduct.setVisibility(View.VISIBLE);
                    editTextProductName.setVisibility(View.VISIBLE);
                    editTextProductPrice.setVisibility(View.VISIBLE);
                    editTextProductDescription.setVisibility(View.VISIBLE);
                    spinnerProductType.setVisibility(View.VISIBLE);
                } else {
                    buttonSaveProduct.setVisibility(View.GONE);
                    editTextProductName.setVisibility(View.GONE);
                    editTextProductPrice.setVisibility(View.GONE);
                    editTextProductDescription.setVisibility(View.GONE);
                    spinnerProductType.setVisibility(View.GONE);
                }
            }
        });
        buttonSaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextProductName.getText().toString();
                String description = editTextProductDescription.getText().toString();
                String priceString = editTextProductPrice.getText().toString();
                String type = spinnerProductType.getSelectedItem().toString();

                // Kiểm tra các trường không được để trống
                if (name.isEmpty() || description.isEmpty() || priceString.isEmpty()) {
                    Toast.makeText(ProductManagerActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra giá phải là số
                double price;
                try {
                    price = Double.parseDouble(priceString);
                } catch (NumberFormatException e) {
                    Toast.makeText(ProductManagerActivity.this, "Giá sản phẩm phải là số", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Thực hiện lưu sản phẩm vào database
                databaseHelper.addProduct(name, description, price, type);

                // Cập nhật danh sách sản phẩm và RecyclerView
                productList.clear();
                productList.addAll(databaseHelper.getAllProducts());
                productAdapter.notifyDataSetChanged();

                // Đặt lại giá trị trống cho các ô input
                editTextProductName.setText("");
                editTextProductDescription.setText("");
                editTextProductPrice.setText("");

                // Hiển thị thông báo thành công
                Toast.makeText(ProductManagerActivity.this, "Lưu sản phẩm thành công", Toast.LENGTH_SHORT).show();
            }
        });

    }
    // truyền query tìm kiếm
    private void performSearch(String query) {
        List<Product> filteredList = new ArrayList<>();

        for (Product product : productList) {
            //trong product models có getname
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(product);
            }
        }

        productAdapter.setData(filteredList);
    }

    private void showDeleteConfirmationDialog(final Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận")
                .setMessage("Bạn muốn xóa sản phẩm này chứ?")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProduct(product);
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void deleteProduct(Product product) {
        databaseHelper.deleteProduct(product.getId());
        productList.remove(product);
        productAdapter.notifyDataSetChanged();
        Toast.makeText(ProductManagerActivity.this, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
    }
     // hiển thị fragment
    private void showEditProductFragment(Product product) {
        EditProductFragment editProductFragment = EditProductFragment.newInstance(product.getId(), product.getName(), product.getDescription(), product.getPrice());
        FragmentManager fragmentManager = getSupportFragmentManager();
        editProductFragment.show(fragmentManager, "edit_product_dialog");
    }
}
