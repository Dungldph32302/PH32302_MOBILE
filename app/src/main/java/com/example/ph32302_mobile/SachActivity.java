package com.example.ph32302_mobile;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.ph32302_mobile.Adapter.AdapterSach;
import com.example.ph32302_mobile.Dao.SachDao;
import com.example.ph32302_mobile.Dao.TheLoaiDao;
import com.example.ph32302_mobile.Mode.SachMode;

import java.util.ArrayList;

public class SachActivity extends AppCompatActivity {
 RecyclerView recyclerView ;
 SachDao Dao;
 AdapterSach adapterSach;
 ArrayList<SachMode>list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach);
        recyclerView = findViewById(R.id.rlvSach);
        LinearLayoutManager layoutManager=new LinearLayoutManager(SachActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        Dao=new SachDao(SachActivity.this);
        list= Dao.SellecAll();
        adapterSach = new AdapterSach(SachActivity.this,list);
        recyclerView.setAdapter(adapterSach);
        adapterSach.notifyDataSetChanged();
    }


}