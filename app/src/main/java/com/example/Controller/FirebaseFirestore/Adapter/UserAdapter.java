package com.example.Controller.FirebaseFirestore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Module.Users;
import com.example.View.MessageActivity;
import com.example.mxh_gdu3.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private List<Users> users;

    public UserAdapter(Context context, List<Users> users) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        final Users user = users.get(position);
        try {
            holder.HoTen.setText(user.getHo_Ten());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            if(user.getAnh_Dai_Dien().equals("default"))
            {
                holder.AnhDaiDien.setImageResource(R.drawable.no_person);
            }
            else
            {
                Glide.with(context).load(user.getAnh_Dai_Dien()).into(holder.AnhDaiDien);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userid", user.getUid());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView HoTen;
        public ImageView AnhDaiDien;
        public String tokenUserId;

        public ViewHolder(View itemView) {
            super(itemView);
            HoTen = itemView.findViewById(R.id.textViewHoTen);
            AnhDaiDien = itemView.findViewById(R.id.AnhDaiDien);

        }
    }
}
