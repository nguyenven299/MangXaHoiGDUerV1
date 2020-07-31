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

public class ShowInforSV {
    private static ShowInforSV instance;

    public static ShowInforSV getInstance() {
        if (instance == null)
            instance = new ShowInforSV();
        return instance;
    }

    public interface IshowInforSV {
        void onSuccess(SV SV);
    }

    public void ShowSV(final IshowInforSV ishowInforSV) {
        ListenerRegistration listenerRegistration;
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final DocumentReference docRef = firebaseFirestore.collection("SV").document(firebaseUser.getUid());
        listenerRegistration = docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    SV sv = new SV();
                    String Ten = snapshot.getString("Ho_Ten");
                    String MSSV = snapshot.getString("MSSV");
                    String S_D_T = snapshot.getString("SDT");
                    sv.setHo_Ten(Ten);
                    sv.setMSSV(MSSV);
                    sv.setSDT(S_D_T);
                    ishowInforSV.onSuccess(sv);
                }
            }
        });
    }
}
