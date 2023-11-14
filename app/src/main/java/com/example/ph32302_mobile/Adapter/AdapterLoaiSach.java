package com.example.ph32302_mobile.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ph32302_mobile.Dao.TheLoaiDao;
import com.example.ph32302_mobile.Mode.TheLoaiMode;
import com.example.ph32302_mobile.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

@SuppressLint({"MissingInflatedId", "LocalSuppress"})
public class AdapterLoaiSach extends RecyclerView.Adapter<AdapterLoaiSach.viewholder> {
    private final Context context;
    private final ArrayList<TheLoaiMode> list;
    TheLoaiDao mhDao;
    TheLoaiDao tlDao;

    public AdapterLoaiSach(Context context, ArrayList<TheLoaiMode> list) {
        this.context = context;
        this.list = list;
        mhDao=new TheLoaiDao(context);
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loaisach, null);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.TvTenSach.setText(String.valueOf("Mã TL: "+list.get(position).getMaTheLoai()));
        holder.TvSoLuong.setText( "Tên TL: "+list.get(position).getTenTheLoai());

        TheLoaiMode dv = list.get(position);
        // xủ lý khi cập nhật
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openDialogUpdate(dv);
                return  true;
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo!");
                builder.setIcon(R.drawable.baseline_warning_24);
                builder.setMessage("Bạn có muốn xoá?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (mhDao.deleteTheLoai(dv.getMaTheLoai())) {
                            list.clear();
                            list.addAll(mhDao.SellecAllTheLoai());
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


//        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openDialogUpdate(dv);
//            }
//        });
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

           TvTenSach=itemView.findViewById(R.id.tvMaLoaiSach);
           TvSoLuong=itemView.findViewById(R.id.tvTenLoai);
           delete=itemView.findViewById(R.id.btnXoaTheLoai);
        }
    }

    public void openDialogUpdate (TheLoaiMode dv) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.update_the_loai, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        TextView matl=view.findViewById(R.id.TvMaTL);
        TextInputEditText txten=view.findViewById(R.id.txtTenTLUP);
        Button update,canel;
        update=view.findViewById(R.id.btnUpdateTLUP);
        canel=view.findViewById(R.id.btncanelTLUP);

        matl.setText("Mã: "+String.valueOf(dv.getMaTheLoai()));
        txten.setText(dv.getTenTheLoai());
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
                if(ten.isEmpty()){
                    Toast.makeText(context, "Không để trống Tên Thể Loại", Toast.LENGTH_SHORT).show();
                } else { int mtl=dv.getMaTheLoai();
                    try {
                         dv.setMaTheLoai(mtl);
                         dv.setTenTheLoai(ten);

                        if (mhDao.updatelistTL(dv)) {
                            list.clear();
                            list.addAll(mhDao.SellecAllTheLoai());
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

