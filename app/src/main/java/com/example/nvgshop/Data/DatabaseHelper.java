package com.example.nvgshop.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.nvgshop.models.Account;
import com.example.nvgshop.models.Product;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "NvgShop.db";

    private static final String TABLE_ACCOUNT = "Account";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    private static final String TABLE_PRODUCT = "Product";
    private static final String COLUMN_PRODUCT_ID = "Id";
    private static final String COLUMN_PRODUCT_NAME = "Name";
    private static final String COLUMN_PRODUCT_DESCRIPTION = "Description";
    private static final String COLUMN_PRODUCT_PRICE = "Price";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableAccountQuery = "CREATE TABLE " + TABLE_ACCOUNT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT"
                + ")";

        sqLiteDatabase.execSQL(createTableAccountQuery);

        String createTableProductQuery = "CREATE TABLE " + TABLE_PRODUCT + "("
                + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PRODUCT_NAME + " TEXT, "
                + COLUMN_PRODUCT_DESCRIPTION + " TEXT, "
                + COLUMN_PRODUCT_PRICE + " REAL"
                + ")";

        sqLiteDatabase.execSQL(createTableProductQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        onCreate(sqLiteDatabase);
    }

    public void addAccount(String userName, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, userName);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_ACCOUNT, null, values);
        db.close();
    }

    public List<Account> getAllAccount() {

        List<Account> accountsList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNT, null);

        if (cursor.moveToFirst()) {
            do {
                int accountId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                String pass = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));

                Account accounts = new Account(accountId , name, email, pass);
                accountsList.add(accounts);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return accountsList;
    }

   // kiểm tra tài khoản trong csdl
    public boolean checkLogin(String userName, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USERNAME};
        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {userName, password};
        Cursor cursor = db.query(TABLE_ACCOUNT, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }
    //kiểm tra validate tên và email
    public boolean checkAccountExists(String userName, String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USERNAME + " = ? OR " + COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {userName, userEmail};
        Cursor cursor = db.query(TABLE_ACCOUNT, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    // lấy thông tin email
    public String getAccountEmail(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_EMAIL};
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {userName};
        Cursor cursor = db.query(TABLE_ACCOUNT, columns, selection, selectionArgs, null, null, null);

        String email = "";
        if (cursor.moveToFirst()) {
            email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
        }

        cursor.close();
        db.close();
        return email;
    }

    public void addProduct(String name, String description, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, name);
        values.put(COLUMN_PRODUCT_DESCRIPTION, description);
        values.put(COLUMN_PRODUCT_PRICE, price);
        db.insert(TABLE_PRODUCT, null, values);
        db.close();
    }
    public List<Product> getAllProducts() {

        List<Product> productList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCT, null);

        if (cursor.moveToFirst()) {
            do {
                int productId = cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_DESCRIPTION));
                double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRODUCT_PRICE));

                Product product = new Product(productId, name, description, price);
                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return productList;
    }

    public void deleteProduct(int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT, COLUMN_PRODUCT_ID + " = ?", new String[]{String.valueOf(productId)});
        db.close();
    }

}