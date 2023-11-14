package com.example.ph32302_mobile.Mode;

public class ThuThuMode {
    private String maTT;
    private String HoTenTT,MatKhau;
    private int Quyen;

    public ThuThuMode() {
    }

    public ThuThuMode(String maTT, String hoTenTT, String matKhau,int Quyen) {
        this.maTT = maTT;
        HoTenTT = hoTenTT;
        MatKhau = matKhau;
        this.Quyen=Quyen;
    }

    public int getQuyen() {
        return Quyen;
    }

    public void setQuyen(int quyen) {
        Quyen = quyen;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public String getHoTenTT() {
        return HoTenTT;
    }

    public void setHoTenTT(String hoTenTT) {
        HoTenTT = hoTenTT;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }
}
