package com.example.ph32302_mobile.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {
    public static final String DB="ndfg";
    public DBhelper(@Nullable Context context) {
        super(context,DB,null,34);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table TheLoai(\n" +
                    " MaTheLoai integer primary key autoincrement,\n" +
                    "\tTenTheLoai text not null\n" + ")");
            db.execSQL("create table Sach(\n" +
                    "     MaSach integer primary key autoincrement ,\n" +
                    "\t MaTheLoai integer,\n" +
                    "\t TenSach text,\n" +
                    "\t GiaThue integer,\n" +
                    "\t foreign key (MaTheLoai) references TheLoai\n" +
                    ")");
            db.execSQL("create table ThanhVien(\n" +
                    "    MaThanhVien integer primary key autoincrement,\n" +
                    "\tHoVaTen text,\n" +
                    "\tNamSinh text\n" +
                    ")");
            db.execSQL("create table ThuThu(\n" +
                    "  MaThuThu text primary key ,\n" +
                    "\thoVaTen text,\n" +
                    "\tMatKhau text,\n" +
                    "\tQuyen integer"+
                    ")");
            db.execSQL("create table PM(\n" +
                    " MaPM integer primary key autoincrement ,\n" +
                    "\t MaThanhVien integer references ThanhVien(MaThanhVien),\n" +
                    "\t MaThuThu text references ThuThu(MaThuThu),\n" +
                    "\t MaSach integer  references Sach(MaSach),\n" +
                    "\t NgayMuon text,\n" +
                    "\t TraSach integer\n" +
//                    "\t GiaThue integer references Sach(GiaThue)\n" +
                    ")");

            // thêm dữ liệu vào bảng
        db.execSQL("insert into TheLoai values (1,'Kinh tế')");
        db.execSQL("INSERT INTO TheLoai values (2, 'Ngoại Ngữ')");
        db.execSQL("INSERT INTO TheLoai values (3, 'Công nghệ thông tin')");
        db.execSQL("INSERT INTO TheLoai values (4, 'Ẩm thực')");
        db.execSQL("INSERT INTO TheLoai values (5, 'Sức Khỏe')");

        // thêm sữ liệu bảng sách
        db.execSQL(" insert into Sach values (1,1,'Sách làm giàu','10.000')");
        db.execSQL("insert into Sach values (2,1,'Sách lập nghiệp',92000)");
        db.execSQL("    insert into Sach values (3,3,'Sách lập trình C ',100000)");
        db.execSQL(" insert into Sach values (4,5,'Sách Vận động 360 ngày ',200000)");
        db.execSQL(" insert into Sach values (5,5,'Sách pháp luật ',200000)");

        // thêm dữ liệu vào bảng thành viên
        db.execSQL(" insert into ThanhVien values (1,'Nguyễn Văn A','2004-09-17')");
        db.execSQL("insert into ThanhVien values (2,'Nguyễn Văn B','2002-08-20')");
        db.execSQL(" insert into ThanhVien values (3,'Nguyễn Văn C','2004-03-29')");

        // thêm dữ liệu vào bảng thủ thư
        db.execSQL("  insert into ThuThu values ('abc','Trần Văn A','abc1',0)");
        db.execSQL("insert into ThuThu values ('aaa','Trần Văn B','1234a',0)");
        db.execSQL(" insert into ThuThu values ('blk','Trần Văn C','blk1',0)");
        db.execSQL(" insert into ThuThu values ('admin','Admin','admin',1)");


        // thêm dữ liệu vào bảng phiếu mượn
        db.execSQL(" insert into PM values (1,1,2,3,'2023-02-09',1)");
        db.execSQL("insert into PM values (2,3,2,3,'2023-02-09',0)");
        db.execSQL(" insert into PM values (3,2,2,2,'2023-10-29',0)");
        db.execSQL("insert into PM values (4,1,2,1,'2023-06-30',1)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if(oldVersion!= newVersion){
                db.execSQL("drop table if exists admin");
                db.execSQL(" drop table if exists PM");
                db.execSQL(" drop table if exists TheLoai");
                db.execSQL(" drop table if exists Sach ");
                db.execSQL(" drop table if exists ThanhVien ");
                db.execSQL(" drop table if exists ThuThu ");
                onCreate(db);
            }
    }
}
