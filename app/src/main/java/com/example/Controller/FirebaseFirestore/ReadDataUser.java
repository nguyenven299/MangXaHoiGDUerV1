package com.example.Controller.FirebaseFirestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ReadDataUser {
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public  void ReadDataUser(final String uid, final String MSSV, final String HoTen, final String Email, final String SDT, final  String NganhHoc, final String LopHoc)
    {

        DocumentReference documentReference = firebaseFirestore.collection("SV").document(uid);
        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            StringBuilder stringBuilder =new StringBuilder("");
                            stringBuilder.append("Ho_Ten").append(documentSnapshot.get(HoTen));
                            stringBuilder.append("MSSV").append(documentSnapshot.get(MSSV));
                            stringBuilder.append("Email").append(documentSnapshot.get(Email));
                            stringBuilder.append("SDT").append(documentSnapshot.get(SDT));
                            stringBuilder.append("Nganh_Hoc").append(documentSnapshot.get(NganhHoc));
                            stringBuilder.append("Lop_Hoc").append(documentSnapshot.get(LopHoc));
//                            stringBuilder.append("Hinh Dai Dien").append(documentSnapshot.get())
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "ReadDataUser: Fail ");
                    }
                });
    }
    public void ReadSV ()
    {
        firebaseFirestore.collection("SV")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Danh Sach", document.getId() + " => " + document.getData());

                            }
                        } else  {
                            Log.d("Danh Sach", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
