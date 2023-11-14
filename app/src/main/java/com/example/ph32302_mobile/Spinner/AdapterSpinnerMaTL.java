package com.example.ph32302_mobile.Spinner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ph32302_mobile.Mode.TheLoaiMode;
import com.example.ph32302_mobile.R;

import java.util.ArrayList;

public class AdapterSpinnerMaTL extends BaseAdapter {
    private Context context;
    private ArrayList<TheLoaiMode> list;

    public AdapterSpinnerMaTL(Context context, ArrayList<TheLoaiMode> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // khởi tạo và kiên kết layout
        LayoutInflater inflater= ((Activity)context).getLayoutInflater();// sinh layout
        convertView= inflater.inflate(R.layout.spinner_ma_tl,parent,false); // liên kết
        // ánh xạ thành phần từng thành phần trên  layout
        TextView txtName=convertView.findViewById(R.id.TvHienThiMaTL);


        // điền dữ liệu
       txtName.setText(list.get(position).getTenTheLoai());
        return convertView;
    }
}
