package com.example.ph32302_mobile.Mode;

public class PhieuMuonMode {
    private int MaPM,MaTV,MaSach;
    private String ngayMuon;
    private int TraSach,giaThue;
    private String TenTV,TenSach;
    String MaThuThu;
    public PhieuMuonMode() {
    }

    public String getMaThuThu() {
        return MaThuThu;
    }

    public void setMaThuThu(String maThuThu) {
        MaThuThu = maThuThu;
    }


    public PhieuMuonMode(int maTV, String maThuThu, int maSach, String ngayMuon, int traSach) {
        MaTV = maTV;
        MaThuThu = maThuThu;
        MaSach = maSach;
        this.ngayMuon = ngayMuon;
        TraSach = traSach;
    }

    public PhieuMuonMode(int maPM,String maThuThu, int maTV, int maSach, String ngayMuon, int traSach, int giaThue, String tenTV, String tenSach) {
        MaPM = maPM;
        MaThuThu = maThuThu;
        MaTV = maTV;
        MaSach = maSach;
        this.ngayMuon = ngayMuon;
        TraSach = traSach;
        this.giaThue = giaThue;
        TenTV = tenTV;
        TenSach = tenSach;
    }

    public int getMaPM() {
        return MaPM;
    }

    public void setMaPM(int maPM) {
        MaPM = maPM;
    }

    public int getMaTV() {
        return MaTV;
    }

    public void setMaTV(int maTV) {
        MaTV = maTV;
    }

    public int getMaSach() {
        return MaSach;
    }

    public void setMaSach(int maSach) {
        MaSach = maSach;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public int getTraSach() {
        return TraSach;
    }

    public void setTraSach(int traSach) {
        TraSach = traSach;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public String getTenTV() {
        return TenTV;
    }

    public void setTenTV(String tenTV) {
        TenTV = tenTV;
    }

    public String getTenSach() {
        return TenSach;
    }

    public void setTenSach(String tenSach) {
        TenSach = tenSach;
    }


}
