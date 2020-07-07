package com.senjapagi.shsd.AdapterModel;

import android.content.Context;
import android.graphics.Color;
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

import java.util.ArrayList;

public class adapterNilaiMentoring extends RecyclerView.Adapter<adapterNilaiMentoring.holder_nilai>  {
    ArrayList<model_nilai_mentoring> data;
    Context mContext;
    public adapterNilaiMentoring(ArrayList<model_nilai_mentoring> data) {
        this.data = data;
    }
    @NonNull
    @Override
    public holder_nilai onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new holder_nilai(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mentee_nilai,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull holder_nilai holder, int position) {
        holder.tvTitleNilai.setText(data.get(position).getJudul());
        holder.tvNilaiKehadiran.setText(data.get(position).getNilai_kehadiran());
        holder.tvNilaiKultum.setText(data.get(position).getNilai_kultum());
        String type = data.get(position).getTipe();
        if(type.equals("general")){
            holder.containerCard.setCardBackgroundColor(Color.parseColor("#3282b8"));
            holder.score1Container.setVisibility(View.INVISIBLE);
            holder.txtLabelScore2.setText("Nilai : ");
            holder.scoreList.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));
        }else if(type.equals("small_class")){
            holder.containerCard.setCardBackgroundColor(Color.parseColor("#45B6FE"));
            holder.scoreList.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation2));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class holder_nilai extends RecyclerView.ViewHolder {

        TextView tvTitleNilai,tvNilaiKehadiran,tvNilaiKultum , txtLabelScore2;
        CardView containerCard;
        RelativeLayout scoreList;
        LinearLayout score1Container ;

        public holder_nilai(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            containerCard=itemView.findViewById(R.id.card_data);
            tvTitleNilai=itemView.findViewById(R.id.tvTitleNilai);
            tvNilaiKehadiran=itemView.findViewById(R.id.tvNilaiKehadiran);
            tvNilaiKultum=itemView.findViewById(R.id.tvNilaiKultum);
            scoreList=itemView.findViewById(R.id.rlScoreList);
            score1Container=itemView.findViewById(R.id.score1Container);
            txtLabelScore2=itemView.findViewById(R.id.txtLabelScore2);
        }
    }
}
