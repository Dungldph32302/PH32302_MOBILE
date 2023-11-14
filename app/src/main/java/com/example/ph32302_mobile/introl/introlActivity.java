package com.example.ph32302_mobile.introl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ph32302_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class introlActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private Handler sliderHandler = new Handler();

    private LinearLayout indicatorLayout;
    private int dotsCount;
    private ImageView[] dots;

 AppCompatButton btnDangnhap;
 TextView tvsinup;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introl);
        viewPager = findViewById(R.id.viewPager);
        btnDangnhap=findViewById(R.id.btDangnhap);
        tvsinup=findViewById(R.id.TvSignUp);

        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(introlActivity.this,ActivityDangNhap.class);
                startActivity(intent);
            }
        });
//        tvsinup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(introlActivity.this,DangKyActivity.class);
//                startActivity(intent);
//            }
//        });

        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.img_14);
        imageList.add(R.drawable.img_17);
        imageList.add(R.drawable.img_20);
        imageList.add(R.drawable.img_11);

        ImageAdapter adapter = new ImageAdapter(imageList);
        viewPager.setAdapter(adapter);
        autoSlide();

        indicatorLayout = findViewById(R.id.indicatorLayout);
        dotsCount = adapter.getItemCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_dot_inactive));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(8, 0, 8, 0);
            indicatorLayout.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_dot_active));

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(introlActivity.this, R.drawable.ic_dot_inactive));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(introlActivity.this, R.drawable.ic_dot_active));
            }
        });
    }
    private boolean isAutoForward = true; // Biến để kiểm tra xem slider đang tự động chạy về phía trước hay phía sau

    private void autoSlide() {
        sliderHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentPosition = viewPager.getCurrentItem();
                int nextPosition;

                if (isAutoForward) {
                    nextPosition = currentPosition + 1;
                } else {
                    nextPosition = currentPosition - 1;
                }

                if (nextPosition >= viewPager.getAdapter().getItemCount()) {
                    isAutoForward = false;
                    nextPosition = viewPager.getAdapter().getItemCount() - 2;
                } else if (nextPosition < 0) {
                    isAutoForward = true;
                    nextPosition = 1;
                }

                viewPager.setCurrentItem(nextPosition);
                autoSlide();
            }
        }, 5000); // Thời gian chờ giữa các lần chuyển ảnh (3 giây ở đây)
    }
    }