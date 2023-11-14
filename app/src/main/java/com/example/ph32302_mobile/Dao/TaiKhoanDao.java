package com.example.ph32302_mobile.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ph32302_mobile.DataBase.DBhelper;
import com.example.ph32302_mobile.Mode.TaiKhoan;

public class TaiKhoanDao {
    private final DBhelper dBhelper;

    public TaiKhoanDao(Context context) {
        dBhelper=new DBhelper(context);
    }
    public boolean addUser(TaiKhoan taiKhoan){
        SQLiteDatabase db=dBhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user",taiKhoan.getUsername());
        values.put("password",taiKhoan.getPassword());
        long ram=db.insert("TaiKhoan",null,values);
        return (ram >0);
    }
    public boolean checkdangnhap(String user,String password){
        SQLiteDatabase db=dBhelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from TaiKhoan where user=? and password=?",new String[]{user,password});
        int row=cursor.getCount();
        return (row>0);
    }

    public boolean checkTaiKoan(String user){
        SQLiteDatabase db=dBhelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from TaiKhoan where user=? ",new String[]{user});
        int row=cursor.getCount();
        return (row>0);
    }
}
