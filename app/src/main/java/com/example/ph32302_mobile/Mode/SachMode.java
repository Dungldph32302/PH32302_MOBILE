package com.example.ph32302_mobile.Mode;

public class SachMode {
    private int MaSach;
    private String TenSach;

    private int MaTheLoai,GiaThue;

    public SachMode() {
    }

    public SachMode(int maSach, String tenSach, int maTheLoai, int giaThue) {
        MaSach = maSach;
        TenSach = tenSach;
        MaTheLoai = maTheLoai;
        GiaThue = giaThue;
    }

    public SachMode(String tenSach, int maTheLoai, int giaThue) {
        TenSach = tenSach;
        MaTheLoai = maTheLoai;
        GiaThue = giaThue;
    }

    public int getMaSach() {
        return MaSach;
    }

    public void setMaSach(int maSach) {
        MaSach = maSach;
    }

    public String getTenSach() {
        return TenSach;
    }

    public void setTenSach(String tenSach) {
        TenSach = tenSach;
    }

    public int getMaTheLoai() {
        return MaTheLoai;
    }

    public void setMaTheLoai(int maTheLoai) {
        MaTheLoai = maTheLoai;
    }

    public int getGiaThue() {
        return GiaThue;
    }

    public void setGiaThue(int giaThue) {
        GiaThue = giaThue;
    }
}
