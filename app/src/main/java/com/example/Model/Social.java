package com.example.Model;

public class Social {
    private String Thong_Bao;
    private String Uid;
    private String Thoi_Gian;
    private String Hinh_Thong_Bao;
    private String Key;
    private String Ho_Ten;
    private String Hinh_Dai_Dien;

    public String getThong_Bao() {
        return Thong_Bao;
    }

    public void setThong_Bao(String thong_Bao) {
        Thong_Bao = thong_Bao;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getThoi_Gian() {
        return Thoi_Gian;
    }

    public void setThoi_Gian(String thoi_Gian) {
        Thoi_Gian = thoi_Gian;
    }

    public String getHinh_Thong_Bao() {
        return Hinh_Thong_Bao;
    }

    public void setHinh_Thong_Bao(String hinh_Thong_Bao) {
        Hinh_Thong_Bao = hinh_Thong_Bao;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getHo_Ten() {
        return Ho_Ten;
    }

    public void setHo_Ten(String ho_Ten) {
        Ho_Ten = ho_Ten;
    }

    public String getHinh_Dai_Dien() {
        return Hinh_Dai_Dien;
    }

    public void setHinh_Dai_Dien(String hinh_Dai_Dien) {
        Hinh_Dai_Dien = hinh_Dai_Dien;
    }

    public Social() {
    }

    public Social(String thong_Bao, String uid, String thoi_Gian, String hinh_Thong_Bao, String key, String ho_Ten, String hinh_Dai_Dien) {
        Thong_Bao = thong_Bao;
        Uid = uid;
        Thoi_Gian = thoi_Gian;
        Hinh_Thong_Bao = hinh_Thong_Bao;
        Key = key;
        Ho_Ten = ho_Ten;
        Hinh_Dai_Dien = hinh_Dai_Dien;
    }

    @Override
    public String toString() {
        return "Social{" +
                "Thong_Bao='" + Thong_Bao + '\'' +
                ", Uid='" + Uid + '\'' +
                ", Thoi_Gian='" + Thoi_Gian + '\'' +
                ", Hinh_Thong_Bao='" + Hinh_Thong_Bao + '\'' +
                ", Key='" + Key + '\'' +
                ", Ho_Ten='" + Ho_Ten + '\'' +
                ", Hinh_Dai_Dien='" + Hinh_Dai_Dien + '\'' +
                '}';
    }
}
