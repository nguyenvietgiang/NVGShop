package com.example.nvgshop.portal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nvgshop.Data.DatabaseHelper;
import com.example.nvgshop.R;

public class FeedbackActivity extends AppCompatActivity {
    private BaseActivity baseActivity;
    private Spinner feedbackRatingSpinner;
    private EditText feedbackContentEditText;
    private DatabaseHelper DataHelper;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        baseActivity = new BaseActivity(this);
        baseActivity.setupNavigationView();
        baseActivity.setGreetingText();

        DataHelper  = new DatabaseHelper(this);

        feedbackRatingSpinner = findViewById(R.id.feedback_rating_spinner);
        feedbackContentEditText = findViewById(R.id.feedback_content_edittext);
        sendButton = findViewById(R.id.send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFeedbackToDatabase();
            }
        });
    }

    private void addFeedbackToDatabase() {
        // Lấy thông tin từ các thành phần giao diện
        String name = getNameFromSharedPreferences();
        String status = feedbackRatingSpinner.getSelectedItem().toString();
        String content = feedbackContentEditText.getText().toString();

        if (content.isEmpty()) {
            // Nếu nội dung phản hồi rỗng, hiển thị thông báo lỗi
            Toast.makeText(this, "Vui lòng nhập nội dung phản hồi", Toast.LENGTH_SHORT).show();
        } else {
            // Nếu nội dung phản hồi không rỗng, thêm phản hồi vào cơ sở dữ liệu
            DataHelper.addFeedback(name, status, content);

            // Hiển thị thông báo hoặc thực hiện các tác vụ khác sau khi thêm phản hồi thành công
            Toast.makeText(this, "Phản hồi đã được gửi", Toast.LENGTH_SHORT).show();
        }
    }


    private String getNameFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", "");
    }
}
