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

public class ReadDataSV {
    public static ReadDataSV instance;

    public static ReadDataSV getInstance() {
        if (instance == null)
            instance = new ReadDataSV();
        return instance;
    }

    public interface IreadDataSV {
        void onImage(SV sv);

        void onImageNull(SV sv);

        void onSuccess(SV SV);
    }

    public void ReadSV(String iduser, final IreadDataSV ireadDataSV) {
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
                    SV sv = snapshot.toObject(SV.class);
                    ireadDataSV.onSuccess(sv);
                    if (sv.getAnh_Dai_Dien().equals("default")) {
                        ireadDataSV.onImageNull(sv);
                    } else {
                        ireadDataSV.onImage(sv);
                    }
                }
            }
        });
    }
}
