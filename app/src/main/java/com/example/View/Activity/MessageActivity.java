package com.example.View.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.Controller.FirebaseRealtime.Chat.ReadMessage;
import com.example.Controller.FirebaseRealtime.Chat.SendMessage;
import com.example.Controller.FirebaseFirestore.ReadDataGV;
import com.example.Controller.FirebaseFirestore.ReadDataSV;
import com.example.Model.GV;
import com.example.Model.SV;
import com.example.View.Adapter.MessageAdapter;
import com.example.Model.Chats;
import com.example.Model.Users;
import com.example.mxh_gdu3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {
    private CircleImageView HinhDaiDien;
    private TextView HoTen;
    private DatabaseReference databaseReference;
    private ImageButton imageButton_send;
    private EditText editText_send;
    private Intent intent;
    private MessageAdapter messageAdapter;
    private List<Chats> chats;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private Chats chat;
    private ListenerRegistration listenerRegistration;
    public String useridSV;
    public String useridGV;
    private Users users;
    private String userid;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        imageButton_send = findViewById(R.id.buttonGui);
        editText_send = findViewById(R.id.editTextTinNhan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        imageView = findViewById(R.id.HinhDaiDien);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        HinhDaiDien = findViewById(R.id.HinhDaiDien);
        HoTen = findViewById(R.id.HoTen);
        intent = getIntent();
        useridSV = intent.getStringExtra("useridSV");
        final String tokenUserId = intent.getStringExtra("tokenUser");
        useridGV = intent.getStringExtra("useridGV");
        userid = intent.getStringExtra("userid");
        imageButton_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editText_send.getText().toString();
                if (!msg.equals("")) {
                    try {
                        if (useridSV != null) {
                            sendMessage(firebaseUser.getUid(), useridSV, msg, tokenUserId);
                        }
                        if (useridGV != null) {
                            sendMessage(firebaseUser.getUid(), useridGV, msg, tokenUserId);
                        }
                        if (userid != null) {
                            sendMessage(firebaseUser.getUid(), userid, msg, tokenUserId);
                        }

                    } catch (Exception e) {
                        Toast.makeText(MessageActivity.this, ">>>>>>> Loi SendMessage", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                editText_send.setText("");

            }
        });
        if (userid != null) {
            final DocumentReference docRef = firebaseFirestore.collection("GV").document(userid);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            thongTinGV(userid);
                        } else {
                            final DocumentReference docRef1 = firebaseFirestore.collection("SV").document(userid);
                            docRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            thongTinSV(userid);
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            });
        }
        if (useridSV != null) {
            thongTinSV(useridSV);
        }
        if (useridGV != null) {
            thongTinGV(useridGV);
        }
    }
    private void thongTinGV(final String iduser) {
        ReadDataGV.getInstance().ReadGV(iduser, new ReadDataGV.IreadDataGV() {
            @Override
            public void onImageNull(GV gv) {
                HinhDaiDien.setImageResource(R.drawable.no_person);
                HoTen.setText(gv.getHo_Ten());
                docTinNhan(firebaseUser.getUid(), iduser, gv.getAnh_Dai_Dien());
            }
            @Override
            public void onImage(GV gv) {
                HoTen.setText(gv.getHo_Ten());
                Glide.with(getApplicationContext()).asBitmap().load(gv.getAnh_Dai_Dien()).into(new SimpleTarget<Bitmap>(200, 200) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        resource = Bitmap.createScaledBitmap(resource, (int) (resource.getWidth() * 0.8), (int) (resource.getHeight() * 0.8), true);
                        HinhDaiDien.setImageBitmap(resource);
                    }
                });
                docTinNhan(firebaseUser.getUid(), iduser, gv.getAnh_Dai_Dien());
            }
            @Override
            public void onSuccess(com.example.Model.GV gv) {

            }
        });
    }

    private void thongTinSV(final String iduser) {
        ReadDataSV.getInstance().ReadSV(iduser, new ReadDataSV.IreadDataSV() {
            @Override
            public void onImage(SV sv) {
                HoTen.setText(sv.getHo_Ten());
                Glide.with(getApplicationContext()).asBitmap().load(sv.getAnh_Dai_Dien()).into(new SimpleTarget<Bitmap>(200, 200) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        resource = Bitmap.createScaledBitmap(resource, (int) (resource.getWidth() * 0.8), (int) (resource.getHeight() * 0.8), true);
                        HinhDaiDien.setImageBitmap(resource);
                    }
                });
                docTinNhan(firebaseUser.getUid(), iduser, sv.getAnh_Dai_Dien());
            }

            @Override
            public void onImageNull(SV sv) {
                HinhDaiDien.setImageResource(R.drawable.no_person);
                HoTen.setText(sv.getHo_Ten());
                docTinNhan(firebaseUser.getUid(), iduser, sv.getAnh_Dai_Dien());
            }
            @Override
            public void onSuccess(SV sv) {

            }
        });


    }

    private void docTinNhan(final String myid, final String userid, final String anh_dai_dien) {

        ReadMessage.getInstance().ReadMessageRealTime(myid, userid, new ReadMessage.IreadMessage() {
            @Override
            public void DataReadMessage(List<Chats> chats1) {
                messageAdapter = new MessageAdapter(MessageActivity.this, chats1, anh_dai_dien);
                recyclerView.setAdapter(messageAdapter);
            }
        });
    }

    private void sendMessage(String sender, final String receiver, String message, String tokenUserId) {
        SendMessage.getInstance().SendMessageRealTime(sender, receiver, message, new SendMessage.IsendMessage() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
    }


}
