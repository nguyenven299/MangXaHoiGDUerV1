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

public class EditSocialNetworkImple implements EditSocialNetwork {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Thong_Bao");
    final private String Uid = "uid";
    final private String HinhThongBao = "Hinh_Thong_Bao";
    final private String Thong_Bao = "Thong_Bao";
    final private String Thoi_Gian = "Thoi_Gian";
    final private String Ho_Ten = "Ho_Ten";
    final private String HinhDaiDien = "Hinh_Dai_Dien";

    public void UpdateSocialNetwork(Social social) {

        Social social1 = new Social();
        social1 = social;
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Uid, social1.getUid());
        hashMap.put(HinhThongBao, social1.getHinh_Thong_Bao());
        hashMap.put(Thoi_Gian, social1.getThoi_Gian());
        hashMap.put(Thong_Bao, social1.getThong_Bao());
        hashMap.put(Ho_Ten, social1.getHo_Ten());
        hashMap.put(HinhDaiDien, social1.getHinh_Dai_Dien());
        databaseReference.child(social1.getKey()).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("ThanhCong", "onComplete: ThanhCong ");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

}
