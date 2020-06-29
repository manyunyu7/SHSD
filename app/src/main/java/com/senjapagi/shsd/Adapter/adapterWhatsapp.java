package com.senjapagi.shsd.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.senjapagi.shsd.R;
import com.senjapagi.shsd.admin_update_helpdesk;
import com.senjapagi.shsd.model_whatsapp;

import java.util.ArrayList;

public class adapterWhatsapp extends RecyclerView.Adapter<adapterWhatsapp.holder_nilai>  {
    ArrayList<model_whatsapp> data;
    Context mContext;
    public adapterWhatsapp(ArrayList<model_whatsapp> data) {
        this.data = data;
    }
    @NonNull
    @Override
    public holder_nilai onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new holder_nilai(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_admin_whatsapp,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull final holder_nilai holder, int position) {

        holder.tvID.setText(data.get(position).getId());
        holder.tvNama.setText(data.get(position).getNama());
        holder.tvNomor.setText(data.get(position).getNomor());
        holder.scoreList.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent a = new Intent(mContext, admin_update_helpdesk.class);
               a.putExtra("id",holder.tvID.getText().toString());
               a.putExtra("nama",holder.tvNama.getText().toString());
               a.putExtra("nomor",holder.tvNomor.getText().toString());
               mContext.startActivity(a);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class holder_nilai extends RecyclerView.ViewHolder {

        TextView tvNomor,tvNama,tvID;
        CardView containerCard;
        RelativeLayout scoreList;
        LinearLayout score1Container ;

        public holder_nilai(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            scoreList=itemView.findViewById(R.id.rlScoreList);
            tvNama=itemView.findViewById(R.id.tvNamaPemilik);
            tvID=itemView.findViewById(R.id.tvIdWhatsapp);
            tvNomor=itemView.findViewById(R.id.tvNomorWhatsapp);
        }
    }
}
