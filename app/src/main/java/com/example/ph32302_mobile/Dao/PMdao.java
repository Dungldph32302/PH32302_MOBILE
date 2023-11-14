package com.example.ph32302_mobile.Dao;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ph32302_mobile.DataBase.DBhelper;
import com.example.ph32302_mobile.Mode.PhieuMuonMode;

import java.util.ArrayList;

public class PMdao {
    private final Context context;
    private final DBhelper dBhelper;

    public PMdao(Context context) {
        this.context = context;
        dBhelper = new DBhelper(context);
    }
    // lấy toàn bộ dữ liệu từ bảng pm
//    public ArrayList<PhieuMuonMode> SellecAllPM(){
//        ArrayList<PhieuMuonMode>list= new ArrayList<>();
//        SQLiteDatabase db=dBhelper.getReadableDatabase();
//        try {
//            Cursor cursor=db.rawQuery("select * from PM",null);
//            if(cursor.getCount()>0){
//                cursor.moveToFirst();
//                    while (!cursor.isAfterLast()){
//                        PhieuMuonMode pm=new PhieuMuonMode();
//                        pm.setMaPM(cursor.getInt(0));
//                        pm.setMaTV(cursor.getInt(1));
//                        pm.setMaTT(cursor.getInt(2));
//                        pm.setMaSach(cursor.getInt(3));
//                        pm.setNgayMuon(cursor.getString(4));
//                        pm.setTraSach(cursor.getInt(5));
//                        pm.setGiaThue(cursor.getInt(6));
////                        MaPM integer primary key ,\n" +
////                        "\t MaThanhVien integer,\n" +
////                                "\t MaThuThu integer,\n" +
////                                "\t MaSach integer,\n" +
////                                "\t NgayMuon text,\n" +
////                                "\t TraSach integer,\n" +
////                                "\t GiaThue integer,\n" +
//                        list.add(pm);
//                        cursor.moveToNext();
//                    }
//            }
//        }catch (Exception e){
//            Log.i(TAG,"lỗi",e);
//        }
//        return list;
//    }
    @SuppressLint("Range")
    public ArrayList<PhieuMuonMode> getAllPhieuMuonModes() {
        ArrayList<PhieuMuonMode> list = new ArrayList<>();
        SQLiteDatabase db = dBhelper.getReadableDatabase();

        String query = "SELECT PM.*, Sach.TenSach, Sach.GiaThue, ThanhVien.HoVaTen " +
                "FROM PM " +
                "INNER JOIN Sach ON PM.MaSach = Sach.MaSach " +
                "INNER JOIN ThanhVien ON PM.MaThanhVien = ThanhVien.MaThanhVien";

        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    PhieuMuonMode pm = new PhieuMuonMode();
                    pm.setMaPM(cursor.getInt(cursor.getColumnIndex("MaPM")));
                    pm.setMaTV(cursor.getInt(cursor.getColumnIndex("MaThanhVien")));
                    pm.setMaThuThu(cursor.getString(cursor.getColumnIndex("MaThuThu")));
                    pm.setMaSach(cursor.getInt(cursor.getColumnIndex("MaSach")));
                    pm.setNgayMuon(cursor.getString(cursor.getColumnIndex("NgayMuon")));
                    pm.setTraSach(cursor.getInt(cursor.getColumnIndex("TraSach")));
                    pm.setGiaThue(cursor.getInt(cursor.getColumnIndex("GiaThue")));
                    pm.setTenSach(cursor.getString(cursor.getColumnIndex("TenSach")));
                    pm.setTenTV(cursor.getString(cursor.getColumnIndex("HoVaTen")));
                    list.add(pm);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        return list;
    }

    public int getSoLuongPhieuMuon() {
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        int soLuong = 0;

        try {
            String query = "SELECT COUNT(*) FROM PM";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                soLuong = cursor.getInt(0); // Lấy giá trị đếm từ cột đầu tiên (cột 0)
            }

            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi lấy số lượng phiếu mượn", e);
        } finally {
            db.close();
        }

        return soLuong;
    }

    @SuppressLint("Range")
    public int getTongTienThue() {
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        int tongTien = 0;

        try {
            String query = "SELECT SUM(Sach.GiaThue) AS TongTienThue " +
                    "FROM PM " +
                    "INNER JOIN Sach ON PM.MaSach = Sach.MaSach";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                tongTien = cursor.getInt(cursor.getColumnIndex("TongTienThue"));
            }

            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi lấy tổng tiền thuê", e);
        } finally {
            db.close();
        }

        return tongTien;
    }

    public boolean addPM(PhieuMuonMode sp){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=dBhelper.getWritableDatabase();
        values.put("MaThanhVien",sp.getMaTV());
        values.put("MaThuThu",sp.getMaThuThu());
        values.put("MaSach",sp.getMaSach());
        values.put("NgayMuon",sp.getNgayMuon());
        values.put("TraSach",sp.getTraSach());
        long row=db.insert("PM",null,values);
        return (row>0);
    }
    public boolean updatePM(PhieuMuonMode sp){
        ContentValues values= new ContentValues();
        SQLiteDatabase db=dBhelper.getWritableDatabase();
        values.put("MaPM",sp.getMaPM());
        values.put("MaThanhVien",sp.getMaTV());
        values.put("MaThuThu",sp.getMaThuThu());
        values.put("MaSach",sp.getMaSach());
        values.put("NgayMuon",sp.getNgayMuon());
        values.put("TraSach",sp.getTraSach());
        long row=db.update("PM",values,"MaPM=?",new String[]{String.valueOf(sp.getMaPM())});
        return  (row>0);
    }

    public boolean deletePM(int masp){
        SQLiteDatabase db=dBhelper.getWritableDatabase();
        long row=db.delete("PM","MaPM=?",new String[]{String.valueOf(masp)});
        return (row>0);
    }
    @SuppressLint("Range")
    public int ThongKeDoanhThu(String fromDateStr, String toDateStr) {
        int totalGiaThue = 0;
        SQLiteDatabase db = dBhelper.getReadableDatabase();

        String query = "SELECT SUM(Sach.GiaThue) AS TotalGiaThue " +
                "FROM PM " +
                "INNER JOIN Sach ON PM.MaSach = Sach.MaSach " +
                "WHERE PM.NgayMuon BETWEEN ? AND ?";

        Cursor cursor = db.rawQuery(query, new String[]{fromDateStr, toDateStr});
        try {
            if (cursor.moveToFirst()) {
                totalGiaThue = cursor.getInt(cursor.getColumnIndex("TotalGiaThue"));
            }
        } finally {
            cursor.close();
        }

        return totalGiaThue;
    }

}
