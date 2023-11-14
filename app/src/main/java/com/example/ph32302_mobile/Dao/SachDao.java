package com.example.ph32302_mobile.Dao;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ph32302_mobile.DataBase.DBhelper;
import com.example.ph32302_mobile.Mode.SachMode;

import java.util.ArrayList;
import java.util.List;

public class SachDao {
    private final DBhelper dBhelper;

    public SachDao(Context context) {
        dBhelper = new  DBhelper(context);
    }
    // lấy toàn bộ dữ liệu từ database
    public ArrayList<SachMode> SellecAll(){
        ArrayList<SachMode> list= new ArrayList<>();
        SQLiteDatabase db= dBhelper.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("Select * from Sach",null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    SachMode sach= new SachMode();
                    sach.setMaSach(cursor.getInt(0));
                    sach.setMaTheLoai(cursor.getInt(1));
                    sach.setTenSach(cursor.getString(2));
                    sach.setGiaThue(cursor.getInt(3));
                    list.add(sach);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            Log.i(TAG,"lỗi",e);
        }
        return list;
    }
    public ArrayList<SachMode> getALL(){
        String sql="select * from Sach";
                return getALL();
    }

// top 10 sách bán chạy
@SuppressLint("Range")
public ArrayList<SachMode> getTop10MostBorrowedBooks() {
    List<SachMode> top10Books = new ArrayList<>();
    SQLiteDatabase db = dBhelper.getReadableDatabase();

    // Sử dụng truy vấn SQL để lấy danh sách 10 quyển sách xuất hiện nhiều lần nhất trong phiếu mượn
    String query = "SELECT Sach.*, COUNT(*) AS SoLuotMuon " +
            "FROM PM " +
            "INNER JOIN Sach ON PM.MaSach = Sach.MaSach " +
            "GROUP BY Sach.MaSach " +
            "ORDER BY SoLuotMuon DESC " +
            "LIMIT 10";

    Cursor cursor = db.rawQuery(query, null);
    if (cursor.moveToFirst()) {
        do {
            SachMode book = new SachMode();
            book.setMaSach(cursor.getInt(cursor.getColumnIndex("MaSach")));
            book.setMaTheLoai(cursor.getInt(cursor.getColumnIndex("MaTheLoai")));
            book.setTenSach(cursor.getString(cursor.getColumnIndex("TenSach")));
            book.setGiaThue(cursor.getInt(cursor.getColumnIndex("GiaThue")));
            // Thêm sách vào danh sách top 10
            top10Books.add(book);
        } while (cursor.moveToNext());
    }

    cursor.close();
    db.close();

    return (ArrayList<SachMode>) top10Books;
}


    public boolean updatelistSach(SachMode sp){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=dBhelper.getWritableDatabase();
        values.put("MaSach",sp.getMaSach());
        values.put("MaTheLoai",sp.getMaTheLoai());
        values.put("TenSach",sp.getTenSach());
        values.put("GiaThue",sp.getGiaThue());
        long row=db.update("Sach",values,"MaSach=?",new String[]{String.valueOf(sp.getMaSach())});
        return (row>0);
    }
    public boolean deleteSach(int id){
         SQLiteDatabase db= dBhelper.getWritableDatabase();
         long row=db.delete("Sach","MaSach=?",new String[]{String.valueOf(id)});
         return  (row>0);
    }
    public boolean addSach(SachMode sp){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=dBhelper.getWritableDatabase();
     //   values.put("MaSach",sp.getMaSach());
        values.put("TenSach",sp.getTenSach());
        values.put("MaTheLoai",sp.getMaTheLoai());
        values.put("GiaThue",sp.getGiaThue());
        long row=db.insert("Sach",null,values);
        return (row>0);
    }
}
