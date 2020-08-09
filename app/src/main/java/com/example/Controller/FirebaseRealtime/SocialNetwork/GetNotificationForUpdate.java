package com.example.Controller.FirebaseRealtime.SocialNetwork;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.Model.Social;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GetNotificationForUpdate {
    public static GetNotificationForUpdate instance;

    public static GetNotificationForUpdate getInstance() {
        if (instance == null)
            instance = new GetNotificationForUpdate();
        return instance;
    }

    public interface IgetNotificationForUpdate {
        void ImageNull(String ThongBao, String HinhThongBao);

        void Image(String ThongBao, String HinhThongBao);
    }

    public void GetImage(String keySocial, final IgetNotificationForUpdate igetNotificationForUpdate) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Thong_Bao");
        databaseReference.child(keySocial).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


//                    Social social1 = dataSnapshot.getValue(Social.class);
                    Social social1 = dataSnapshot.getValue(Social.class);
                    if (social1.getHinh_Thong_Bao().equals("default")) {
                        igetNotificationForUpdate.ImageNull(social1.getThong_Bao(), social1.getHinh_Thong_Bao());
                        Log.d("HinhThongBao", "onDataChange: khong co" + social1.getHinh_Thong_Bao());
                    } else {
                        igetNotificationForUpdate.Image(social1.getThong_Bao(), social1.getHinh_Thong_Bao());
                        Log.d("HinhThongBao", "onDataChange: co" + social1.getHinh_Thong_Bao());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
