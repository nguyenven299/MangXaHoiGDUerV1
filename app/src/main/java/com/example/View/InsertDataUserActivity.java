package com.example.View;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Controller.FirebaseFirestore.InsertDataUser;
import com.example.Controller.FirebaseFirestore.InsertDataUserImple;
import com.example.Module.SV;
import com.example.mxh_gdu3.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InsertDataUserActivity extends AppCompatActivity {
    private Button buttonDongY, buttonHuy;
    private String LopHoc1;
    private String NganhHoc1;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private String LopHocCNTT[] = {
            "11DHPM",
            "11DHPM1",
            "11DHPM2",
            "11DHPM3",
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
    private String NganhHoc[] = {
            "Chọn Ngành Học",
            "Công Nghệ Thông Tin",
            "Quản Trị Khách Sạn",
            "Luật",
            "Kinh Tế",
    };
    private EditText editTextHoTen, editTextMSSV, editTextSDT;
    private InsertDataUser insertDataUser = new InsertDataUserImple();
    private Spinner spinnerNganhHoc, spinnerLopHoc;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertdatauser);
        editTextHoTen = findViewById(R.id.editTextHoTen);
        editTextMSSV = findViewById(R.id.edtiTextMSSV);

        editTextSDT = findViewById(R.id.edtiTextSoDienThoai);
        buttonDongY = findViewById(R.id.buttonDongY);
        buttonHuy = findViewById(R.id.buttonHuy);
        spinnerLopHoc = findViewById(R.id.spinnerLopHoc);
        spinnerNganhHoc = findViewById(R.id.spinnerNganhHoc);
        spinnerNganhHoc.requestFocusFromTouch();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imageView = findViewById(R.id.icon);
        TextView textView = findViewById(R.id.texticon);
        imageView.setImageResource(R.drawable.logo_gdu);
        textView.setText("Gia Dinh University");
        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NganhHoc = NganhHoc1;
                String LopHoc = LopHoc1;
                String MSSV = editTextMSSV.getText().toString();
                String HoTen = editTextHoTen.getText().toString();
                String SDT = editTextSDT.getText().toString();
                String Email= firebaseUser.getEmail();
                if (MSSV.length() < 9 && HoTen.length() < 9 && SDT.length() != 10&&NganhHoc.length()<4 && LopHoc.length()<5 ) {
                    Toast.makeText(InsertDataUserActivity.this, "Thông Tin Không Chính Xác", Toast.LENGTH_SHORT).show();

                } else {
                    Access(MSSV, HoTen, SDT,Email, NganhHoc, LopHoc);
                }
            }
        });
        ChonNganhHoc();


    }

    private void Access(String mssv, String hoTen, String sdt,String email, String nganhHoc, String lopHoc) {
        SV SV = new SV();
        SV.setMSSV(mssv);
        SV.setHo_Ten(hoTen);
        SV.setLopHoc(lopHoc);
        SV.setNganh_Hoc(nganhHoc);
        SV.setSDT(sdt);
        SV.setEmail(email);
        InsertDataUserActivity insertDataUserActivity = new InsertDataUserActivity();
        insertDataUser.InsertDatabase(SV, insertDataUserActivity);
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
                Toast.makeText(InsertDataUserActivity.this, "" + NganhHoc1, Toast.LENGTH_SHORT).show();

                if (NganhHoc1.equals("Chọn Ngành Học")) {

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

                Toast.makeText(InsertDataUserActivity.this, "Vui Lòng Chọn Lớp Học", Toast.LENGTH_LONG).show();
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
                Toast.makeText(InsertDataUserActivity.this, "" + LopHoc1, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(InsertDataUserActivity.this, "Vui Lòng Chọn Lớp Học", Toast.LENGTH_LONG).show();
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
                Toast.makeText(InsertDataUserActivity.this, "" + LopHoc1, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(InsertDataUserActivity.this, "Vui Lòng Chọn Lớp Học", Toast.LENGTH_LONG).show();
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
                Toast.makeText(InsertDataUserActivity.this, "" + LopHoc1, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(InsertDataUserActivity.this, "Vui Lòng Chọn Lớp Học", Toast.LENGTH_LONG).show();
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
                Toast.makeText(InsertDataUserActivity.this, "" + LopHoc1, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(InsertDataUserActivity.this, "Vui Lòng Chọn Lớp Học", Toast.LENGTH_LONG).show();
            }
        });
    }
//
//    private void Access( FirebaseAuth firebaseAuth, EditText MSSV, EditText HoTen, EditText Email, EditText SDT, String NganhHoc, String LopHoc, EditText MatKhau) {
//        insertDataUser.InsertDatabase(this.firebaseAuth, MSSV,  HoTen,  Email,  SDT,  NganhHoc,  LopHoc, MatKhau);
//    }
}
