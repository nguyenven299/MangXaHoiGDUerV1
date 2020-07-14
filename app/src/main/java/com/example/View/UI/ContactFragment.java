package com.example.View.UI;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.Module.GV;
import com.example.Module.SV;
import com.example.Controller.FirebaseFirestore.Adapter.GVAdapter;
import com.example.Controller.FirebaseFirestore.Adapter.SVAdapter;
import com.example.Controller.FirebaseFirestore.ReadDataUser;
import com.example.mxh_gdu3.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {
    private ProgressBar progressBarGV, progressBarSV;
    private RecyclerView recyclerViewSV, recyclerViewGV;
    private SVAdapter SVAdapter;
    private GVAdapter GVAdapter;
    private List<SV> sv;
    private List<GV> gv;
    private ReadDataUser readDataUser;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        progressBarGV = view.findViewById(R.id.progressBar2);
        progressBarSV = view.findViewById(R.id.progressBar3);
        recyclerViewSV = view.findViewById(R.id.recycler_view_SV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setReverseLayout(true);
        linearLayoutManager1.setStackFromEnd(true);
        recyclerViewSV.setHasFixedSize(true);
        recyclerViewSV.setLayoutManager(linearLayoutManager1);
        recyclerViewGV = view.findViewById(R.id.recycler_view_GV);
        recyclerViewGV.setHasFixedSize(true);
        recyclerViewGV.setLayoutManager(linearLayoutManager);
        sv = new ArrayList<>();
        gv = new ArrayList<>();
        readUser();
        return view;
    }
    private void readUser() {
        firebaseFirestore.collection("SV").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            return;
                        } else {
                            sv.clear();
                            sv = queryDocumentSnapshots.toObjects(SV.class);

                            SVAdapter = new SVAdapter(getContext(), sv);
                            recyclerViewSV.setAdapter(SVAdapter);
                            SVAdapter.notifyDataSetChanged();
                            progressBarSV.setVisibility(View.GONE);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Lỗi!", Toast.LENGTH_SHORT).show();
                    }
                });

        firebaseFirestore.collection("GV").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            return;
                        } else {
                            gv.clear();
                            gv = queryDocumentSnapshots.toObjects(GV.class);
                            GVAdapter = new GVAdapter(getContext(), gv);
                            recyclerViewGV.setAdapter(GVAdapter);
                            GVAdapter.notifyDataSetChanged();
                            progressBarGV.setVisibility(View.GONE);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Lỗi!", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}