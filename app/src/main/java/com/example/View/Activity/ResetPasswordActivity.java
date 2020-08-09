package com.example.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mxh_gdu3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPasswordActivity extends AppCompatActivity {
    private Button buttonDongY, buttonHuy;
    private EditText editTextMatKhau, editTextMatKhauNhapLai;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        buttonDongY = findViewById(R.id.buttonDongY);
        buttonHuy = findViewById(R.id.buttonHuy);
        editTextMatKhau = findViewById(R.id.editTextMatKhau);
        editTextMatKhauNhapLai = findViewById(R.id.editTextMatKhauNhapLai);
        setSupportActionBar(toolbar);
        ImageView imageView = findViewById(R.id.icon);
        TextView textView = findViewById(R.id.texticon);
        imageView.setImageResource(R.drawable.logo_gdu);
        textView.setText("Gia Dinh University");
        checkBox = findViewById(R.id.checkbox);
        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    if (editTextMatKhauNhapLai.getText().toString().trim().equals(editTextMatKhau.getText().toString().trim())) {
                        user.updatePassword(editTextMatKhau.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            Toast.makeText(ResetPasswordActivity.this, "Thay Đổi Mật Khẩu Thành Công", Toast.LENGTH_SHORT).show();

                                            finish();
                                        }
                                    }
                                });
                    } else {
                        editTextMatKhauNhapLai.setError("Mật Khẩu Không Trùng Khớp");
                    }
                }

            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editTextMatKhau.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editTextMatKhauNhapLai.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    editTextMatKhau.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editTextMatKhauNhapLai.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
}
