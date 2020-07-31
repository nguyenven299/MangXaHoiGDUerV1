package com.example.Controller.FirebaseRealtime.Chat;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.Model.Chats;
import com.example.View.Activity.MessageActivity;
import com.example.View.Adapter.MessageAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadMessage {
    public static ReadMessage instance;
    private List<Chats> chats;
    public static ReadMessage getInstance() {
        if (instance == null)
            instance = new ReadMessage();
        return instance;
    }

    public interface IreadMessage {
        void DataReadMessage ( List<Chats> chats1);
    }

    public void ReadMessageRealTime(final String myid, final String userid, final IreadMessage ireadMessage) {
        chats = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
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
                    ireadMessage.DataReadMessage(chats);
//                    messageAdapter = new MessageAdapter(MessageActivity.this, chats, anh_dai_dien);
//                    recyclerView.setAdapter(messageAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
