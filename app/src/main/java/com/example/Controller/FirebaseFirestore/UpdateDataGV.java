package com.example.Controller.FirebaseFirestore;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.Model.GV;
import com.example.View.Activity.NavigationActivity;
import com.example.View.Activity.UpdateUserActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateDataGV {
    private static UpdateDataGV instance;
    public static UpdateDataGV getInstance()
    {
        if(instance == null)
            instance= new UpdateDataGV();
        return instance;
    }
    public interface  IupdateDataGV
    {
        void onSuccess(String Success);
        void onFail(String Fail);
    }
    public void UpdateGV(GV GV, final IupdateDataGV iupdateDataGV)
    {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference documentReference1 = firebaseFirestore.collection("GV").document(firebaseUser.getUid());
        documentReference1.update("Ho_Ten", GV.getHo_Ten());
        documentReference1.update("MSGV", GV.getMSGV());
        documentReference1.update("Email", firebaseUser.getEmail());
        documentReference1.update("SDT", GV.getSDT());
        documentReference1.update("Nganh_Day", GV.getNganh_Day())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        iupdateDataGV.onSuccess("Cập Nhật Thành Công");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iupdateDataGV.onFail("Cập Nhật Thất Bại");
            }
        });
    }
}
