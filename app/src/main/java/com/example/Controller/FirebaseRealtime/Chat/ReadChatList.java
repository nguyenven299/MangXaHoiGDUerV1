package com.example.Controller.FirebaseRealtime.Chat;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.Controller.FirebaseFirestore.CheckAccGVExist;
import com.example.Controller.FirebaseRealtime.User.ReadDataUserRealtime;
import com.example.Model.ChatList;
import com.example.Model.Users;
import com.example.View.Adapter.UserAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadChatList {
    public static ReadChatList instance;
    private DatabaseReference databaseReference;

    public static ReadChatList getInstance() {
        if (instance == null)
            instance = new ReadChatList();
        return instance;
    }

    public interface IreadChatList {
        void onSuccess(List<Users> usersList);

        void onFail(String Fail);
    }

    public void ReadUserChatList(final String uid, final IreadChatList ireadChatList) {
        databaseReference = FirebaseDatabase.getInstance().getReference("ChatList").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ChatList> stringList;
                stringList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatList chatList = snapshot.getValue(ChatList.class);
                    stringList.add(chatList);
                }
                ReadDataUserRealtime.getInstance().ReadDataUser(stringList, uid, new ReadDataUserRealtime.IreadDataUserRealtime() {
                    @Override
                    public void onSuccess(List<Users> usersList) {

//                            userAdapter = new UserAdapter(getContext(), usersList);
//                            recyclerView.setAdapter(userAdapter);
                        ireadChatList.onSuccess(usersList);
                    }

                    @Override
                    public void onFail(String Fail) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("ChatListReceiver").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final ChatList chatList = snapshot.getValue(ChatList.class);
                    Log.d("keyData", "onDataChange: " + chatList.getUid());
                    final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("ChatList")
                            .child(uid).child(chatList.getUid());
                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.exists()) {
                                databaseReference1.child("Uid").setValue(chatList.getUid());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
