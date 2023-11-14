package com.example.ph32302_mobile.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.ph32302_mobile.Dao.PMdao;
import com.example.ph32302_mobile.Dao.SachDao;
import com.example.ph32302_mobile.Dao.ThanhVienDao;
import com.example.ph32302_mobile.Dao.ThuThuDao;
import com.example.ph32302_mobile.Mode.PhieuMuonMode;
import com.example.ph32302_mobile.Mode.SachMode;
import com.example.ph32302_mobile.Mode.ThanhVienMode;
import com.example.ph32302_mobile.Mode.ThuThuMode;
import com.example.ph32302_mobile.R;
import com.example.ph32302_mobile.Spinner.AdapterSpinnerMaSach;
import com.example.ph32302_mobile.Spinner.AdapterSpinnerMaTT;
import com.example.ph32302_mobile.Spinner.AdapterSpinnerMaTV;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class AdapterPhieuMuon extends BaseAdapter implements Filterable
{
    Context context;
    ArrayList<PhieuMuonMode> list ;
    PMdao pMdao;

    public AdapterPhieuMuon(Context context, ArrayList<PhieuMuonMode> list) {
        this.context = context;
        this.list = list;
        pMdao=new PMdao(context);
    }
    public AdapterPhieuMuon() {
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
        // khởi tạo layout
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        convertView=inflater.inflate(R.layout.item_phieu_muon,parent,false);// liên kết layout
        // ánh xạ từng thành phần
        TextView tvMaPM, tvMaThanhVien, tvMaThuThu,  tvMaSach ,tvNgayMuon ,tvTraSach  ,tvGiaThue ;
        ImageView delete=convertView.findViewById(R.id.imgDeletePM);
                tvMaPM=convertView.findViewById(R.id.tvMaPhieuMuon);
                tvMaThanhVien=convertView.findViewById(R.id.tvMaThanhVien);
                tvMaSach =convertView.findViewById(R.id.tvTenSach);
                tvNgayMuon =convertView.findViewById(R.id.tvNgayMuon);
                tvTraSach=convertView.findViewById(R.id.tvTrangThai);
                tvGiaThue =convertView.findViewById(R.id.tvTienThue);
        AppCompatButton btntr=convertView.findViewById(R.id.btntraSach);
        PhieuMuonMode pm1=list.get(position);
        // set dữ liệu
                tvMaPM.setText(String.valueOf(list.get(position).getMaPM()));
                tvMaThanhVien.setText(String.valueOf(list.get(position).getTenTV()));
                tvMaSach.setText(String.valueOf(list.get(position).getTenSach()));
                tvNgayMuon.setText("Ngày mượn: "+list.get(position).getNgayMuon());
                tvGiaThue.setText(String.valueOf(list.get(position).getGiaThue()+" VND"));
              //  tvTraSach.setText(String.valueOf(list.get(position).getTraSach()));
                if(list.get(position).getTraSach()==1){
                    tvTraSach.setText("Đã trả");
                    tvTraSach.setTextColor(Color.parseColor("#0000FF"));
                    btntr.setVisibility(convertView.GONE); // ẩn btn
                }else{
                    tvTraSach.setText("Chưa trả");
                    tvTraSach.setTextColor(Color.parseColor("#FF0000"));
                }
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                    View view = inflater.inflate(R.layout.update_pm, null);
                    builder.setView(view);
                    Dialog dialog = builder.create();
                    dialog.show();
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvMa=view.findViewById(R.id.tvPMUpdate);
                    Spinner ThanhVien = view.findViewById(R.id.spinnerPMThanhVien);
                    Spinner ThuThu = view.findViewById(R.id.spinneraddPMThuThu);
                    Spinner Sach = view.findViewById(R.id.spinneraddPMSach);
                    CheckBox chk=view.findViewById(R.id.chkTraSach);
                    int tras= list.get(position).getTraSach();
                    if(tras==1){
                        chk.setChecked(true);
                    }
                    // set nội dung
                // Trước khi gọi findViewById, tìm vị trí giá trị mặc định trong danh sách Spinner
// Tìm Spinner ThanhVien và set giá trị mặc định
               // Đảm bảo adapter đã được thiết lập trước đó


                // set spinner
                    tvMa.setText("Mã Phiếu: "+list.get(position).getMaPM());

                    ThanhVienDao tv = new ThanhVienDao(context);
                    ArrayList<ThanhVienMode> list1 = tv.SellecAllTV();
                    AdapterSpinnerMaTV adapter1 = new AdapterSpinnerMaTV(context, list1);
                    ThanhVien.setAdapter(adapter1);
                    int ma=list.get(position).getMaTV();
                    ThanhVien.setSelection(ma-1);

                    ThuThuDao tt = new ThuThuDao(context);
                    ArrayList<ThuThuMode> list2 = tt.SellecAllTT();
                    AdapterSpinnerMaTT adaptertt = new AdapterSpinnerMaTT(context, list2);
                    ThuThu.setAdapter(adaptertt);

                    SachDao sachDao = new SachDao(context);
                    ArrayList<SachMode> list3 = sachDao.SellecAll();
                    AdapterSpinnerMaSach spinnerMaSach = new AdapterSpinnerMaSach(context, list3);
                    Sach.setAdapter(spinnerMaSach);
                    int mas=list.get(position).getMaSach();
                    Sach.setSelection(mas-1);

//        Gia.setText(String.valueOf(list.get()));
//        EditText txtMa=view.findViewById(R.id.txtmadvUpdate);
//        EditText edtNoiDung = view.findViewById(R.id.txtNoidungUpdate);
//
//        EditText edtThanhTien = view.findViewById(R.id.txtThanhTienUpdate);
                    Button btnSave = view.findViewById(R.id.btaddPM);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button bthuy = view.findViewById(R.id.btncanel);
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
                                int traSach=0;
                                if(chk.isChecked()){
                                    traSach=1;
                                }
                                String ngay = String.valueOf(java.time.LocalDate.now());
                                pm1.setMaPM(list.get(position).getMaPM());
                                pm1.setMaThuThu(mathu[0]);
                                pm1.setMaSach( maS[0]);
                                pm1.setMaTV(maTV[0]);
                                pm1.setTraSach(traSach);
                                pm1.setNgayMuon(list.get(position).getNgayMuon());
                                if (pMdao.updatePM(pm1)) {
                                    list.clear();
                                    list.addAll(pMdao.getAllPhieuMuonModes());
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Cập nhật thành công Thêm thành công ", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                return true;
            }
        });
        // sử lý khi ấn vào nut trả sách
        btntr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhieuMuonMode pm=list.get(position);
                pm.setMaPM(list.get(position).getMaPM());
                pm.setMaThuThu(list.get(position).getMaThuThu());
                pm.setMaTV(list.get(position).getMaTV());
                pm.setMaSach(list.get(position).getMaSach());
                pm.setNgayMuon(list.get(position).getNgayMuon());
                pm.setTraSach(1);
                if(pMdao.updatePM(pm)){
                    list.clear();
                    list.addAll(pMdao.getAllPhieuMuonModes());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(v, "Hoàn tác ", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                   //  Hoặc nếu bạn muốn thêm tùy chọn "Hoàn tác"
                     snackbar.setAction("Hoàn tác", new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             pm.setMaPM(list.get(position).getMaPM());
                             pm.setMaThuThu(list.get(position).getMaThuThu());
                             pm.setMaTV(list.get(position).getMaTV());
                             pm.setMaSach(list.get(position).getMaSach());
                             pm.setNgayMuon(list.get(position).getNgayMuon());
                             pm.setTraSach(0);
                             pMdao.updatePM(pm);
                             list.clear();
                             list.addAll(pMdao.getAllPhieuMuonModes());
                             notifyDataSetChanged();
                             notifyDataSetChanged();
                         }
                     });
                     snackbar.show();

                }else{
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
//                MaPM integer primary key ,\n" +
//                "\t MaThanhVien integer references ThanhVien(MaThanhVien),\n" +
//                        "\t MaThuThu integer references ThuThu(MaThuThu),\n" +
//                        "\t MaSach integer  references Sach(MaSach),\n" +
//                        "\t NgayMuon text,\n" +
//                        "\t TraSach integer\n"
            }
        });
                // sử lý khi click vào xóa
        PhieuMuonMode pm=list.get(position);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Cảnh báo!");
                        builder.setMessage("Bạn có muốn xoá?");
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if (pMdao.deletePM(pm.getMaPM())) {
                                    list.clear();
                                    list.addAll(pMdao.getAllPhieuMuonModes());
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
