package com.example.View.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Controller.FirebaseRealtime.SocialNetwork.ReadSocialNetwork;
import com.example.Controller.FirebaseRealtime.User.InsertDataUserRealtime;
import com.example.View.Adapter.SocialAdapter;
import com.example.Model.Social;
import com.example.mxh_gdu3.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeFragment extends Fragment {
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private ListView listView;
    private ArrayList<Social> socialList;
    private Social social;
    private SocialAdapter socialAdapter;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    //DATA
    private static ArrayList<Social> mangThongBao = new ArrayList<>();
    private static ArrayList<Social> mangThongBaoTam = new ArrayList<>();
    private boolean ascending = true;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progressBar);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setReverseLayout(true);
        linearLayoutManager1.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager1);
//        nhanThongBao();
        socialList = new ArrayList<Social>();
        ReadSocialNetwork.getInstance().ReadDataSocial(new ReadSocialNetwork.IReadSocialNetwork() {
            @Override
            public void onReadSocialNetworkSuccess(List<Social> socialList) {
                socialAdapter = new SocialAdapter(socialList, getContext());
                recyclerView.setAdapter(socialAdapter);
                socialAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReadSocialNetworkFail(String error) {
                Log.d("LoiHienThiTrangChu", "onReadSocialNetworkFail: "+error);
            }

        });

        return view;
    }


}
