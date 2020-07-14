package com.example.Controller.FirebaseFirestore;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.Module.SV;
import com.example.View.InsertDataUserActivity;
import com.example.View.NavigationActivity;
import com.example.View.UpdateUserActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.facebook.FacebookSdk.getApplicationContext;

public class UpdateDataUserImple implements UpdateDataUser {
    final String USERNAME = "Ho_Ten";
    final String MASO = "MSSV";
    final String EMAIL = "Email";
    final String PHONENUMBER = "SDT";
    final String CARRER = "Nganh_Hoc";
    final String CLASS = "Lop_Hoc";
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


        public void UpdateUserData(SV SV, final UpdateUserActivity updateUserActivity)
        {
            SV SV1 = new SV();
            SV1 = SV;
        DocumentReference documentReference = firebaseFirestore.collection("SV").document(firebaseUser.getUid());
        documentReference.update(USERNAME,SV1.getHo_Ten());
        documentReference.update(MASO,SV1.getMSSV());
        documentReference.update(EMAIL,firebaseUser.getEmail());
        documentReference.update(PHONENUMBER,SV1.getSDT());
        documentReference.update(CARRER,SV1.getNganh_Hoc());
        documentReference.update(CLASS,SV1.getLopHoc())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        ChangeHomeActivy();
                        Log.d("TAG", "onSuccess: updateUser");
                    }
                });

    }
    public String ChangeHomeActivy() {
            String back ="ok";
            return back;

    }
}
