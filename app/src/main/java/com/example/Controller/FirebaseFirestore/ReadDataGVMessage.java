package com.example.Controller.FirebaseFirestore;

import androidx.annotation.Nullable;

import com.example.Model.GV;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class ReadDataGVMessage {
    public static ReadDataGVMessage instance;
    public static ReadDataGVMessage getInstance()
    {
        if(instance == null)
            instance= new ReadDataGVMessage();
        return instance;
    }
    public  interface IreadDataGV
    {
        void onImage(GV gv);
        void onImageNull(GV gv);
    }
    public void ReadGV(String iduser,final IreadDataGV ireadDataGV)
    {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        ListenerRegistration listenerRegistration;
        final DocumentReference docRef1 = firebaseFirestore.collection("GV").document(iduser);
        listenerRegistration = docRef1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    String Ten = snapshot.getString("Ho_Ten");
                    String Hinh_Dai_Dien = snapshot.getString("Anh_Dai_Dien");

                    if (Hinh_Dai_Dien.equals("default")) {
                        GV gv = new GV();
                        gv.setHo_Ten(Ten);
                        gv.setAnh_Dai_Dien(Hinh_Dai_Dien);
                        ireadDataGV.onImageNull(gv);
                    } else {
                        GV gv = new GV();
                        gv.setHo_Ten(Ten);
                        gv.setAnh_Dai_Dien(Hinh_Dai_Dien);
                        ireadDataGV.onImage(gv);
                    }
                }
            }
        });
    }
}
