package com.example.ph32302_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ph32302_mobile.Dao.ThuThuDao;
import com.example.ph32302_mobile.Fragment.FragmentDoiMK;
import com.example.ph32302_mobile.Fragment.FragmentHome;
import com.example.ph32302_mobile.Fragment.FragmentLoaiSach;
import com.example.ph32302_mobile.Fragment.FragmentPhieuMuon;
import com.example.ph32302_mobile.Fragment.FragmentSach;
import com.example.ph32302_mobile.Fragment.FragmentTaoAcc;
import com.example.ph32302_mobile.Fragment.FragmentThanhVien;
import com.example.ph32302_mobile.Fragment.FragmentThongKe;
import com.example.ph32302_mobile.Fragment.FragmentTop10Sach;
import com.example.ph32302_mobile.introl.introlActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    NavigationView nav;
    private DrawerLayout drawable;
    private Toolbar toolbar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        nav=findViewById(R.id.navigationView);
        View v=nav.getHeaderView(0);
        TextView tvname=v.findViewById(R.id.welcomeName);

        setSupportActionBar(toolbar);
        drawable=findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(this,drawable,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawable.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        Intent intent = getIntent();
        String ten = intent.getStringExtra("us");
        ThuThuDao thuThuDao= new ThuThuDao(MainActivity.this);
        int quen=thuThuDao.quyen(ten);
        if(quen==0){
            nav.getMenu().findItem(R.id.tk).setVisible(false);
        }
        tvname.setText( "Welcome "+ten);

        bottomNavigationView=findViewById(R.id.btnNavigation);
        FragmentHome frg=new FragmentHome();
        replec(frg);
        // xử lý khi click vào navigationView
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.PhieuMuon){
                    FragmentPhieuMuon frg=new FragmentPhieuMuon();
                    replec(frg);
                } else if (item.getItemId()==R.id.LoaiSach) {
                        FragmentLoaiSach frg=new FragmentLoaiSach();
                        replec(frg);
                } else if (item.getItemId()==R.id.Sach) {
                        FragmentSach frg = new FragmentSach();
                        replec(frg);
                }else if (item.getItemId()==R.id.PhieuMuon) {
                    FragmentPhieuMuon frg=new FragmentPhieuMuon();
                    replec(frg);
                }else if (item.getItemId()==R.id.ThanhVien) {
                    FragmentThanhVien frg=new FragmentThanhVien();
                    replec(frg);
                }else if (item.getItemId()==R.id.nav_setting) {
                    FragmentTop10Sach frg=new FragmentTop10Sach();
                    replec(frg);
                }else if (item.getItemId()==R.id.checkout) {
                   Intent intent1= new Intent(MainActivity.this, introlActivity.class);
                   startActivity(intent1);
                }else if (item.getItemId()==R.id.tk) {
                    FragmentTaoAcc frg= new FragmentTaoAcc();
                    replec(frg);
                }else if (item.getItemId()==R.id.doimk) {
                    FragmentDoiMK frg= new FragmentDoiMK();
                    replec(frg);
                }else if (item.getItemId()==R.id.nav_check_out) {
                    FragmentThongKe frg= new FragmentThongKe();
                    replec(frg);
                }
                return true;
            }
        });


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.QuanLy){
                    FragmentHome frg=new FragmentHome();
                    replec(frg);
                }else if(item.getItemId()==R.id.sach) {
                    FragmentSach frg = new FragmentSach();
                    replec(frg);
                }else if(item.getItemId()==R.id.TheLoai) {
                    FragmentLoaiSach frg = new FragmentLoaiSach();
                    replec(frg);
                }else if(item.getItemId()==R.id.navThongKe) {
                    FragmentTop10Sach frg = new FragmentTop10Sach();
                    replec(frg);
                }
//                } else if (item.getItemId()==R.id.TheLoai) {
//                    FragmentPhieuMuon frg=new FragmentPhieuMuon();
//                    replec(frg);
//                }
                return true;
            }
        });
    }
    public void replec(Fragment fragment){
        FragmentManager frg=getSupportFragmentManager();
        frg.beginTransaction().replace(R.id.frmBai3,fragment).commit();
    }
// set tên  toobal
    public void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}