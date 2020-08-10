package com.example.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mxh_gdu3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class ForgetPasswordActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imageView = findViewById(R.id.icon);
        TextView textView = findViewById(R.id.texticon);
        imageView.setImageResource(R.drawable.logo_gdu);
        textView.setText("Gia Dinh University");
        final MaterialEditText editTextEmail = findViewById(R.id.editTextEmail);
        Button buttonDongY = findViewById(R.id.buttonDongY);
        Button buttonTroVe = findViewById(R.id.buttonTroVe);
        buttonTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.sendPasswordResetEmail(editTextEmail.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgetPasswordActivity.this, "Đã gửi đến Email của bạn", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
            }
        });

    }
}
