package com.example.ph32302_mobile.Dao;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ph32302_mobile.DataBase.DBhelper;
import com.example.ph32302_mobile.Mode.SachMode;
import com.example.ph32302_mobile.Mode.ThanhVienMode;

import java.util.ArrayList;

public class ThanhVienDao {
    private final DBhelper dBhelper;

    public ThanhVienDao(Context context) {
        dBhelper = new  DBhelper(context);
    }
    // lấy toàn bộ dữ liệu từ database
    public ArrayList<ThanhVienMode> SellecAllTV(){
        ArrayList<ThanhVienMode> list= new ArrayList<>();
        SQLiteDatabase db= dBhelper.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("Select * from ThanhVien",null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    ThanhVienMode tv= new ThanhVienMode();
                     tv.setMaTV(cursor.getInt(0));
                    tv.setNameTV(cursor.getString(1));
                    tv.setNamSinhTV(cursor.getString(2));
                    list.add(tv);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            Log.i(TAG,"lỗi",e);
        }
        return list;
    }
    public ArrayList<SachMode> getALL(){
        String sql="select * from ThanhVien";
        return getALL();
    }
    public int getSoLuongTV() {
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        int soLuong = 0;

        try {
            String query = "SELECT COUNT(*) FROM ThanhVien";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                soLuong = cursor.getInt(0); // Lấy giá trị đếm từ cột đầu tiên (cột 0)
            }

            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi lấy số lượng Thành viên", e);
        } finally {
            db.close();
        }

        return soLuong;
    }
    public boolean deleteThanhVien(int id){
        SQLiteDatabase db= dBhelper.getWritableDatabase();
        long row=db.delete("ThanhVien","MaThanhVien=?",new String[]{String.valueOf(id)});
        return  (row>0);
    }
    public boolean updatelistTV(ThanhVienMode sp){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=dBhelper.getWritableDatabase();
        values.put("MaThanhVien",sp.getMaTV());
        values.put("HoVaTen",sp.getNameTV());
        values.put("NamSinh",sp.getNamSinhTV());
        long row=db.update("ThanhVien",values,"MaThanhVien=?",new String[]{String.valueOf(sp.getMaTV())});
        return (row>0);
    }
    public boolean addlistTV(ThanhVienMode sp){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=dBhelper.getWritableDatabase();
        values.put("HoVaTen",sp.getNameTV());
        values.put("NamSinh",sp.getNamSinhTV());
        long row=db.insert("ThanhVien",null,values);
        return (row>0);
    }

}
