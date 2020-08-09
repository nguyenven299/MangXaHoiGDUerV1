package com.example.View.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.Controller.FirebaseFirestore.CheckAccExist;
import com.example.Controller.FirebaseFirestore.ReadDataGV;
import com.example.Controller.FirebaseFirestore.ReadDataSV;
import com.example.Model.GV;
import com.example.Model.SV;
import com.example.View.UI.ChatFragment;
import com.example.View.UI.ContactFragment;
import com.example.View.UI.HomeFragment;
import com.example.View.UI.ProfileFragment;
import com.example.mxh_gdu3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

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
        CheckAccExist.getInstance().CheckAccout(firebaseUser.getUid(), new CheckAccExist.IcheckAccExist() {
            @Override
            public void AccExist(String Exist) {
                Toast.makeText(getApplication(), Exist, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void AccNull(String Null) {
                Toast.makeText(getApplication(), Null, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplication(), InsertDataUserActivity.class);
                startActivity(intent);
            }
        });
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
        ReadDataGV.getInstance().ReadGV(firebaseUser.getUid(), new ReadDataGV.IreadDataGV() {
            @Override
            public void onImage(GV gv) {

            }

            @Override
            public void onImageNull(GV gv) {

            }

            @Override
            public void onSuccess(GV gv) {
                DangThongBao.setVisibility(View.VISIBLE);
            }
        });
        super.onStart();
    }

    private void addControls() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        ReadDataGV.getInstance().ReadGV(firebaseUser.getUid(), new ReadDataGV.IreadDataGV() {
            @Override
            public void onImage(GV gv) {
                Glide.with(getApplicationContext()).asBitmap().load(gv.getAnh_Dai_Dien()).into(new SimpleTarget<Bitmap>(200, 200) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        resource = Bitmap.createScaledBitmap(resource, (int) (resource.getWidth() * 0.8), (int) (resource.getHeight() * 0.8), true);
                        HinhDaiDien.setImageBitmap(resource);
                    }
                });
                HoTen.setText(gv.getHo_Ten());

            }

            @Override
            public void onImageNull(GV gv) {
                HinhDaiDien.setImageResource(R.drawable.no_person);
                HoTen.setText(gv.getHo_Ten());

            }

            @Override
            public void onSuccess(com.example.Model.GV gv) {

            }
        });
        ReadDataSV.getInstance().ReadSV(firebaseUser.getUid(), new ReadDataSV.IreadDataSV() {
            @Override
            public void onImage(SV sv) {
                Glide.with(getApplicationContext()).asBitmap().load(sv.getAnh_Dai_Dien()).into(new SimpleTarget<Bitmap>(200, 200) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        resource = Bitmap.createScaledBitmap(resource, (int) (resource.getWidth() * 0.8), (int) (resource.getHeight() * 0.8), true);
                        HinhDaiDien.setImageBitmap(resource);
                    }
                });
            }

            @Override
            public void onImageNull(SV sv) {
                HinhDaiDien.setImageResource(R.drawable.no_person);
            }

            @Override
            public void onSuccess(SV SV) {
                HoTen.setText(SV.getHo_Ten());
            }
        });

    }
}
