package com.example.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.Module.GV;
import com.example.Module.SV;
import com.example.View.UI.ChatFragment;
import com.example.View.UI.ContactFragment;
import com.example.View.UI.HomeFragment;
import com.example.View.UI.ProfileFragment;
import com.example.mxh_gdu3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class NavigationActivity extends AppCompatActivity {
    private CircleImageView HinhDaiDien;
    private TextView HoTen;
    private Button DangThongBao;
    private Toolbar toolbar;
    private SV sv;
    private GV gv;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private ListenerRegistration listenerRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.inflateMenu(R.menu.profile_menu);
        addControls();
        HinhDaiDien = findViewById(R.id.imageViewHinhDaiDien);
        HoTen = findViewById(R.id.HoTen);
        DangThongBao = findViewById(R.id.buttonDangThongBao);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(naListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
        DangThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener naListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment = null;
            switch (menuItem.getItemId()) {
                case R.id.navigation_TrangChu:
                    fragment = new HomeFragment();
                    break;
                case R.id.navigation_TrangCaNhanh:
                    fragment = new ProfileFragment();
                    break;
                case R.id.navigation_NhanTin:
                    fragment = new ChatFragment();
                    break;
                case R.id.navigation_DanhSach:
                    fragment = new ContactFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
            return true;
        }
    };

    @Override
    protected void onStart() {
        FirebaseFirestore rootRef1 = FirebaseFirestore.getInstance();
        DocumentReference docIdRef1 = rootRef1.collection("GV").document(firebaseUser.getUid());
        docIdRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        gv = documentSnapshot.toObject(GV.class);
                        assert gv != null;
                        DangThongBao.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        super.onStart();
    }

    private void addControls() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        final DocumentReference docRef = firebaseFirestore.collection("GV").document(firebaseUser.getUid());
        listenerRegistration = docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    String Ten = snapshot.getString("Ho_Ten");
                    String Hinh_Dai_Dien = snapshot.getString("Anh_Dai_Dien");
                    HoTen.setText(Ten);
                    if(Hinh_Dai_Dien.equals("default"))
                    {
                        HinhDaiDien.setImageResource(R.drawable.no_person);
                    }
                    else
                    {
                        Glide.with(getApplicationContext()).asBitmap().load(Hinh_Dai_Dien).into(new SimpleTarget<Bitmap>(200, 200) {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                resource = Bitmap.createScaledBitmap(resource, (int) (resource.getWidth() * 0.8), (int) (resource.getHeight() * 0.8), true);
                                HinhDaiDien.setImageBitmap(resource);
                            }
                        });
                    }

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("Uid", firebaseUser.getUid());
                    hashMap.put("Ho_Ten", Ten);
                    hashMap.put("Anh_Dai_Dien", Hinh_Dai_Dien);
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference();
                    databaseReference.child("Users").child(firebaseUser.getUid()).setValue(hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("firebase", "onSuccess: ");
                                }
                            });
                }

            }
        });
        final DocumentReference docRef1 = firebaseFirestore.collection("SV").document(firebaseUser.getUid());
        listenerRegistration = docRef1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    String Ten = snapshot.getString("Ho_Ten");
                    String Hinh_Dai_Dien = snapshot.getString("Anh_Dai_Dien");
                    HoTen.setText(Ten);
                    if (Hinh_Dai_Dien.equals("default")) {
                        HinhDaiDien.setImageResource(R.drawable.no_person);
                    } else {
                        Glide.with(getApplicationContext()).asBitmap().load(Hinh_Dai_Dien).into(new SimpleTarget<Bitmap>(200, 200) {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                resource = Bitmap.createScaledBitmap(resource, (int) (resource.getWidth() * 0.8), (int) (resource.getHeight() * 0.8), true);
                                HinhDaiDien.setImageBitmap(resource);
                            }
                        });
                    }
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("Uid", firebaseUser.getUid());
                    hashMap.put("Ho_Ten", Ten);
                    hashMap.put("Anh_Dai_Dien", Hinh_Dai_Dien);
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference();
                    databaseReference.child("Users").child(firebaseUser.getUid()).setValue(hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("firebase", "onSuccess: ");
                                }
                            });
                }
            }
        });

    }
}
