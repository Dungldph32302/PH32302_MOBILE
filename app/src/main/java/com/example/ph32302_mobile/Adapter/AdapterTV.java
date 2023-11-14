package com.example.ph32302_mobile.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ph32302_mobile.Dao.PMdao;
import com.example.ph32302_mobile.Dao.ThanhVienDao;
import com.example.ph32302_mobile.Mode.PhieuMuonMode;
import com.example.ph32302_mobile.Mode.ThanhVienMode;
import com.example.ph32302_mobile.Mode.TheLoaiMode;
import com.example.ph32302_mobile.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterTV extends BaseAdapter implements Filterable
{

    Context context;
    ArrayList<ThanhVienMode> list ;
    ThanhVienDao pMdao;

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    public AdapterTV(Context context, ArrayList<ThanhVienMode> list) {
        this.context = context;
        this.list = list;
        pMdao=new ThanhVienDao(context);
    }
    public AdapterTV() {
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
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        // khởi tạo layout
                ThanhVienMode thanhVienMode=list.get(position);
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        convertView=inflater.inflate(R.layout.itemthanhvien,parent,false);// liên kết layout
        // ánh xạ từng thành phần
        TextView TvMaTV, TvTenTV,TvDate;
           ImageView delete=convertView.findViewById(R.id.deleteTV);
                TvMaTV=convertView.findViewById(R.id.tvmaTV);
                TvTenTV=convertView.findViewById(R.id.tvnametv);
                TvDate =convertView.findViewById(R.id.tvdatetv);

        // set dữ liệu
        TvMaTV.setText("Mã: "+String.valueOf(list.get(position).getMaTV()));
        TvTenTV.setText(list.get(position).getNameTV());
        TvDate.setText("Date : "+list.get(position).getNamSinhTV());


       //  xử lý khi update
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openDialogUpdateTV(thanhVienMode);
                return true;
            }
        });
                // sử lý khi click vào xóa
        ThanhVienMode pm=list.get(position);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Cảnh báo!");
                        builder.setMessage("Bạn có muốn xoá?");
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if (pMdao.deleteThanhVien(pm.getMaTV())) {
                                    list.clear();
                                    list.addAll(pMdao.SellecAllTV());
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
                        //
                    }
                });
        return convertView;
    }
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    public void openDialogUpdateTV (ThanhVienMode dv) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.update_tv, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
          TextView matl=view.findViewById(R.id.TvMaTV);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextInputEditText txten=view.findViewById(R.id.txtTenTVUpdate);
        TextInputEditText date=view.findViewById(R.id.txtDateTvUpdate);
        Button update,canel;
        update=view.findViewById(R.id.btnUpdateTV);
        canel=view.findViewById(R.id.btncanelTV);

        matl.setText("Mã: "+String.valueOf(dv.getMaTV()));
        txten.setText(dv.getNameTV());
        date.setText(dv.getNamSinhTV());
        // xử lý khi ấn hủy
        canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma=matl.getText().toString().trim();
                String ten = txten.getText().toString().trim();
                String ns=date.getText().toString().trim();

                if(ten.isEmpty()){
                    Toast.makeText(context, "Không để trống Tên Thành Viên", Toast.LENGTH_SHORT).show();
                } else if (date.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Không để trống Năm Sinh", Toast.LENGTH_SHORT).show();
                } else if (!isValidDate(ns)) {
                    Toast.makeText(context, "Nhập ngày theo đúng định dạng", Toast.LENGTH_SHORT).show();
                } else { int mtl=dv.getMaTV();
                    try {
                        dv.setMaTV(mtl);
                        dv.setNameTV(ten);
                        dv.setNamSinhTV(ns);
                        if (pMdao.updatelistTV(dv)) {
                            list.clear();
                            list.addAll(pMdao.SellecAllTV());
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
    public static boolean isValidDate(String inputDate) {
        String dateFormat = "yyyy-MM-dd"; // Định dạng ngày

        // Tạo một đối tượng SimpleDateFormat với định dạng ngày
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false); // Tắt chế độ linh hoạt

        try {
            // Chuyển chuỗi ngày thành đối tượng Date
            sdf.parse(inputDate);
            return true; // Nếu không có lỗi, chuỗi hợp lệ
        } catch (ParseException e) {
            // Nếu có lỗi ParseException, chuỗi không hợp lệ
            return false;
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
    }
}
