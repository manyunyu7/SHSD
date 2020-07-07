package com.senjapagi.shsd.AdapterModel;

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
import com.senjapagi.shsd.mentor_lihat_kelompok;

import java.util.ArrayList;

public class adapterKelompok extends RecyclerView.Adapter<adapterKelompok.holder_nilai>  {
    ArrayList<model_kelompok> data;
    Context mContext;
    public adapterKelompok(ArrayList<model_kelompok> data) {
        this.data = data;
    }
    @NonNull
    @Override
    public holder_nilai onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new holder_nilai(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kelompok,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull final holder_nilai holder, final int position) {

        holder.tvID.setText(data.get(position).getId());
        holder.tvKelompok.setText(data.get(position).getKode());
        holder.idGrup.setText(data.get(position).getIdGrup());
        holder.nama.setText(data.get(position).getNama());
        holder.tvKelompok.setText(data.get(position).getKode());

        final String nama,kontak,line;
        nama=data.get(position).getNama();
        kontak=data.get(position).getKontak();
        line=data.get(position).getLine();

        holder.scoreList.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "Nama : "+nama, Toast.LENGTH_SHORT).show();
//                Toast.makeText(mContext, "Kontak: "+kontak, Toast.LENGTH_SHORT).show();
//                Toast.makeText(mContext, "Line: "+line, Toast.LENGTH_SHORT).show();

               Intent a = new Intent(mContext, mentor_lihat_kelompok.class);
               a.putExtra("id",holder.tvID.getText().toString());
               a.putExtra("kode",data.get(position).getKode());
               a.putExtra("nama",nama);
               a.putExtra("line",line);
               a.putExtra("kontak",kontak);
               a.putExtra("id_grup",data.get(position).getIdGrup());

               mContext.startActivity(a);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class holder_nilai extends RecyclerView.ViewHolder {

        TextView tvKelompok,tvID,nama,line,kontak,idGrup;
        CardView containerCard;
        RelativeLayout scoreList;
        LinearLayout score1Container ;

        public holder_nilai(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            scoreList=itemView.findViewById(R.id.rlScoreList);
            tvKelompok=itemView.findViewById(R.id.tvKodeKelompok);
            tvID=itemView.findViewById(R.id.tvIdKelompok);
            nama=itemView.findViewById(R.id.tvNamaMentorKel);
            line=itemView.findViewById(R.id.tvLineMentorKel);
            kontak=itemView.findViewById(R.id.tvKontakMentorKel);
            idGrup=itemView.findViewById(R.id.tvIdGrupKel);
        }
    }
}
