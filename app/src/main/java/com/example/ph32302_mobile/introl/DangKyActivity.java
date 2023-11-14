package com.example.ph32302_mobile.introl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ph32302_mobile.R;

public class DangKyActivity extends AppCompatActivity {
TextView tvTroiLai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        tvTroiLai= findViewById(R.id.TvTroLai);

        tvTroiLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangKyActivity.this,introlActivity.class);
                startActivity(intent);
                intent.clone();
            }
        });
    }
}