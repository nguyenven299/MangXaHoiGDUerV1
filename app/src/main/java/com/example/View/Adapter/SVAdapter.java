package com.example.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.Model.SV;
import com.example.View.Activity.MessageActivity;
import com.example.mxh_gdu3.R;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class SVAdapter extends RecyclerView.Adapter<SVAdapter.ViewHolder> {
    private Context context;
    private List<SV> SVS;

    public SVAdapter(Context context, List<SV> users) {
        this.SVS = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new SVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SV SV = SVS.get(position);
        try {
            holder.username.setText(SV.getHo_Ten());

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (SV.getAnh_Dai_Dien().equals("default")) {
                holder.profile_image.setImageResource(R.drawable.no_person);
            } else {
                Glide.with(getApplicationContext()).load(SV.getAnh_Dai_Dien()).into(holder.profile_image);
            }
        } catch (Exception e) {
            Log.d("url image", "onBindViewHolder: " +e.getMessage());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("useridSV", SV.getUid());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return SVS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public ImageView profile_image;
        public String tokenUserId;
        public ViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.textViewHoTen);
            profile_image = itemView.findViewById(R.id.AnhDaiDien);

        }
    }
}

