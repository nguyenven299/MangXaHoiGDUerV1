package com.example.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Controller.FirebaseFirestore.CheckAccExist;
import com.example.mxh_gdu3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private Button buttonDangKy, buttonDongY, buttonGuiMa, buttonGuiLaiMa;
    private EditText editTextEmail, editTextMatKhau;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onStart() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
            startActivity(intent);
            finish();

        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonDangKy = findViewById(R.id.buttonDangKy);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextMatKhau = findViewById(R.id.editTextMatKhau);
        TextView textViewQuenMK = findViewById(R.id.textViewQuenMK);
        textViewQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        buttonDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActiviy.class);
                startActivity(intent);
            }
        });
        buttonDongY = findViewById(R.id.buttonDongY);
        buttonDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(editTextEmail.getText().toString().trim())) {
                    editTextEmail.setError("Vui Lòng Nhập Email");
                } else if (TextUtils.isEmpty(editTextMatKhau.getText().toString().trim())) {
                    editTextMatKhau.setError("Vui Lòng Nhập Mật Khẩu");
                } else {
                    DangNhap();

                }
            }
        });
    }

    private void DangNhap() {
        String email = editTextEmail.getText().toString().trim();
        String matkhau = editTextMatKhau.getText().toString().trim();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(matkhau)) {
            Toast.makeText(MainActivity.this, "All Fildes are required", Toast.LENGTH_SHORT).show();

        } else {
            firebaseAuth.signInWithEmailAndPassword(email, matkhau)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                                startActivity(intent);
//
                            }
                        }
                    })
//
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Tài Khoản Không Chính Xác", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }
}
