package com.example.ph32302_mobile.Dao;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ph32302_mobile.DataBase.DBhelper;
import com.example.ph32302_mobile.Mode.ThuThuMode;

import java.util.ArrayList;

public class ThuThuDao {
    private final DBhelper dBhelper;

    public ThuThuDao(Context context) {
        dBhelper = new  DBhelper(context);
    }
    // lấy toàn bộ dữ liệu từ database
    public ArrayList<ThuThuMode> SellecAllTT(){
        ArrayList<ThuThuMode> list= new ArrayList<>();
        SQLiteDatabase db= dBhelper.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("Select * from ThuThu",null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    ThuThuMode tv= new ThuThuMode();
                     tv.setMaTT(cursor.getString(0));
                    tv.setHoTenTT(cursor.getString(1));
                    tv.setMatKhau(cursor.getString(2));
                    list.add(tv);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            Log.i(TAG,"lỗi",e);
        }
        return list;
    }
    public boolean checkTaiKoan(String user){
        SQLiteDatabase db=dBhelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from ThuThu where MaThuThu=? ",new String[]{user});
        int row=cursor.getCount();
        return (row>0);
    }
   @SuppressLint("Range")
   public int quyen(String use){
       SQLiteDatabase db=dBhelper.getWritableDatabase();
       Cursor cursor=db.rawQuery("select Quyen from ThuThu where MaThuThu=? ",new String[]{use});
       int quyen=0;
       if (cursor != null) {
           if (cursor.moveToFirst()) {
              quyen = cursor.getInt(cursor.getColumnIndex("Quyen"));
           }
           cursor.close();
       }
       return quyen;
   }
    public boolean checkdangnhap(String user,String password){
        SQLiteDatabase db=dBhelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from ThuThu where  MaThuThu=? and MatKhau=?",new String[]{user,password});
        int row=cursor.getCount();
        return (row>0);
    }

    public boolean addThuThu(ThuThuMode sp){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=dBhelper.getWritableDatabase();
        values.put("MaThuThu",sp.getMaTT());
        values.put("hoVaTen",sp.getHoTenTT());
        values.put("MatKhau",sp.getMatKhau());
        values.put("Quyen",sp.getQuyen());
        long row=db.insert("ThuThu",null,values);
        return (row>0);
    }
    public boolean updatelistTT(ThuThuMode sp){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=dBhelper.getWritableDatabase();
        values.put("MaThuThu",sp.getMaTT());
        values.put("MatKhau",sp.getMatKhau());
        values.put("Quyen",sp.getQuyen());
        long row=db.update("ThuThu",values,"MaThuThu=?",new String[]{String.valueOf(sp.getMaTT())});
        return (row>0);
    }

}
