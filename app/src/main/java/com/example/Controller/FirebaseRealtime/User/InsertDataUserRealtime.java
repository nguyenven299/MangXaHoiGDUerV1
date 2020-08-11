package com.example.Controller.FirebaseRealtime.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.Controller.FirebaseFirestore.ReadDataGV;
import com.example.Controller.FirebaseFirestore.ReadDataSV;
import com.example.Controller.FirebaseFirestore.ReadDataUser;
import com.example.Model.GV;
import com.example.Model.SV;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;

public class InsertDataUserRealtime {
    public static InsertDataUserRealtime instance;

    public static InsertDataUserRealtime getInstance() {
        if (instance == null)
            instance = new InsertDataUserRealtime();
        return instance;
    }

    public interface IinsertDataUser {

    }

    public void InserDataUserRealtime( IinsertDataUser insertDataUser) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("GV").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult())
                    {
                        GV gv = documentSnapshot.toObject(GV.class);
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("Uid", gv.getUid());
                        hashMap.put("Ho_Ten", gv.getHo_Ten());
                        hashMap.put("Anh_Dai_Dien", gv.getAnh_Dai_Dien());
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = firebaseDatabase.getReference();
                        databaseReference.child("Users").child(gv.getUid()).setValue(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                    }
                }
            }
        });
        FirebaseFirestore firebaseFirestore1 = FirebaseFirestore.getInstance();
        firebaseFirestore1.collection("SV").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult())
                    {
                        SV sv = documentSnapshot.toObject(SV.class);
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("Uid", sv.getUid());
                        hashMap.put("Ho_Ten", sv.getHo_Ten());
                        hashMap.put("Anh_Dai_Dien", sv.getAnh_Dai_Dien());
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = firebaseDatabase.getReference();
                        databaseReference.child("Users").child(sv.getUid()).setValue(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                    }
                }
            }
        });
    }
}
