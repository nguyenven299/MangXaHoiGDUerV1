package com.example.Module;

public class SV {
    private  String Id;
    private String Ho_Ten;
    private String MSSV;
    private String SDT;
    private String Nganh_Hoc;
    private String LopHoc;
    private String Anh_Dai_Dien;
    private String Email;
    private String Uid;

    public SV(String id, String ho_Ten, String MSSV, String SDT, String nganh_Hoc, String lopHoc, String anh_Dai_Dien, String email, String uid) {
        Id = id;
        Ho_Ten = ho_Ten;
        this.MSSV = MSSV;
        this.SDT = SDT;
        Nganh_Hoc = nganh_Hoc;
        LopHoc = lopHoc;
        Anh_Dai_Dien = anh_Dai_Dien;
        Email = email;
        Uid = uid;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }



    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getHo_Ten() {
        return Ho_Ten;
    }

    public void setHo_Ten(String ho_Ten) {
        Ho_Ten = ho_Ten;
    }

    public String getMSSV() {
        return MSSV;
    }

    public void setMSSV(String MSSV) {
        this.MSSV = MSSV;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getNganh_Hoc() {
        return Nganh_Hoc;
    }

    public void setNganh_Hoc(String nganh_Hoc) {
        Nganh_Hoc = nganh_Hoc;
    }

    public String getLopHoc() {
        return LopHoc;
    }

    public void setLopHoc(String lopHoc) {
        LopHoc = lopHoc;
    }

    public String getAnh_Dai_Dien() {
        return Anh_Dai_Dien;
    }

    public void setAnh_Dai_Dien(String anh_Dai_Dien) {
        Anh_Dai_Dien = anh_Dai_Dien;
    }

    public SV() {
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}

