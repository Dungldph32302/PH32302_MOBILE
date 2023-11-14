package com.example.ph32302_mobile.introl;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ph32302_mobile.Dao.ThuThuDao;
import com.example.ph32302_mobile.MainActivity;
import com.example.ph32302_mobile.R;

public class ActivityDangNhap extends AppCompatActivity {
 TextView TvTroLai,TvSignUp;
    EditText txtusser,txtpassword;
    Button btnlogin;
    CheckBox chk;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        txtusser=findViewById(R.id.txtEmial);
        txtpassword=findViewById(R.id.txtpasswordintro);
        btnlogin=findViewById(R.id.bt2);
        chk=findViewById(R.id.chkRemenber);

        TvTroLai=findViewById(R.id.TvTroLaidn);
        TvSignUp=findViewById(R.id.TvSignUpdn);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("savedUsername", "");
        String savedPassword = sharedPreferences.getString("savedPassword", "");


        // xử lý lưu mật khẩu
        txtusser.setText(savedUsername);
        txtpassword.setText(savedPassword);
        TvTroLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ActivityDangNhap.this,introlActivity.class);
                startActivity(intent);
            }
        });
        TvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ActivityDangNhap.this,DangKyActivity.class);
                startActivity(intent);
            }
        });

        // xử lý dữ liệu đăng n hập ư
        ThuThuDao userDao1= new ThuThuDao(this);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten=txtusser.getText().toString();
                String mk=txtpassword.getText().toString();
                if(userDao1.checkTaiKoan(ten)){
                    if(userDao1.checkdangnhap(ten,mk)){
                        Intent intent= new Intent(ActivityDangNhap.this, MainActivity.class);
                        intent.putExtra("nd","user");
                        intent.putExtra("us",ten);
                        startActivity(intent);
                        Toast.makeText(ActivityDangNhap.this,"Đăng Nhập thành công", Toast.LENGTH_LONG).show();
                         if(chk.isChecked()){
                             SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                             SharedPreferences.Editor editor = sharedPreferences.edit();
                             editor.putString("savedUsername", ten);
                             editor.putString("savedPassword", mk);
                             editor.apply();
                         }
                    }else {
//                        Drawable errorBorder = getResources().getDrawable(R.drawable.eros);
//                        txtpassword.setBackground(errorBorder);
                        Toast.makeText(ActivityDangNhap.this,"Sai password , Đăng Nhập thất bại !", Toast.LENGTH_LONG).show();
                    }
                }
//                else if(userDao1.checkAdmin(ten)){
//                    if(userDao1.checkdangnhapAdmin(ten,mk)){
//                        Intent intent= new Intent(ActivityDangNhap.this, MainActivity.class);
//                        startActivity(intent);
//                        Toast.makeText(ActivityDangNhap.this,"Đăng Nhập thành công", Toast.LENGTH_LONG).show();
//                        if(chk.isChecked()){
//                            SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putString("savedUsername", ten);
//                            editor.putString("savedPassword", mk);
//                            editor.apply();
//                        }
//                    }else {
////                        Drawable errorBorder = getResources().getDrawable(R.drawable.eros);
////                        txtpassword.setBackground(errorBorder);
//                        Toast.makeText(ActivityDangNhap.this,"Sai password , Đăng Nhập thất bại !", Toast.LENGTH_LONG).show();
//                    }
//                }
                else{
//                    Drawable errorBorder = getResources().getDrawable(R.drawable.eros);
//                    txtusser.setBackground(errorBorder);
                    Toast.makeText(ActivityDangNhap.this,"Tài Khoản không tồn tại", Toast.LENGTH_LONG).show();
                }
                // chek tài khoản admin
            }
        });
    }
}