package com.example.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mxh_gdu3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActiviy extends AppCompatActivity {
    private Button buttonDangKy, buttonHuy;
    private EditText editTextEmail, editTextMatKhau, editTextNhapLaiMatKhau;
    private FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitity_signup);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imageView = findViewById(R.id.icon);
        TextView textView = findViewById(R.id.texticon);
        imageView.setImageResource(R.drawable.logo_gdu);
        textView.setText("Gia Dinh University");
        buttonDangKy = findViewById(R.id.buttonDangKy);
        buttonHuy = findViewById(R.id.buttonHuy);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextMatKhau = findViewById(R.id.editTextMatKhau);
        editTextNhapLaiMatKhau = findViewById(R.id.editTextNhapLaiMatKhau);

        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemTraEditText();
            }
        });
    }


    private void kiemTraEditText() {
        if (editTextEmail.getText().toString().isEmpty() || editTextMatKhau.getText().toString().isEmpty() || editTextNhapLaiMatKhau.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui Lòng Nhập Đủ Thông Tin", Toast.LENGTH_SHORT).show();
        } else if (!editTextMatKhau.getText().toString().equals(editTextNhapLaiMatKhau.getText().toString())) {
            editTextNhapLaiMatKhau.setError("Mật khẩu không trùng khớp");
        } else {
            DangKyTaiKhoan();
        }
    }

    private void DangKyTaiKhoan() {
        firebaseAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextMatKhau.getText().toString())
                .addOnCompleteListener(SignUpActiviy.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignUpActiviy.this, "Đăng Ký Tài Khoản Thành Công", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(SignUpActiviy.this, InsertDataUserActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("failure ", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActiviy.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

}
