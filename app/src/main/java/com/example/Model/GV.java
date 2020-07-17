package com.example.Model;

public class GV {
    private  String Ho_Ten;
    private String MSGV;
    private String SDT;
    private String Nganh_Day;
    private String LopHoc;
    private String Anh_Dai_Dien;
    private String Email;
    private String Uid;

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public GV( String ho_Ten, String MSGV, String SDT, String nganh_Day, String lopHoc, String anh_Dai_Dien, String email, String uid) {
        Ho_Ten = ho_Ten;
        this.MSGV = MSGV;
        this.SDT = SDT;
        Nganh_Day = nganh_Day;
        LopHoc = lopHoc;
        Anh_Dai_Dien = anh_Dai_Dien;
        Email = email;
        Uid = uid;
    }

    public GV() {
    }


    public String getHo_Ten() {
        return Ho_Ten;
    }

    public void setHo_Ten(String ho_Ten) {
        Ho_Ten = ho_Ten;
    }

    public String getMSGV() {
        return MSGV;
    }

    public void setMSGV(String MSGV) {
        this.MSGV = MSGV;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getNganh_Day() {
        return Nganh_Day;
    }

    public void setNganh_Day(String nganh_Hoc) {
        Nganh_Day = nganh_Hoc;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
