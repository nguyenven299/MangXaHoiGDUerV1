package com.example.Controller.FirebaseFirestore;

import android.view.View;

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

public class ShowInforGV {
    public static ShowInforGV instance;
    public static ShowInforGV getInstance()
    {
        if(instance== null)
            instance = new ShowInforGV();
        return instance;
    }
    public interface IshowInforGV
    {
        void onSuccess(GV gv);
    }
    public void ShowGV (final IshowInforGV ishowInforGV)
    {
        ListenerRegistration listenerRegistration;
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final DocumentReference docRef1 = firebaseFirestore.collection("GV").document(firebaseUser.getUid());
        listenerRegistration = docRef1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    GV gv = new GV();
                    String Ten = snapshot.getString("Ho_Ten");
                    String MSGV = snapshot.getString("MSGV");
                    String S_D_T = snapshot.getString("SDT");

                    gv.setHo_Ten(Ten);
                    gv.setMSGV(MSGV);
                    gv.setSDT(S_D_T);
                    ishowInforGV.onSuccess(gv);
                }
            }
        });
    }
}
