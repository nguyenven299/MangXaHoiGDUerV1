package com.example.Controller.FirebaseRealtime.SocialNetwork;

import androidx.annotation.NonNull;

import com.example.Model.Social;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdateSocialNetwork {
    public static UpdateSocialNetwork instance;
    final private String Uid = "uid";
    final private String HinhThongBao = "Hinh_Thong_Bao";
    final private String Thong_Bao = "Thong_Bao";
    final private String Thoi_Gian = "Thoi_Gian";
    final private String Ho_Ten = "Ho_Ten";
    final private String HinhDaiDien = "Hinh_Dai_Dien";

    public static UpdateSocialNetwork getInstance() {
        if (instance == null)
            instance = new UpdateSocialNetwork();
        return instance;
    }

    public interface IupdateSocialNetwork {
        void onSuccess(String Success);

        void onFail(String Fail);
    }

    public void UpdateSocial(String keySocial, Social social, final IupdateSocialNetwork iupdateSocialNetwork) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Thong_Bao");
        DatabaseReference databaseReference1 = databaseReference.child(keySocial);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Uid, social.getUid());
        hashMap.put(HinhThongBao, social.getHinh_Thong_Bao());
        hashMap.put(Thoi_Gian, social.getThoi_Gian());
        hashMap.put(Thong_Bao, social.getThong_Bao());
        hashMap.put(Ho_Ten, social.getHo_Ten());
        hashMap.put(HinhDaiDien, social.getHinh_Dai_Dien());
        databaseReference1.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                iupdateSocialNetwork.onSuccess("Cập Nhật Thành Công");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iupdateSocialNetwork.onFail("Cập Nhật Thất Bại");
            }
        });
    }
}
