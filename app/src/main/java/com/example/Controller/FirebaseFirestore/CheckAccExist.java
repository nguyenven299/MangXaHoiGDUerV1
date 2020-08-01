package com.example.Controller.FirebaseFirestore;

import androidx.annotation.NonNull;

import com.example.Model.GV;
import com.example.Model.SV;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CheckAccExist {
    public static CheckAccExist instance;
    public static CheckAccExist getInstance()
    {
        if(instance ==null)
            instance = new CheckAccExist();
        return instance;
    }
    public interface IcheckAccExist
    {
        void AccExist(String Exist);
        void AccNull(String Null);
    }
    public void CheckAccout(final String uid, final IcheckAccExist icheckAccExist)
    {
        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("GV").document(uid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       DocumentSnapshot documentSnapshot = task.getResult();
                       if(documentSnapshot.exists())
                       {
                           GV gv = documentSnapshot.toObject(GV.class);
                           icheckAccExist.AccExist("Chào " +gv.getHo_Ten() );
                       }
                       else
                       {
                           FirebaseFirestore firebaseFirestore1 = FirebaseFirestore.getInstance();
                           firebaseFirestore1.collection("SV").document(uid).get()
                                   .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                       @Override
                                       public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                           DocumentSnapshot document = task.getResult();
                                           if (document.exists()) {
                                               SV sv = document.toObject(SV.class);
                                               icheckAccExist.AccExist("Chào " +sv.getHo_Ten() );
                                           } else {
                                               icheckAccExist.AccNull("Vui Lòng Nhập Đầy Đủ Thông Tin");
                                           }
                                       }
                                   });
                       }
                    }
                });

    }
}
