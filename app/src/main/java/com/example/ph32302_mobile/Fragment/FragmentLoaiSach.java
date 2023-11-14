package com.example.ph32302_mobile.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ph32302_mobile.Adapter.AdapterLoaiSach;
import com.example.ph32302_mobile.Adapter.AdapterSach;
import com.example.ph32302_mobile.Dao.SachDao;
import com.example.ph32302_mobile.Dao.TheLoaiDao;
import com.example.ph32302_mobile.MainActivity;
import com.example.ph32302_mobile.Mode.TheLoaiMode;
import com.example.ph32302_mobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class FragmentLoaiSach extends Fragment {
    public FragmentLoaiSach() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        // Đặt tiêu đề trên Toolbar khi Fragment này được hiển thị
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setActionBarTitle("Quản lý thể loại");
        }
    }

// ... Các phương thức khác của Fragment

    RecyclerView recyclerView;
    TheLoaiDao mhDao;
    AdapterLoaiSach adapter;
    ArrayList<TheLoaiMode> list;
    FloatingActionButton btnadd;
    FloatingActionButton btnthemtl;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_sach, container, false);
        recyclerView = view.findViewById(R.id.rlvListLoaiSach);
//        TextView sol=view.findViewById(R.id.TvSL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mhDao = new TheLoaiDao(getActivity());
        list = mhDao.SellecAllTheLoai();
        adapter = new AdapterLoaiSach(getActivity(),list);
        recyclerView.setAdapter(adapter);
        btnthemtl=view.findViewById(R.id.btnthemtl);


        // xử lý khi thêm thể loại
        btnthemtl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater =getLayoutInflater();
                View view = inflater.inflate(R.layout.add_the_loai, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();
                TextInputEditText ten= view.findViewById(R.id.txtTenTLAdd);
                String tenTL=ten.getText().toString().trim();
                Button btadd=view.findViewById(R.id.btnaddTL);
                Button huy=view.findViewById(R.id.btncanelTLAdd);
                // xử lý khi chọn hủy
                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ten.getText().toString().trim().isEmpty()){
                            Toast.makeText(getActivity(), "Vui lòng nhập tên thể loại", Toast.LENGTH_SHORT).show();
                        }else{
                            TheLoaiMode tl= new TheLoaiMode(ten.getText().toString());
                            if(mhDao.addTheLoai(tl)){
                                list.clear();
                                list.addAll(mhDao.SellecAllTheLoai());
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), "Bạn đã thêm 1 Thể Loại mới ", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    }
                });

            }
        });
        return view;
    }
}