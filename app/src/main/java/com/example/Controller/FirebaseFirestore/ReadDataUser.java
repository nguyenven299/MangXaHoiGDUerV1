package com.example.Controller.FirebaseFirestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.Model.GV;
import com.example.Model.SV;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ReadDataUser {
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private static ReadDataUser instance;

    public static ReadDataUser getInstance() {
        if (instance == null)
            instance = new ReadDataUser();

        return instance;
    }

    public void ReadDataSV(final IReadDataUser iReadDataUser) {

        firebaseFirestore.collection("SV").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<SV> svList = new ArrayList<>();
                        svList = queryDocumentSnapshots.toObjects(SV.class);
                        iReadDataUser.onReadDataSuccess(svList, null);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        iReadDataUser.onReadDataFail(e.getMessage());
                    }
                });
    } public void ReadDataGV(final IReadDataUser iReadDataUser) {

        firebaseFirestore.collection("GV").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<GV> gvList = new ArrayList<>();
                        gvList = queryDocumentSnapshots.toObjects(GV.class);
                        iReadDataUser.onReadDataSuccess(null, gvList);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        iReadDataUser.onReadDataFail(e.getMessage());
                    }
                });
    }

    public interface IReadDataUser {
        void onReadDataSuccess(List<SV> svList, List<GV> gvList);

        void onReadDataFail(String error);
    }
}
