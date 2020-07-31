package com.example.Controller.FirebaseFirestore;

import androidx.annotation.Nullable;

import com.example.Model.SV;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class ReadDataSVMessage {
    public static ReadDataSVMessage instance;
    public static ReadDataSVMessage getInstance()
    {
        if(instance == null)
            instance = new ReadDataSVMessage();
        return instance;
    }
    public interface IreadDataSV
    {
        void onImage(SV sv);
        void onImageNull(SV sv);
    }
    public void ReadSV(String iduser,final IreadDataSV ireadDataSV)
    {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        ListenerRegistration listenerRegistration;
        final DocumentReference docRef1 = firebaseFirestore.collection("SV").document(iduser);
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
                        SV sv = new SV();
                        sv.setHo_Ten(Ten);
                        sv.setAnh_Dai_Dien(Hinh_Dai_Dien);
                        ireadDataSV.onImageNull(sv);
                    } else {
                        SV sv = new SV();
                        sv.setHo_Ten(Ten);
                        sv.setAnh_Dai_Dien(Hinh_Dai_Dien);
                        ireadDataSV.onImage(sv);
                    }
                }
            }
        });
    }
}
