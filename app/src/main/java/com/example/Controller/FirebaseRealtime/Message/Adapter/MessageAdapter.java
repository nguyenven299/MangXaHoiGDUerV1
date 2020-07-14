package com.example.Controller.FirebaseRealtime.Message.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Module.Chats;
import com.example.mxh_gdu3.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private Context context;
    private List<Chats> chats;
    private String imageurl;
    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    public MessageAdapter(Context context, List<Chats> chats, String imageurl) {
        this.context = context;
        this.chats = chats;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_TYPE_RIGHT)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(chats.get(position).getSender().equals(firebaseUser.getUid()))
        {
            return MSG_TYPE_RIGHT;
        }
        else
        {
            return MSG_TYPE_LEFT;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Chats chats1 =chats.get(position);
        try
        {
            holder.show_message.setText(chats1.getMessage());
        }
        catch (Exception e)
        {
            Log.d("loiMessage", "onBindViewHolder: "+e.getMessage());
        }
        if (imageurl.equals("default")) {
            holder.HinhDaiDien.setImageResource(R.drawable.no_person);
        } else {
            Glide.with(getApplicationContext()).load(imageurl).into(holder.HinhDaiDien);
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView show_message;
        public ImageView HinhDaiDien;
        public ViewHolder(View itemView)
        {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            HinhDaiDien = itemView.findViewById(R.id.HinhDaiDien);
        }
    }
}
