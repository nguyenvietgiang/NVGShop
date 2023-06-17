package com.example.nvgshop;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.nvgshop.models.SliderItem;
import com.google.android.material.navigation.NavigationView;

import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class MainActivity extends AppCompatActivity {
    private BaseActivity baseActivity;
    private SliderView imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);

        baseActivity = new BaseActivity(this); // Truyền vào tham số Activity hiện tại
        baseActivity.setupNavigationView();
        baseActivity.setGreetingText();

        imageSlider = findViewById(R.id.imageSlider);
        setupImageSlider();
    }

    private void setupImageSlider() {
        SliderAdapter adapter = new SliderAdapter(); // Tạo adapter cho image slider của bạn

        // Thêm các ảnh vào adapter
        adapter.addItem(new SliderItem(R.drawable.slider1));
        adapter.addItem(new SliderItem(R.drawable.slider2));

        imageSlider.setSliderAdapter(adapter);
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION); // Animation khi chuyển ảnh
        imageSlider.startAutoCycle(); // Tự động chuyển ảnh
    }
}


