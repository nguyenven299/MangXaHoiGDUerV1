package com.example.View.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Controller.FirebaseFirestore.UpdateDataUser;
import com.example.Controller.FirebaseFirestore.UpdateDataUserImple;
import com.example.Model.GV;
import com.example.Model.SV;
import com.example.mxh_gdu3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class UpdateUserActivity extends AppCompatActivity {
    private TextView textView, textViewNgangHoc, textViewLopHoc;
    private String NganhHoc[] = {
            "",
            "Công Nghệ Thông Tin",
            "Quản Trị Khách Sạn",
            "Luật",
            "Kinh Tế",
    };
    private String LopHocQTKS[] = {
            "11DHPM1",
            "11DHPM11",
            "11DHPM21",
            "11DHPM31",
    };
    private String LopHocLuat[] = {
            "11DHPM11",
            "11DHPM111",
            "11DHPM211",
            "11DHPM311",
    };
    private String LopHocKinhTe[] = {
            "11DHPM111",
            "11DHPM1111",
            "11DHPM2111",
            "11DHPM3111",
    };
    private String LopHocCNTT[] = {
            "11DHPM",
            "11DHPM1",
            "11DHPM2",
            "11DHPM3",
    };
    private EditText editTextHoTen, editTextMSSV, editTextSDT;
    private Button buttonDongY, buttonHuy;
    private Spinner spinnerNganhHoc, spinnerLopHoc;
    private String LopHoc1;
    private String NganhHoc1;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private UpdateDataUser updateDataUser = new UpdateDataUserImple();
    private SV SV = new SV();
    private GV GV = new GV();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    final String USERNAME = "Ho_Ten";
    final String MASO = "MSSV";
    final String EMAIL = "Email";
    final String PHONENUMBER = "SDT";
    final String CARRER = "Nganh_Hoc";
    final String CLASS = "Lop_Hoc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertdatauser);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imageView = findViewById(R.id.icon);
        TextView textView = findViewById(R.id.texticon);
        imageView.setImageResource(R.drawable.logo_gdu);
        textView.setText("Gia Dinh University");
        editTextHoTen = findViewById(R.id.editTextHoTen);
        editTextMSSV = findViewById(R.id.edtiTextMSSV);
        textViewLopHoc = findViewById(R.id.textViewLopHoc);
        textViewNgangHoc = findViewById(R.id.textViewNganhHoc);
        editTextSDT = findViewById(R.id.edtiTextSoDienThoai);
        buttonDongY = findViewById(R.id.buttonDongY);
        buttonHuy = findViewById(R.id.buttonHuy);
        spinnerLopHoc = findViewById(R.id.spinnerLopHoc);
        spinnerNganhHoc = findViewById(R.id.spinnerNganhHoc);
        spinnerNganhHoc.requestFocusFromTouch();

        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateUserActivity.this, NavigationActivity.class);
                startActivity(intent);
                finish();
            }
        });
        buttonDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NganhHoc = NganhHoc1;
                String LopHoc = null;
                String MSSV = editTextMSSV.getText().toString();
                String HoTen = editTextHoTen.getText().toString();
                String SDT = editTextSDT.getText().toString();
                String Email = firebaseUser.getEmail();
                if (spinnerLopHoc != null && spinnerLopHoc.getSelectedItem() != null) {
                    LopHoc = (String) spinnerLopHoc.getSelectedItem();
                    if (MSSV.length() < 9 && HoTen.length() < 9 && SDT.length() != 10 && NganhHoc.length() < 4) {
                        Toast.makeText(UpdateUserActivity.this, "Thông Tin Chưa Đầy Đủ", Toast.LENGTH_SHORT).show();
                    } else {
                        Access(MSSV, HoTen, SDT, Email, NganhHoc, LopHoc);
                        UpdateUserData();
                    }
                } else {
                    Toast.makeText(UpdateUserActivity.this, "Thông Tin Chưa Đầy Đủ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ChonNganhHoc();
        HienThiThongTin();
    }

    private void HienThiThongTin() {
        ListenerRegistration listenerRegistration;
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final DocumentReference docRef = firebaseFirestore.collection("SV").document(firebaseUser.getUid());
        listenerRegistration = docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    String Ten = snapshot.getString("Ho_Ten");
                    String MSSV = snapshot.getString("MSSV");
                    String S_D_T = snapshot.getString("SDT");
                    editTextSDT.setText(S_D_T);
                    editTextMSSV.setText(MSSV);
                    editTextHoTen.setText(Ten);
                }
            }
        });
        final DocumentReference docRef1 = firebaseFirestore.collection("GV").document(firebaseUser.getUid());
        listenerRegistration = docRef1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    String Ten = snapshot.getString("Ho_Ten");
                    String MSSV = snapshot.getString("MSGV");
                    String S_D_T = snapshot.getString("SDT");
                    editTextSDT.setText(S_D_T);
                    editTextMSSV.setText(MSSV);
                    editTextHoTen.setText(Ten);
                    textViewLopHoc.setVisibility(View.GONE);
                    spinnerLopHoc.setVisibility(View.GONE);
                    textViewNgangHoc.setText("Chuyên Ngành");
                }
            }
        });
    }

    public void UpdateUserData() {
        if (editTextHoTen.getText().toString().isEmpty() && LopHoc1.isEmpty()) {
            Toast.makeText(this, "thieu kia", Toast.LENGTH_SHORT).show();
        } else {
            firebaseFirestore.collection("GV").document(firebaseUser.getUid()).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    DocumentReference documentReference1 = firebaseFirestore.collection("GV").document(firebaseUser.getUid());
                                    documentReference1.update("Ho_Ten", GV.getHo_Ten());
                                    documentReference1.update("MSGV", GV.getMSGV());
                                    documentReference1.update(EMAIL, firebaseUser.getEmail());
                                    documentReference1.update("SDT", GV.getSDT());
                                    documentReference1.update("Nganh_Day", GV.getNganh_Day())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(UpdateUserActivity.this, "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(UpdateUserActivity.this, NavigationActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                    Log.d("TAG", "onSuccess: updateUser");
                                                }
                                            });
                                } else {
                                    DocumentReference documentReference = firebaseFirestore.collection("SV").document(firebaseUser.getUid());
                                    documentReference.update(USERNAME, SV.getHo_Ten());
                                    documentReference.update(MASO, SV.getMSSV());
                                    documentReference.update(EMAIL, firebaseUser.getEmail());
                                    documentReference.update(PHONENUMBER, SV.getSDT());
                                    documentReference.update(CARRER, SV.getNganh_Hoc());
                                    documentReference.update(CLASS, SV.getLopHoc())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(UpdateUserActivity.this, "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(UpdateUserActivity.this, NavigationActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                    Log.d("TAG", "onSuccess: updateUser");
                                                }
                                            });
                                }
                            }
                        }
                    });


        }
    }

    private void Access(String mssv, String hoTen, String sdt, String email, String nganhHoc, String lopHoc) {
        SV.setMSSV(mssv);
        SV.setHo_Ten(hoTen);
        SV.setLopHoc(lopHoc);
        SV.setNganh_Hoc(nganhHoc);
        SV.setSDT(sdt);
        SV.setEmail(email);


        GV.setMSGV(mssv);
        GV.setHo_Ten(hoTen);
        GV.setNganh_Day(nganhHoc);
        GV.setSDT(sdt);
        GV.setEmail(email);
    }

    private void ChonNganhHoc() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (
                        this,
                        android.R.layout.simple_spinner_item,
                        NganhHoc
                );
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spinnerNganhHoc.setAdapter(adapter);
        //thiết lập sự kiện chọn phần tử cho Spinner
        spinnerNganhHoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NganhHoc1 = parent.getItemAtPosition(position).toString();
                Toast.makeText(UpdateUserActivity.this, "" + NganhHoc1, Toast.LENGTH_SHORT).show();

                if (position == 0) {
                    return;
                } else if (NganhHoc1.equals("Công Nghệ Thông Tin")) {
                    ChonLopHoc();
                } else if (NganhHoc1.equals("Quản Trị Khách Sạn")) {
                    ChonLopHoc1();
                } else if (NganhHoc1.equals("Luật")) {
                    ChonLopHoc2();
                } else if (NganhHoc1.equals("Kinh Tế")) {
                    ChonLopHoc3();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void ChonLopHoc() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (
                        this,
                        android.R.layout.simple_spinner_item,
                        LopHocCNTT
                );
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spinnerLopHoc.setAdapter(adapter);
        //thiết lập sự kiện chọn phần tử cho Spinner
        spinnerLopHoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LopHoc1 = parent.getItemAtPosition(position).toString();
                Toast.makeText(UpdateUserActivity.this, "" + LopHoc1, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(UpdateUserActivity.this, "Vui Lòng Chọn Lớp Học", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ChonLopHoc2() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (
                        this,
                        android.R.layout.simple_spinner_item,
                        LopHocLuat
                );
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spinnerLopHoc.setAdapter(adapter);
        //thiết lập sự kiện chọn phần tử cho Spinner
        spinnerLopHoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LopHoc1 = parent.getItemAtPosition(position).toString();
                Toast.makeText(UpdateUserActivity.this, "" + LopHoc1, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(UpdateUserActivity.this, "Vui Lòng Chọn Lớp Học", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ChonLopHoc3() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (
                        this,
                        android.R.layout.simple_spinner_item,
                        LopHocKinhTe
                );
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spinnerLopHoc.setAdapter(adapter);
        //thiết lập sự kiện chọn phần tử cho Spinner
        spinnerLopHoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LopHoc1 = parent.getItemAtPosition(position).toString();
                Toast.makeText(UpdateUserActivity.this, "" + LopHoc1, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(UpdateUserActivity.this, "Vui Lòng Chọn Lớp Học", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ChonLopHoc1() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (
                        this,
                        android.R.layout.simple_spinner_item,
                        LopHocQTKS
                );
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spinnerLopHoc.setAdapter(adapter);
        //thiết lập sự kiện chọn phần tử cho Spinner
        spinnerLopHoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LopHoc1 = parent.getItemAtPosition(position).toString();
                Toast.makeText(UpdateUserActivity.this, "" + LopHoc1, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(UpdateUserActivity.this, "Vui Lòng Chọn Lớp Học", Toast.LENGTH_LONG).show();
            }
        });
    }
}
