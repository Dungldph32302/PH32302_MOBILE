package com.example.ph32302_mobile.Fragment;

import static com.example.ph32302_mobile.R.drawable.baseline_add_24;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ph32302_mobile.Adapter.AdapterPhieuMuon;
import com.example.ph32302_mobile.Dao.PMdao;
import com.example.ph32302_mobile.Dao.SachDao;
import com.example.ph32302_mobile.Dao.ThanhVienDao;
import com.example.ph32302_mobile.Dao.TheLoaiDao;
import com.example.ph32302_mobile.Dao.ThuThuDao;
import com.example.ph32302_mobile.MainActivity;
import com.example.ph32302_mobile.Mode.PhieuMuonMode;
import com.example.ph32302_mobile.Mode.SachMode;
import com.example.ph32302_mobile.Mode.ThanhVienMode;
import com.example.ph32302_mobile.Mode.TheLoaiMode;
import com.example.ph32302_mobile.Mode.ThuThuMode;
import com.example.ph32302_mobile.R;
import com.example.ph32302_mobile.Spinner.AdapterSpinnerMaSach;
import com.example.ph32302_mobile.Spinner.AdapterSpinnerMaTL;
import com.example.ph32302_mobile.Spinner.AdapterSpinnerMaTT;
import com.example.ph32302_mobile.Spinner.AdapterSpinnerMaTV;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


public class FragmentPhieuMuon extends Fragment {
    @Override
    public void onResume() {
        super.onResume();
        // Đặt tiêu đề trên Toolbar khi Fragment này được hiển thị
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setActionBarTitle("Quản lý phiếu mượn");
        }
    }
// ... Các phương thức khác của Fragment
    public FragmentPhieuMuon() {
        // Required empty public constructor
    }
ArrayList<PhieuMuonMode>list=new ArrayList<>();
    ListView listView;
AdapterPhieuMuon adapterPhieuMuon;
PMdao pMdao;
FloatingActionButton add;
AdapterPhieuMuon adapterPM;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);
        add = view.findViewById(R.id.BtnaddPM);
        // Tạo một đối tượng PMdao
        PMdao pMdao = new PMdao(getContext());

        listView = view.findViewById(R.id.PhieuMuonlist);
        list = pMdao.getAllPhieuMuonModes(); // Gọi phương thức trên đối tượng PMdao đã tạo
        adapterPM = new AdapterPhieuMuon(getActivity(), list);
        listView.setAdapter(adapterPM);
        adapterPM.notifyDataSetChanged();

        TextView tvsort=view.findViewById(R.id.TVsort);
        tvsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable= ContextCompat.getDrawable(getContext(),R.drawable.baseline_list_24);
                tvsort.setBackground(drawable);
                Comparator<PhieuMuonMode> giathue=new Comparator<PhieuMuonMode>() {
                    @Override
                    public int compare(PhieuMuonMode o1, PhieuMuonMode o2) {

                        return Integer.compare(o1.getGiaThue(),o2.getGiaThue());
                    }
                };
                Collections.sort(list,giathue);
                adapterPM.notifyDataSetChanged();
                Toast.makeText(getContext(), "List đã sắp xếp", Toast.LENGTH_SHORT).show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View view = inflater.inflate(R.layout.add_pm, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();
                Spinner ThanhVien = view.findViewById(R.id.spinnerPMThanhVien);
                Spinner ThuThu = view.findViewById(R.id.spinneraddPMThuThu);
                Spinner Sach = view.findViewById(R.id.spinneraddPMSach);

                // set nội dung
                // set spinner
                ThanhVienDao tv = new ThanhVienDao(getActivity());
                ArrayList<ThanhVienMode> list1 = tv.SellecAllTV();
                AdapterSpinnerMaTV adapter = new AdapterSpinnerMaTV(getActivity(), list1);
                ThanhVien.setAdapter(adapter);
                ThuThuDao tt = new ThuThuDao(getActivity());
                ArrayList<ThuThuMode> list2 = tt.SellecAllTT();
                AdapterSpinnerMaTT adaptertt = new AdapterSpinnerMaTT(getActivity(), list2);
                ThuThu.setAdapter(adaptertt);
                SachDao sachDao = new SachDao(getActivity());
                ArrayList<SachMode> list3 = sachDao.SellecAll();
                AdapterSpinnerMaSach spinnerMaSach = new AdapterSpinnerMaSach(getActivity(), list3);
                Sach.setAdapter(spinnerMaSach);

//        Gia.setText(String.valueOf(list.get()));
//        EditText txtMa=view.findViewById(R.id.txtmadvUpdate);
//        EditText edtNoiDung = view.findViewById(R.id.txtNoidungUpdate);
//
//        EditText edtThanhTien = view.findViewById(R.id.txtThanhTienUpdate);
                Button btnSave = view.findViewById(R.id.btaddPM);
                Button bthuy = view.findViewById(R.id.btncanel);
                int[] maTV = {0};
                // lấy string từ spinner Thành viên
                ThanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maTV[0] = ((ThanhVienMode) ThanhVien.getItemAtPosition(position)).getMaTV();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                // xử lý khi lấy từ spinner Thu thu
                String mathu[] = new String[1];
                ThuThu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mathu[0] = ((ThuThuMode) ThuThu.getItemAtPosition(position)).getMaTT();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                // xử lý khi chon spinner Sach
                int[] maS = {0};
                Sach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maS[0] = ((SachMode) Sach.getItemAtPosition(position)).getMaSach();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                // xử lý khi chọn hủy
                // xửa lý khi chọn add
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String ngay = String.valueOf(java.time.LocalDate.now());
                            PhieuMuonMode pm = new PhieuMuonMode(maTV[0], mathu[0], maS[0], ngay, 0);
                            if (pMdao.addPM(pm)) {
                                list.clear();
                                list.addAll(pMdao.getAllPhieuMuonModes());
                                adapterPM.notifyDataSetChanged();
                                Toast.makeText(getActivity(), "Thêm thành công ", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        return view;
    }
}