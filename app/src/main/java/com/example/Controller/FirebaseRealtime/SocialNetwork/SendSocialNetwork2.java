package com.example.Controller.FirebaseRealtime.SocialNetwork;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.Model.Social;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SendSocialNetwork2 {
    public static SendSocialNetwork2 instance;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Thong_Bao");
    final private String Uid = "uid";
    final private String HinhThongBao = "Hinh_Thong_Bao";
    final private String Thong_Bao = "Thong_Bao";
    final private String Thoi_Gian = "Thoi_Gian";
    final private String Ho_Ten = "Ho_Ten";
    final private String HinhDaiDien = "Hinh_Dai_Dien";

    public static SendSocialNetwork2 getInstance() {
        if (instance == null)
            instance = new SendSocialNetwork2();
        return instance;
    }

    public void SendSocialNetwork2(Social social, final ISendSocicalNetwork2 iSendSocicalNetwork2) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Uid, social.getUid());
        hashMap.put(HinhThongBao, social.getHinh_Thong_Bao());
        hashMap.put(Thoi_Gian, social.getThoi_Gian());
        hashMap.put(Thong_Bao, social.getThong_Bao());
        hashMap.put(Ho_Ten, social.getHo_Ten());
        hashMap.put(HinhDaiDien, social.getHinh_Dai_Dien());
        databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                iSendSocicalNetwork2.onSendSuccess("Đăng Thông Báo Thành Công");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        iSendSocicalNetwork2.onSendFail(e.getMessage());
                    }
                });
    }

    public interface ISendSocicalNetwork2 {
        void onSendSuccess(String Success);

        void onSendFail(String Fail);
    }
}
