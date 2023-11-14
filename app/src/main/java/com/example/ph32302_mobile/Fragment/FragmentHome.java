package com.example.ph32302_mobile.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ph32302_mobile.Dao.PMdao;
import com.example.ph32302_mobile.Dao.ThanhVienDao;
import com.example.ph32302_mobile.MainActivity;
import com.example.ph32302_mobile.R;
import com.example.ph32302_mobile.SachActivity;


public class FragmentHome extends Fragment {
    @Override
    public void onResume() {
        super.onResume();
        // Đặt tiêu đề trên Toolbar khi Fragment này được hiển thị
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setActionBarTitle("Home");
        }
    }

// ... Các phương thức khác của Fragment


    public FragmentHome() {
        // Required empty public constructor
    }


    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ConstraintLayout Sach= view.findViewById(R.id.ctlSach);
         ConstraintLayout TheLoai=view.findViewById(R.id.ctlTheLoai);
        ConstraintLayout PM=view.findViewById(R.id.ctlPM);
        ConstraintLayout TK=view.findViewById(R.id.thongkekkk);

        TextView tvSophieu=view.findViewById(R.id.HomeSoPhieu);
        TextView tvdoanhthu=view.findViewById(R.id.TvDoanhThuHome);
        TextView tvTV=view.findViewById(R.id.tvTVTK);

        TK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTop10Sach frg= new FragmentTop10Sach();
                MainActivity mainActivity=(MainActivity)getActivity();
                mainActivity.replec(frg);
            }
        });
        Sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentSach frg=new FragmentSach();
                MainActivity mainActivity=(MainActivity)getActivity();
                mainActivity.replec(frg);
            }
        });
        TheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentLoaiSach frg=new FragmentLoaiSach();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.replec(frg);
            }
        });
        PM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentPhieuMuon frg=new FragmentPhieuMuon();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.replec(frg);
            }
        });

        // set textView số phiếu mượn
        PMdao pMdao=new PMdao(getActivity());
        int phieu= pMdao.getSoLuongPhieuMuon();
        tvSophieu.setText(String.valueOf(phieu));
        // set doanh thu
        int doanhthu= pMdao.getTongTienThue();
        tvdoanhthu.setText(String.valueOf(doanhthu)+" $");
        ThanhVienDao tvd= new ThanhVienDao(getActivity());
        int tvv=tvd.getSoLuongTV();
        tvTV.setText(String.valueOf(tvv));
        return view;


    }
}