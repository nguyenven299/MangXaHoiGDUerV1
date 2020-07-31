package com.example.Controller.FirebaseRealtime.SocialNetwork;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.Model.Social;
import com.example.View.Adapter.SocialAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;
import static java.security.AccessController.getContext;

public class ReadSocialNetwork {
    private static ReadSocialNetwork instance;

    public static ReadSocialNetwork getInstance() {
        if (instance == null)
            instance = new ReadSocialNetwork();
        return instance;
    }
    public interface IReadSocialNetwork
    {
        void onReadSocialNetworkSuccess(List<Social> socialList);
        void onReadSocialNetworkFail(String error);

    }
    public void ReadDataSocial(final IReadSocialNetwork iReadSocialNetwork)
    {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Thong_Bao");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Social> socialList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Social social1 = dataSnapshot1.getValue(Social.class);
                    socialList.add(social1);
                    String key = dataSnapshot1.getKey();
                    social1.setKey(key);

                }
                iReadSocialNetwork.onReadSocialNetworkSuccess(socialList);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iReadSocialNetwork.onReadSocialNetworkFail(databaseError.getMessage());
            }
        });
    }
}
