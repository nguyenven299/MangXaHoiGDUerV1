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

public class ReadDataGV {
    public static ReadDataGV instance;
    public static ReadDataGV getInstance()
    {
        if(instance == null)
            instance= new ReadDataGV();
        return instance;
    }
    public  interface IreadDataGV
    {
        void onImage(GV gv);
        void onImageNull(GV gv);
        void onSuccess(GV gv);
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
                    GV gv = snapshot.toObject(GV.class);


                    ireadDataGV.onSuccess(gv);
                    if (gv.getAnh_Dai_Dien().equals("default")) {


                        ireadDataGV.onImageNull(gv);
                    } else {

                        ireadDataGV.onImage(gv);
                    }

                }
            }
        });
    }
}
