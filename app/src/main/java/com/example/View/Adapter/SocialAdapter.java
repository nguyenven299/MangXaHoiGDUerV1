package com.example.View.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.Controller.FirebaseFirestore.CheckAccGVExist;
import com.example.Controller.FirebaseFirestore.ReadDataGV;
import com.example.Model.GV;
import com.example.Model.Social;
import com.example.View.Activity.UpdateNotificationActivity;
import com.example.mxh_gdu3.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.ViewHolder> {
    private int resource;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("Thong_Bao");
    private GV gv;
    private ProgressBar progressBar;
    private ListenerRegistration listenerRegistration;
    private StorageReference storageReference;
    private List<Social> socials;
    private Context context;

    public SocialAdapter(List<Social> social, Context context) {
        this.socials = social;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new SocialAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d("Ten", "onBindViewHolder: ");
        try {
            CheckAccGVExist.getInstance().CheckAccGV(firebaseUser.getUid(), new CheckAccGVExist.IcheckAccGVExist() {
                @Override
                public void onExist(String Uid) {
                    String UidSocial = socials.get(position).getUid();
                    Log.d("UidSocial", "onExist: " + UidSocial);
                    if (firebaseUser.getUid().equals(UidSocial)) {
                        holder.buttonXoa.setVisibility(View.VISIBLE);
                        holder.buttonXoa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Xóa Thông Báo");
                                builder.setMessage("Bạn Muốn Xóa Thông Báo?");
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
                                        databaseReference.child(socials.get(position).getKey()).removeValue();
                                        if (socials.get(position).getHinh_Thong_Bao().equals("default")) {
                                            Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                                        } else {
                                            storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(socials.get(position).getHinh_Thong_Bao());
                                            storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(getApplicationContext(), "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getApplicationContext(), "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();


                            }
                        });


                        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                Toast.makeText(context, "Sửa Thông Báo", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, UpdateNotificationActivity.class);
                                intent.putExtra("keySocial", socials.get(position).getKey());
                                context.startActivity(intent);
//                                            Intent intent = new Intent(context, UpdateNotificationActivity.class);
//                                            context.startActivity(intent);
                                return false;
                            }
                        });
                    } else if (!Uid.equals(UidSocial)) {
                        holder.buttonXoa.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onExistGV(GV gv) {

                }
            });

            if (socials.get(position).getHinh_Thong_Bao() != null) {
//                Glide.with( context ).load( contacts.getPicture() ).apply( RequestOptions.signatureOf( new CenterCrop() ) ).into( imageViewAvatar );
                Glide.with(getApplicationContext()).load(socials.get(position).getHinh_Thong_Bao()).into(holder.imageViewThongBao);
                holder.imageViewThongBao.setVisibility(View.VISIBLE);
            }
            holder.textViewNoiDungTHongBao.setText(socials.get(position).getThong_Bao());
            Log.d("ThongBao", "onBindViewHolder: " + socials.get(position).getThong_Bao());
            holder.textViewThoiGian.setText(socials.get(position).getThoi_Gian());
            ReadDataGV.getInstance().ReadGV(socials.get(position).getUid(), new ReadDataGV.IreadDataGV() {
                @Override
                public void onImage(GV gv) {
                    holder.textViewHoTen.setText(gv.getHo_Ten());
                    Glide.with(getApplicationContext()).asBitmap().load(gv.getAnh_Dai_Dien()).into(new SimpleTarget<Bitmap>(200, 200) {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            resource = Bitmap.createScaledBitmap(resource, (int) (resource.getWidth() * 0.8), (int) (resource.getHeight() * 0.8), true);
                            holder.imageViewHinhDaiDien.setImageBitmap(resource);
                        }
                    });
                }

                @Override
                public void onImageNull(GV gv) {
                    holder.imageViewHinhDaiDien.setImageResource(R.drawable.no_person);
                    holder.textViewHoTen.setText(gv.getHo_Ten());
                }

                @Override
                public void onSuccess(com.example.Model.GV gv) {

                }
            });
            if (holder.textViewHoTen.getText().toString() != null && holder.textViewNoiDungTHongBao.getText().toString() != null) {
                progressBar.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.d("vcl", "getView: " + e);
        }

    }

    @Override
    public int getItemCount() {
        return socials.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewHinhDaiDien;
        public PhotoView imageViewThongBao;
        public TextView textViewHoTen, textViewThoiGian, textViewNoiDungTHongBao;
        public Button buttonXoa;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewThongBao = itemView.findViewById(R.id.imageViewThongBao);
            textViewHoTen = itemView.findViewById(R.id.textViewHoTen);
            textViewThoiGian = itemView.findViewById(R.id.textViewThoiGian);
            textViewNoiDungTHongBao = itemView.findViewById(R.id.textViewNoiDungThongBao);
            imageViewHinhDaiDien = itemView.findViewById(R.id.imageViewHinhDaiDien);
            buttonXoa = itemView.findViewById(R.id.buttonXoa);

        }
    }
}
