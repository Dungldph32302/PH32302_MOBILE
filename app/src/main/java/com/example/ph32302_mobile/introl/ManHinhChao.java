package com.example.ph32302_mobile.introl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ph32302_mobile.MainActivity;
import com.example.ph32302_mobile.R;


public class ManHinhChao extends AppCompatActivity {
    private ImageView welcomeImageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
        welcomeImageView = findViewById(R.id.welcomeImageView);
        progressBar = findViewById(R.id.progressBar);
        // Hiển thị ảnh và vòng tròn xoay
        welcomeImageView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        // Xoay vòng tròn
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000); // Thời gian quay 2 giây
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        progressBar.setAnimation(rotateAnimation);

        // Tạo một Handler để tắt màn hình chào sau 5 giây
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Ẩn ảnh và vòng tròn xoay


                Intent intent=new Intent(ManHinhChao.this, introlActivity.class);
                startActivity(intent);
                // Chuyển đến màn hình chính hoặc màn hình tiếp theo
                // Ví dụ: startMainScreen();
            }
        }, 1500); // 5 giây
    }

    // Hàm để chuyển đến màn hình chính hoặc màn hình tiếp theo
    private void startMainScreen() {
        // Điều hướng đến màn hình chính hoặc màn hình tiếp theo
        // Ví dụ: startActivity(new Intent(this, MainActivity.class));
    }
    }