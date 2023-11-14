package com.example.ph32302_mobile.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ph32302_mobile.Dao.SachDao;
import com.example.ph32302_mobile.Dao.TheLoaiDao;
import com.example.ph32302_mobile.Mode.SachMode;
import com.example.ph32302_mobile.Mode.ThanhVienMode;
import com.example.ph32302_mobile.Mode.TheLoaiMode;
import com.example.ph32302_mobile.R;
import com.example.ph32302_mobile.Spinner.AdapterSpinnerMaTL;
import com.google.android.material.textfield.TextInputEditText;


import java.util.ArrayList;

@SuppressLint({"MissingInflatedId", "LocalSuppress"})
public class AdapterSach extends RecyclerView.Adapter<AdapterSach.viewholder> {
    private final Context context;
    private final ArrayList<SachMode> list;
    SachDao mhDao;
    TheLoaiDao tlDao;

    public AdapterSach(Context context, ArrayList<SachMode> list) {
        this.context = context;
        this.list = list;
        mhDao=new SachDao(context);
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sach, null);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.TvTenSach.setText(String.valueOf(list.get(position).getTenSach()));
        holder.TvSoLuong.setText( list.get(position).getGiaThue()+" VND");

        SachMode dv = list.get(position);
// xử lý khi ấn và giữ item
         holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
             @Override
             public boolean onLongClick(View v) {
                 openDialogUpdate(dv);
                 return true;
             }
         });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo!");
                builder.setMessage("Bạn có muốn xoá?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (mhDao.deleteSach(dv.getMaSach())) {
                            list.clear();
                            list.addAll(mhDao.SellecAll());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                            // Sau khi xóa thành công sản phẩm
                          //  NotificationHelper.showNotification(context.getApplicationContext(), "Bạn đã xóa một sản phẩm.");

                        } else {
                            Toast.makeText(context, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Toast.makeText(context, "Huỷ xoá", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder{

        TextView TvTenSach, TvSoLuong;
       ImageView delete;

        public viewholder(@NonNull View itemView) {
            super(itemView);

           TvTenSach=itemView.findViewById(R.id.tvTenSach);
           TvSoLuong=itemView.findViewById(R.id.tvSoLuong);
           delete=itemView.findViewById(R.id.deleteSach);
        }
    }

    public void openDialogUpdate (SachMode dv) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.update_sach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        TextView tvMa=view.findViewById(R.id.TvMaSach);
        Spinner spinner=view.findViewById(R.id.spinnerUpdateSachMaTL);
        TextInputEditText tenSach=view.findViewById(R.id.txtTenSachUp);
        TextInputEditText Gia=view.findViewById(R.id.txtGiaSach);
        // set nội dung
        tvMa.setText(String.valueOf(dv.getMaSach()));
        tenSach.setText(dv.getTenSach());
        String giathe= String.valueOf(dv.getGiaThue());
        Gia.setText(giathe);
        // set spinner
        TheLoaiDao theLoaiDao = new TheLoaiDao(context);
        ArrayList<TheLoaiMode>list1=theLoaiDao.SellecAllTheLoai();
        AdapterSpinnerMaTL adapter = new AdapterSpinnerMaTL(context, list1);
        spinner.setAdapter(adapter);
//        Gia.setText(String.valueOf(list.get()));
//        EditText txtMa=view.findViewById(R.id.txtmadvUpdate);
//        EditText edtNoiDung = view.findViewById(R.id.txtNoidungUpdate);
//
//        EditText edtThanhTien = view.findViewById(R.id.txtThanhTienUpdate);
        Button btnSave = view.findViewById(R.id.btnUpdateSach);
        Button bthuy= view.findViewById(R.id.btncanel);
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
        // xử lý khi chọn hủy
        bthuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // xửa lý khi chọn add
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ma = tvMa.getText().toString().trim();
                int matl=selectedItemSpinner[0];
//                int gia = Integer.parseInt(Gia.getText().toString());
                String giaText = Gia.getText().toString();
                int gia=0;
                // int sotiet=Integer.valueOf(edtThanhTien.getText().toString());
               String TenSach = tenSach.getText().toString().trim();
                if(TenSach.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập tên sách", Toast.LENGTH_SHORT).show();
                } else if(matl<0){
                    Toast.makeText(context, "Vui lòng chọn thể loại ", Toast.LENGTH_SHORT).show();
                }else if(gia<0){
                    Toast.makeText(context, "Vui lòng nhập giá", Toast.LENGTH_SHORT).show();
                } else if (giaText.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập  giá " +
                            "", Toast.LENGTH_SHORT).show();
                } else {
                           gia=Integer.parseInt(giaText);
                    try {
                        dv.setMaSach(Integer.parseInt(tvMa.getText().toString()));
                        dv.setMaTheLoai(matl);
                        dv.setTenSach(TenSach);
                        dv.setGiaThue(gia);
                        if (mhDao.updatelistSach(dv)) {
                            list.clear();
                            list.addAll(mhDao.SellecAll());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });

    }

}

