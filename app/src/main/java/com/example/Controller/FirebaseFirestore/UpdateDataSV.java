package com.example.Controller.FirebaseFirestore;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.Model.SV;
import com.example.View.Activity.NavigationActivity;
import com.example.View.Activity.UpdateUserActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateDataSV {
    final String USERNAME = "Ho_Ten";
    final String MASO = "MSSV";
    final String PHONENUMBER = "SDT";
    final String CARRER = "Nganh_Hoc";
    final String CLASS = "Lop_Hoc";
    final String Email = "Email";
    private static UpdateDataSV instance;

    public static UpdateDataSV getInstance() {
        if (instance == null)
            instance = new UpdateDataSV();
        return instance;
    }

    public interface IupdateDataSV {
        void Success(String Succees);

        void Fail(String Fail);
    }

    public void UpdateSV(SV SV, final IupdateDataSV iupdateDataSV) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference documentReference = firebaseFirestore.collection("SV").document(firebaseUser.getUid());
        documentReference.update(USERNAME, SV.getHo_Ten());
        documentReference.update(MASO, SV.getMSSV());
        documentReference.update(Email, firebaseUser.getEmail());
        documentReference.update(PHONENUMBER, SV.getSDT());
        documentReference.update(CARRER, SV.getNganh_Hoc());
        documentReference.update(CLASS, SV.getLopHoc())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        iupdateDataSV.Success("Cập Nhật Thành Công");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        iupdateDataSV.Fail("Cập Nhật Thất Bại");
                    }
                });
    }
}
