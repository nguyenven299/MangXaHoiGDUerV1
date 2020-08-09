package com.example.Controller.FirebaseFirestore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.Model.GV;
import com.example.View.Activity.UpdateNotificationActivity;
import com.example.mxh_gdu3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import static com.facebook.FacebookSdk.getApplicationContext;

public class CheckAccGVExist {
    public static CheckAccGVExist instace;
    public static CheckAccGVExist getInstance()
    {
        if(instace == null)
            instace = new CheckAccGVExist();
        return instace;
    }
    public interface IcheckAccGVExist
    {
        void onExist(String Uid);
        void onExistGV(GV gv);
    }
    public void CheckAccGV(final String uid, final IcheckAccGVExist icheckAccGVExist)
    {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("GV").document(uid).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            icheckAccGVExist.onExist(uid);
                        }
                    }
                });

    }
}
