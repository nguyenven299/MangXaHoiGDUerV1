package com.example.Module;

public class Users {
 String Anh_Dai_Dien;
 String Ho_Ten;
 String Uid;

    public Users() {
    }

    public String getAnh_Dai_Dien() {
        return Anh_Dai_Dien;
    }

    public void setAnh_Dai_Dien(String anh_Dai_Dien) {
        Anh_Dai_Dien = anh_Dai_Dien;
    }

    public String getHo_Ten() {
        return Ho_Ten;
    }

    public void setHo_Ten(String ho_Ten) {
        Ho_Ten = ho_Ten;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public Users(String anh_Dai_Dien, String ho_Ten, String uid) {
        Anh_Dai_Dien = anh_Dai_Dien;
        Ho_Ten = ho_Ten;
        Uid = uid;
    }
}
