package com.example.ph32302_mobile.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.example.ph32302_mobile.Adapter.AdapterTV;


import com.example.ph32302_mobile.Dao.ThanhVienDao;

import com.example.ph32302_mobile.MainActivity;
import com.example.ph32302_mobile.Mode.ThanhVienMode;
import com.example.ph32302_mobile.Mode.TheLoaiMode;
import com.example.ph32302_mobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class FragmentThanhVien extends Fragment {


    public FragmentThanhVien() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        // Đặt tiêu đề trên Toolbar khi Fragment này được hiển thị
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setActionBarTitle("Quản lý thành viên");
        }
    }

// ... Các phương thức khác của Fragment

    ArrayList<ThanhVienMode>list=new ArrayList<>();
    ListView listView;
    FloatingActionButton add;
    ThanhVienDao tvdao;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        ThanhVienDao pMdao = new ThanhVienDao(getContext());
        add=view.findViewById(R.id.BtnaddTV);
        listView = view.findViewById(R.id.rlvListThanhVien);
        list = pMdao.SellecAllTV(); // Gọi phương thức trên đối tượng PMdao đã tạo
        AdapterTV adapterPM = new AdapterTV(getActivity(), list);
        listView.setAdapter(adapterPM);
        adapterPM.notifyDataSetChanged();
        // xử lý khi thêm
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater =getLayoutInflater();
                View view = inflater.inflate(R.layout.add_tv, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

                TextInputEditText ten= view.findViewById(R.id.txtTenTVAdd);
                TextInputEditText Date= view.findViewById(R.id.txtDateTvADD);
                String tenTL=ten.getText().toString().trim();
                Button btadd=view.findViewById(R.id.btnAddTV);
                Button huy=view.findViewById(R.id.btncanelTVADD);
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
                            Toast.makeText(getActivity(), "Vui lòng nhập Tên", Toast.LENGTH_SHORT).show();
                        } else if (Date.getText().toString().trim().isEmpty()) {
                            Toast.makeText(getActivity(), "Vui lòng nhập Ngày Sinh", Toast.LENGTH_SHORT).show();
                        } else if (!adapterPM.isValidDate(Date.getText().toString())) {
                            Toast.makeText(getActivity(), "Vui lòng nhập đúng định dạng Ngày Sinh", Toast.LENGTH_SHORT).show();
                        } else{
                            ThanhVienMode tl= new ThanhVienMode(ten.getText().toString(),Date.getText().toString());
                            if(pMdao.addlistTV(tl)){
                                list.clear();
                                list.addAll(pMdao.SellecAllTV());
                                adapterPM.notifyDataSetChanged();
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