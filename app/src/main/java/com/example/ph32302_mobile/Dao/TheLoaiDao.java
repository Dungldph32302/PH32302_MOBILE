package com.example.ph32302_mobile.Dao;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ph32302_mobile.DataBase.DBhelper;
import com.example.ph32302_mobile.Mode.TheLoaiMode;

import java.util.ArrayList;

public class TheLoaiDao {
    private final DBhelper dBhelper;

    public TheLoaiDao(Context context) {
        dBhelper = new  DBhelper(context);
    }



    // lấy toàn bộ dữ liệu từ database
    public ArrayList<TheLoaiMode> SellecAllTheLoai(){
        ArrayList<TheLoaiMode> list= new ArrayList<>();
        SQLiteDatabase db= dBhelper.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("Select * from TheLoai",null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    TheLoaiMode sach= new TheLoaiMode();
                    sach.setMaTheLoai(cursor.getInt(0));
                    sach.setTenTheLoai(cursor.getString(1));
                    list.add(sach);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            Log.i(TAG,"lỗi",e);
        }
        return list;
    }
    public int getTotalSachByTheLoai(int maTheLoai) {
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        int totalSach = 0;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT SUM(SoLuongTon) FROM Sach WHERE MaTheLoai = ?", new String[]{String.valueOf(maTheLoai)});
            if (cursor.moveToFirst()) {
                totalSach = cursor.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý ngoại lệ theo ý muốn, ví dụ log hoặc hiển thị thông báo lỗi
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return totalSach;
    }
    public boolean deleteTheLoai(int id){
        SQLiteDatabase db= dBhelper.getWritableDatabase();
        long row=db.delete("TheLoai","MaTheLoai=?",new String[]{String.valueOf(id)});
        return  (row>0);
    }
    public boolean updatelistTL(TheLoaiMode sp){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=dBhelper.getWritableDatabase();
        values.put("MaTheLoai",sp.getMaTheLoai());
        values.put("TenTheLoai",sp.getTenTheLoai());
        long row=db.update("TheLoai",values,"MaTheLoai=?",new String[]{String.valueOf(sp.getMaTheLoai())});
        return (row>0);
    }
    public boolean addTheLoai(TheLoaiMode sp){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=dBhelper.getWritableDatabase();
        values.put("TenTheLoai",sp.getTenTheLoai());
        long row=db.insert("TheLoai",null,values);
        return (row>0);
    }
}
