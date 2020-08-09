package com.example.Controller.FirebaseRealtime.User;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class InsertDataUserRealtime {
    public static InsertDataUserRealtime instance;

    public static InsertDataUserRealtime getInstance() {
        if (instance == null)
            instance = new InsertDataUserRealtime();
        return instance;
    }

    public interface IinsertDataUser {

    }

    public void InserDataUserRealtime(String uid, String Ten, String Hinh_Dai_Dien, IinsertDataUser insertDataUser) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Uid", uid);
        hashMap.put("Ho_Ten", Ten);
        hashMap.put("Anh_Dai_Dien", Hinh_Dai_Dien);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Users").child(uid).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
    }
}
