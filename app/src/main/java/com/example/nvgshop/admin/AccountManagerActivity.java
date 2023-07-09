package com.example.nvgshop.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.nvgshop.Data.DatabaseHelper;
import com.example.nvgshop.R;
import com.example.nvgshop.models.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountManagerActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private BaseAdminActivity baseActivity;
    private  AccountAdapter accountAdapter;
    private RecyclerView recyclerViewAccountList;
    private List<Account> accountsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manager);
        recyclerViewAccountList = findViewById(R.id.recyclerViewAccountList);
        baseActivity = new BaseAdminActivity(this);
        baseActivity.setupNavigationView();

        accountsList = new ArrayList<>();
        accountAdapter = new AccountAdapter(accountsList);
        recyclerViewAccountList.setAdapter(accountAdapter);
        recyclerViewAccountList.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);
        // Lấy danh sách sản phẩm từ database và cập nhật RecyclerView
        accountsList.addAll(databaseHelper.getAllAccount());
    }
}