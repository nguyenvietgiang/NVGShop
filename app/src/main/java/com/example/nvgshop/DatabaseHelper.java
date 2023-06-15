package com.example.nvgshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "NvgShop.db";

    // Table name
    private static final String TABLE_ACCOUNT = "Account";

    // Columns names
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Tạo câu lệnh SQL để tạo bảng Account
        String createTableQuery = "CREATE TABLE " + TABLE_ACCOUNT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT"
                + ")";

        // Thực thi câu lệnh tạo bảng
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Xóa bảng Account cũ (nếu tồn tại)
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);

        // Tạo lại bảng Account
        onCreate(sqLiteDatabase);
    }
    // hàm phục vụ đăng ký tài khoản
    public void addAccount(String userName, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, userName);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        db.insert(TABLE_ACCOUNT, null, values);
        db.close();
    }
    // Kiểm tra đăng nhập
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

}

