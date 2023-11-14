package com.example.ph32302_mobile.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ph32302_mobile.Adapter.AdapterSach;
import com.example.ph32302_mobile.Dao.SachDao;
import com.example.ph32302_mobile.MainActivity;
import com.example.ph32302_mobile.Mode.LoaiSachMode;
import com.example.ph32302_mobile.Mode.SachMode;
import com.example.ph32302_mobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentTop10Sach extends Fragment {
    @Override
    public void onResume() {
        super.onResume();
        // Đặt tiêu đề trên Toolbar khi Fragment này được hiển thị
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setActionBarTitle("Top 10 sách ");
        }
    }

// ... Các phương thức khác của Fragment

    public FragmentTop10Sach() {
        // Required empty public constructor
    }
    AdapterSach adapter;
    SachDao mhDao;

    RecyclerView recyclerView;
    ArrayList<SachMode> list;
    FloatingActionButton btadd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top10_sach, container, false);
        recyclerView = view.findViewById(R.id.rlvListSachTop10);
        TextView sol=view.findViewById(R.id.TvSL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mhDao = new SachDao(getActivity());
        list = mhDao.getTop10MostBorrowedBooks();
        adapter = new AdapterSach(getActivity(),list);
        recyclerView.setAdapter(adapter);
        return view;
    }
}