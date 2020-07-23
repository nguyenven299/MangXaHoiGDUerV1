package com.example.View.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
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
import com.example.View.Adapter.MessageAdapter;
import com.example.Model.Chats;
import com.example.Model.Users;
import com.example.mxh_gdu3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
        final DocumentReference docRef1 = firebaseFirestore.collection("GV").document(iduser);
        listenerRegistration = docRef1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    String Ten = snapshot.getString("Ho_Ten");
                    String Hinh_Dai_Dien = snapshot.getString("Anh_Dai_Dien");
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
                    docTinNhan(firebaseUser.getUid(), iduser, Hinh_Dai_Dien);

                }
            }
        });
    }

    private void thongTinSV(final String iduser) {
        Log.d("userid", "onCreate:1 " + iduser);
        final DocumentReference docRef1 = firebaseFirestore.collection("SV").document(iduser);
        listenerRegistration = docRef1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    String Ten = snapshot.getString("Ho_Ten");
                    String Hinh_Dai_Dien = snapshot.getString("Anh_Dai_Dien");
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
                    docTinNhan(firebaseUser.getUid(), iduser, Hinh_Dai_Dien);
                }
            }
        });
    }

    private void docTinNhan(final String myid, final String userid, final String anh_dai_dien) {
        chats = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chats.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Chats chat = dataSnapshot1.getValue(Chats.class);

                    if (chat.getReceiver().equals(myid)
                            && chat.getSender().equals(userid)
                            || chat.getReceiver().equals(userid)
                            && chat.getSender().equals(myid)) {
                        chats.add(chat);

                        Log.d("Thong bao", "onDataChange: thong bao ne");
                    }
                    messageAdapter = new MessageAdapter(MessageActivity.this, chats, anh_dai_dien);
                    recyclerView.setAdapter(messageAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage(String sender, final String receiver, String message, String tokenUserId) {
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("thoi_gian", currentDate);
        databaseReference.child("Chats").push().setValue(hashMap);

        final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("ChatList")
                .child(firebaseUser.getUid()).child(receiver);
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    databaseReference1.child("Uid").setValue(receiver);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("ChatListReceiver")
                .child(receiver).child(firebaseUser.getUid());
        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    databaseReference2.child("Uid").setValue(firebaseUser.getUid());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
