package com.example.Controller.FirebaseFirestore;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.Model.SV;

import com.example.View.Activity.InsertDataUserActivity;
import com.example.View.Activity.NavigationActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

public class InsertDataUser {
    final String USERNAME = "Ho_Ten";
    final String MASO = "MSSV";
    final String PHONENUMBER = "SDT";
    final String CARRER = "Nganh_Hoc";
    final String CLASS = "Lop_Hoc";
    final String AVATAR = "Anh_Dai_Dien";
    final String uid = "uid";
    final String Email = "Email";
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private static InsertDataUser instance;
    public static InsertDataUser getInstance()
    {
        if(instance ==null)

            instance = new InsertDataUser();
            return instance;

    }
public interface IinsertDatabase
    {
        void onSuccess(String Success);
        void onFail(String Fail);

    }

    public void InsertDatabase(SV SV1, final IinsertDatabase iinsertDatabase) {

        Log.d("firebase", "InsertDatabase: ");

        Log.d("TAG", "InsertDatabase: " + SV1.getMSSV());
        Map<String, Object> data = new HashMap<>();
        data.put(USERNAME, SV1.getHo_Ten());
        data.put(MASO, SV1.getMSSV());
        data.put(PHONENUMBER, SV1.getSDT());
        data.put(CARRER, SV1.getNganh_Hoc());
        data.put(CLASS, SV1.getLopHoc());
        data.put(AVATAR,"default");
        data.put(uid,firebaseUser.getUid());
        data.put(Email,SV1.getEmail());
        data.put("Uid",firebaseUser.getUid());

        firebaseFirestore.collection("SV").document(firebaseUser.getUid()).set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       iinsertDatabase.onSuccess("Thêm Thông Tin Thành Công");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        iinsertDatabase.onFail("Thêm Thông Tin Thất Bại");
                    }
                });
    }

}
