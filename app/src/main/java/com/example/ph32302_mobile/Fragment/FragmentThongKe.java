package com.example.ph32302_mobile.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ph32302_mobile.Adapter.AdapterTV;
import com.example.ph32302_mobile.Dao.PMdao;
import com.example.ph32302_mobile.MainActivity;
import com.example.ph32302_mobile.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FragmentThongKe extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
        // Đặt tiêu đề trên Toolbar khi Fragment này được hiển thị
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setActionBarTitle("Thống kê doanh thu");
        }
    }

// ... Các phương thức khác của Fragment

    public FragmentThongKe() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_thong_ke, container,false);
        Button btn=view.findViewById(R.id.btnDoanhThu);
        EditText txtNgaybd,txtngaykt;
        TextView ht=view.findViewById(R.id.TvHienthidoanhthu);
        txtNgaybd=view.findViewById(R.id.TXTNgaybd);
        txtngaykt=view.findViewById(R.id.TXTngayKT);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bd=txtNgaybd.getText().toString().trim();
                String kt=txtngaykt.getText().toString();
                AdapterTV tv= new AdapterTV();

                if(tv.isValidDate(bd)&&tv.isValidDate(kt)){
                    PMdao pm= new PMdao(getActivity());
                    int tien=  pm.ThongKeDoanhThu(bd,kt);
                    ht.setText("Doanh Thu: "+Integer.toString(tien)+"VND");
                    Toast.makeText(getActivity(), tien+"VND", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "Vui lòng nhập ngày đúng định dạng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

}