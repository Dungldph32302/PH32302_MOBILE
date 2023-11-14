package com.example.ph32302_mobile.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ph32302_mobile.Adapter.AdapterSach;
import com.example.ph32302_mobile.Dao.SachDao;
import com.example.ph32302_mobile.Dao.TheLoaiDao;
import com.example.ph32302_mobile.MainActivity;
import com.example.ph32302_mobile.Mode.SachMode;
import com.example.ph32302_mobile.Mode.TheLoaiMode;
import com.example.ph32302_mobile.R;
import com.example.ph32302_mobile.Spinner.AdapterSpinnerMaTL;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class FragmentSach extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
        // Đặt tiêu đề trên Toolbar khi Fragment này được hiển thị
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setActionBarTitle("Quản lý sách");
        }
    }

// ... Các phương thức khác của Fragment

    public FragmentSach() {
        // Required empty public constructor
    }
    AdapterSach adapter;
    SachDao mhDao;
    Context context;

    RecyclerView recyclerView;
    ArrayList<SachMode> list;
    FloatingActionButton btadd;
    TheLoaiDao tlDao;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sach, container, false);
        recyclerView = view.findViewById(R.id.rlvListSach);
        TextView sol=view.findViewById(R.id.TvSL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mhDao = new SachDao(getActivity());
        list = mhDao.SellecAll();
        adapter = new AdapterSach(getActivity(), list);
        recyclerView.setAdapter(adapter);
        btadd = view.findViewById(R.id.BtnaddSach);


        // add sách
        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater =getLayoutInflater();
                View view = inflater.inflate(R.layout.add_sach, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();
                // ánh xạ các thành phần
                TextInputEditText txtMaSach,txtTenSach,txtGia;
//                txtMaSach=view.findViewById(R.id.txtAddMaSach);
                txtTenSach=view.findViewById(R.id.txtAddTenSach);
                txtGia=view.findViewById(R.id.txtAddGiaSach);
                Spinner spinner=view.findViewById(R.id.spinneraddSachMaTL);
                Button save,btdelete;
                Button huy=view.findViewById(R.id.btncaneladd);
                save=view.findViewById(R.id.btnaddSach);
                //set adapte
                TheLoaiDao theLoaiDao = new TheLoaiDao(getActivity());
                ArrayList<TheLoaiMode>list1=theLoaiDao.SellecAllTheLoai();
                AdapterSpinnerMaTL adapter1 = new AdapterSpinnerMaTL(getActivity(), list1);
                spinner.setAdapter(adapter1);
                // xủa lý khi chọn spinner
                int[] selectedItemSpinner = {0};
                // lấy string từ spinner
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedItemSpinner[0] = ((TheLoaiMode) spinner.getItemAtPosition(position)).getMaTheLoai();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                // xử lý khi ấn hủy
                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      dialog.dismiss();
                    }
                });
                // xử lý khi thêm
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String ma = txtMaSach.getText().toString().trim();
                        int matl=selectedItemSpinner[0];
//                int gia = Integer.parseInt(Gia.getText().toString());
                        String giaText = txtGia.getText().toString();
                        int gia=0;
                        // int sotiet=Integer.valueOf(edtThanhTien.getText().toString());
                        String TenSach = txtTenSach.getText().toString().trim();
                        if(TenSach.isEmpty()) {
                            Toast.makeText(getActivity(), "Vui lòng nhập tên sách", Toast.LENGTH_SHORT).show();
                        } else if(matl<0){
                            Toast.makeText(getActivity(), "Vui lòng chọn thể loại ", Toast.LENGTH_SHORT).show();
                        }else if(gia<0){
                            Toast.makeText(getActivity(), "Vui lòng nhập giá", Toast.LENGTH_SHORT).show();
                        } else if (giaText.isEmpty()) {
                            Toast.makeText(getActivity(), "Vui lòng nhập  giá " +
                                    "", Toast.LENGTH_SHORT).show();
                        }else {
                            gia=Integer.parseInt(giaText);
                            SachMode dv= new SachMode(TenSach,selectedItemSpinner[0],gia);
                            try {
                                if (mhDao.addSach(dv)) {
                                    list.clear();
                                    list.addAll(mhDao.SellecAll());
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });

            }
        });
        return view;
    }
}