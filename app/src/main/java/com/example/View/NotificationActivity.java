package com.example.View;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.Module.GV;
import com.example.Controller.FirebaseRealtime.SocialNetwork.SendSocialNetwork;
import com.example.Controller.FirebaseRealtime.SocialNetwork.SendSocialNetworkImple;
import com.example.Module.Social;
import com.example.Module.UploadImage;
import com.example.mxh_gdu3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class NotificationActivity extends AppCompatActivity {
    private Button buttonChonAnh, buttonDongY, buttonHuy;
    private EditText editTextThongBao;
    private ImageView imageViewThongBao;
    private Bitmap resized;
    private TextView textViewHoTen;
    private Uri imageUri;
    private Bitmap selectBitmap;
    private Context context;
    private String HinhAnhMaHoaByte;
    private SendSocialNetworkImple sendSocialNetworkImple;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private FirebaseAuth firebaseAuth;
    private StorageTask storageTask;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private GV gV;
    private Social social = new Social();
    private SendSocialNetwork sendSocialNetwork = new SendSocialNetworkImple();
    Calendar calendar = Calendar.getInstance();
    String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
    private String urlImage;
    private String textAddress = "";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        buttonChonAnh = findViewById(R.id.buttonChonAnh);
        imageViewThongBao = findViewById(R.id.imageViewHinhAnh);
        editTextThongBao = findViewById(R.id.editTextThongBao);
        buttonDongY = findViewById(R.id.buttonDongY);
        buttonHuy = findViewById(R.id.buttonHuy);
        progressBar = findViewById(R.id.progressBar);
        Log.d("nguoi dung", "onCreate: " + firebaseUser.getUid());
        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NotificationActivity.this);
                builder.setTitle("Hủy");
                builder.setMessage("Bạn Muốn Hủy Thông Báo?");
                builder.setCancelable(false);
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        buttonChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonAnh();
            }
        });
        buttonDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextThongBao.getText().toString().length() < 10) {
                    Toast.makeText(NotificationActivity.this, "Vui Lòng Nhập Thông Báo", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    DongYDangThongBao();
                }


            }
        });
    }

    private void ChonAnh() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NotificationActivity.this);
        builder.setTitle("Chon Che Do Hinh");
        builder.setMessage("Ban Muon Dang Hinh Tu Thu Vien Hay May Anh?");
        builder.setCancelable(false);
        builder.setPositiveButton("Thu Vien", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ThuVien();
            }
        });
        builder.setNegativeButton("May Anh", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MayAnh();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void DongYDangThongBao() {

        if (imageUri != null) {
            storageReference = FirebaseStorage.getInstance().getReference("Hinh_Thong_Bao");
            String filemane = textAddress;
            final StorageReference storageReference1 = storageReference.child(firebaseUser.getUid() + "/" + filemane + "." + getFileExtension(imageUri));
            storageTask = storageReference1.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            UploadImage uploadImage = new UploadImage(imageUri.toString().trim(), taskSnapshot.getUploadSessionUri().toString());
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful()) ;
                            urlImage = urlTask.getResult().toString();
                            social.setHinh_Thong_Bao(urlImage);
                            if (editTextThongBao.getText().toString().equals(null)) {
                                Toast.makeText(NotificationActivity.this, "Vui Lòng Nhập Thông Báo", Toast.LENGTH_SHORT).show();
                            } else {
                                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                                DocumentReference docIdRef = rootRef.collection("GV").document(firebaseUser.getUid());
                                docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                GV gv = document.toObject(GV.class);
                                                social.setUid(firebaseUser.getUid());
                                                social.setHinh_Dai_Dien(gv.getAnh_Dai_Dien());
                                                social.setThong_Bao(editTextThongBao.getText().toString());
                                                social.setThoi_Gian(currentDate);
                                                sendSocialNetwork.SendSocialNetwork(social);
                                                finish();
                                            }
                                        }
                                    }
                                });
                                Toast.makeText(NotificationActivity.this, "Đăng Thông Báo Thành Công", Toast.LENGTH_SHORT).show();

                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NotificationActivity.this, "Lỗi ", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
            DocumentReference docIdRef = rootRef.collection("GV").document(firebaseUser.getUid());
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            Log.d("danh sach", "onComplete: " + documentSnapshot.getData());
                            GV gv = document.toObject(GV.class);
                            social.setUid(firebaseUser.getUid());
                            social.setHinh_Dai_Dien(gv.getAnh_Dai_Dien());
                            social.setThong_Bao(editTextThongBao.getText().toString());
                            social.setThoi_Gian(currentDate);
                            social.setHinh_Thong_Bao("default");
                            sendSocialNetwork.SendSocialNetwork(social);
                            finish();
                        }
                    }
                }
            });
            Toast.makeText(NotificationActivity.this, "Đăng Thông Báo Thành Công", Toast.LENGTH_SHORT).show();
        }
    }

    public void MayAnh() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 100);

        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void ThuVien() {
        Intent intent = new Intent();
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        String[] mimeType = {"image/jpeg", "image/png", "image/jpg"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType);
        startActivityForResult(intent, 200);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //xử lý hình ảnh chụp trực tiếp trên app
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            selectBitmap = (Bitmap) data.getExtras().get("data");
            Glide.with(this).asBitmap().load(selectBitmap).into(new SimpleTarget<Bitmap>(100, 100) {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                    storageReference = FirebaseStorage.getInstance().getReference("HinhAnhThongBao");
                    //chua lam xong can tim hieu
                    resized = Bitmap.createScaledBitmap(selectBitmap, (int) (selectBitmap.getWidth() * 0.8), (int) (selectBitmap.getHeight() * 0.8), true);
                    imageViewThongBao.setImageBitmap(resized);
                }
            });
        } else if (requestCode == 200 && resultCode == RESULT_OK) {
            //xử lý ảnh lấy từ đt
            imageUri = data.getData();
            try {
                selectBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                resized = Bitmap.createScaledBitmap(selectBitmap, (int) (selectBitmap.getWidth() * 0.8), (int) (selectBitmap.getHeight() * 0.8), true);
                imageViewThongBao.setImageBitmap(resized);
                Uri uri = data.getData();
                String path = uri.getPath();
                String filename = path.substring(path.lastIndexOf("/") + 1);
                textAddress = filename;
                Log.d("path", "onActivityResult: " + path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
