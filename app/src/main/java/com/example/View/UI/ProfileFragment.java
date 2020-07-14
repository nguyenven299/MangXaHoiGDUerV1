package com.example.View.UI;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.Module.GV;
import com.example.Module.SV;
import com.example.Module.Social;
import com.example.Module.UploadImage;
import com.example.View.InsertDataUserActivity;
import com.example.View.MainActivity;
import com.example.View.ResetPasswordActivity;
import com.example.View.UpdateUserActivity;
import com.example.mxh_gdu3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

public class ProfileFragment extends Fragment {
    private Social social;
    private ProgressBar progressBar;
    private TextView HoTen, Email, SDT, MaSo, ChuyenNganh;
    private CircleImageView HinhDaiDien;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private GV gv;
    private SV sv;
    private Bitmap resized;
    private Bitmap selectBitmap;
    private Uri imageUri;
    private StorageReference storageReference;
    private StorageTask storageTask;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private String textAddress = "";
    private ImageView imageViewDangXuat, imageViewChinhSua, imageViewDoiMatKhau;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ListenerRegistration listenerRegistration;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        HoTen = view.findViewById(R.id.HoTen);
        imageViewDoiMatKhau = view.findViewById(R.id.iconSuaMatKhau);
        Email = view.findViewById(R.id.Email);
        SDT = view.findViewById(R.id.SDT);
        MaSo = view.findViewById(R.id.MaSo);
        ChuyenNganh = view.findViewById(R.id.ChuyenNganh);
        HinhDaiDien = view.findViewById(R.id.HinhDaiDien);
        imageViewDangXuat = view.findViewById(R.id.iconDangXuat);
        imageViewChinhSua = view.findViewById(R.id.iconSuaThongTin);
        imageViewDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
        imageViewChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateUserActivity.class);
                startActivity(intent);

            }
        });
        imageViewDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getContext(), MainActivity.class);
                getContext().startActivity(intent);
                getActivity().finish();
            }
        });
        addControls();
        HinhDaiDien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThuVien();
            }
        });
        return view;
    }

    private void addControls() {
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
                        HoTen.setText(gv.getHo_Ten());
                        Email.setText(gv.getEmail());
                        MaSo.setText(gv.getMSGV());
                        SDT.setText(gv.getSDT());
                        ChuyenNganh.setText(gv.getNganh_Day());
                        if (!gv.getAnh_Dai_Dien().equals("default")) {
                            Glide.with(getApplicationContext()).asBitmap().load(gv.getAnh_Dai_Dien()).into(new SimpleTarget<Bitmap>(200, 200) {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    resource = Bitmap.createScaledBitmap(resource, (int) (resource.getWidth() * 0.8), (int) (resource.getHeight() * 0.8), true);
                                    HinhDaiDien.setImageBitmap(resource);
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                }
                            });
                        } else {
                            HinhDaiDien.setImageResource(R.drawable.no_person);
                        }
                        if (HoTen != null && Email != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });
        final DocumentReference docRef = firebaseFirestore.collection("SV").document(firebaseAuth.getUid());
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
                    String Lop_Hoc = snapshot.getString("Lop_Hoc");
                    String MSSV = snapshot.getString("MSSV");
                    String Nganh_Hoc = snapshot.getString("Nganh_Hoc");
                    String S_D_T = snapshot.getString("SDT");
                    Email.setText(firebaseUser.getEmail());
                    SDT.setText(S_D_T);
                    MaSo.setText(MSSV);
                    ChuyenNganh.setText(Nganh_Hoc);
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
                    if (HoTen != null && Email != null) {
                        progressBar.setVisibility(View.GONE);
                    }
                } else {

                }
            }
        });

    }

    public void ThuVien() {
        Intent intent = new Intent();
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        String[] mimeType = {"image/jpeg", "image/png", "image/jpg"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType);
        startActivityForResult(intent, 200);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 200 && resultCode == RESULT_OK) {
            imageUri = data.getData();
            Glide.with(getApplicationContext()).asBitmap().load(imageUri).into(new SimpleTarget<Bitmap>(200, 200) {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    UploadImage();
                    resource = Bitmap.createScaledBitmap(resource, (int) (resource.getWidth() * 0.8), (int) (resource.getHeight() * 0.8), true);


                    String path = imageUri.getPath();
                    String filename = path.substring(path.lastIndexOf("/") + 1);
                    textAddress = filename;
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {
                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getFileExtention(Uri uri1) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri1));
    }

    private void UploadImage() {

        if (imageUri != null) {
            storageReference = FirebaseStorage.getInstance().getReference("Anh_Dai_Dien");
            String filemane = textAddress;
            final StorageReference storageReference1 = storageReference.child(firebaseUser.getUid() + "/" + filemane + "." + getFileExtention(imageUri));
            storageTask = storageReference1.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            UploadImage uploadImage = new UploadImage(imageUri.toString().trim(), taskSnapshot.getUploadSessionUri().toString());
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful()) ;
                            final String urlImage = urlTask.getResult().toString();
                            try {
                                firebaseFirestore.collection("GV").document(firebaseUser.getUid())
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot documentSnapshot = task.getResult();
                                        if (documentSnapshot.exists()) {
                                            firebaseFirestore.collection("GV").document(firebaseUser.getUid()).update("Anh_Dai_Dien", urlImage)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(getContext(), "Thay Đổi Ảnh Thành Công", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getContext(), "Thay Đổi Ảnh Thất Bại", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else {
                                            firebaseFirestore.collection("SV").document(firebaseUser.getUid())
                                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    DocumentSnapshot documentSnapshot = task.getResult();
                                                    if (documentSnapshot.exists()) {
                                                        firebaseFirestore.collection("SV").document(firebaseUser.getUid()).update("Anh_Dai_Dien", urlImage)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        Toast.makeText(getContext(), "Thay Đổi Ảnh Thành Công", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(getContext(), "Thay Đổi Ảnh Thất Bại", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });

                            } catch (Exception e) {
                                Toast.makeText(getActivity(), "Loi ProfileFragment.java : encrypt url image ::: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Loi cmnr", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "Khong file upload", Toast.LENGTH_SHORT).show();
        }
    }
}
