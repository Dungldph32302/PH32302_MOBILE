package com.example.ph32302_mobile.Mode;

public class ThanhVienMode {
    private int MaTV;
    private String nameTV, NamSinhTV;

    public ThanhVienMode() {
    }

    public ThanhVienMode(int maTV, String nameTV, String namSinhTV) {
        MaTV = maTV;
        this.nameTV = nameTV;
        NamSinhTV = namSinhTV;
    }

    public ThanhVienMode(String nameTV, String namSinhTV) {
        this.nameTV = nameTV;
        NamSinhTV = namSinhTV;
    }

    public int getMaTV() {
        return MaTV;
    }

    public void setMaTV(int maTV) {
        MaTV = maTV;
    }

    public String getNameTV() {
        return nameTV;
    }

    public void setNameTV(String nameTV) {
        this.nameTV = nameTV;
    }

    public String getNamSinhTV() {
        return NamSinhTV;
    }

    public void setNamSinhTV(String namSinhTV) {
        NamSinhTV = namSinhTV;
    }
}

