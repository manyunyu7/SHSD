package com.senjapagi.shsd;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.senjapagi.shsd.Adapter.adapterNilaiMentoring;
import com.senjapagi.shsd.Services.CLIENT_API;


import org.json.JSONObject;

import java.util.ArrayList;

public class mentee_lihat_nilai extends AppCompatActivity {
    RelativeLayout rlShining;
    TextView tvNilaiShiningTeam,tvJudulShiningTeam;
    ArrayList<model_nilai_mentoring> data=new ArrayList<>();
    ArrayList<model_nilai_general> dataGeneral= new ArrayList<>();
    adapterNilaiMentoring adaptNilaiMentoring;
//    adapterNilaiGeneral adaptNilaiGeneral;
    RecyclerView rvNilaiMentoring;
    String id;
    String nama ;
    String nim,kelompok ;
    String jurusan ;
    String fakultas ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_lihat_nilai);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);}

        init();
        getSmallClass();
        findViewById(R.id.containerShiningTeam).setVisibility(View.GONE);

        findViewById(R.id.btn_refresh_lyt_error_internet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSmallClass();
            }
        });
        findViewById(R.id.containerShiningTeam).setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_transition_animation3));
        rvNilaiMentoring.setHasFixedSize(true);
        rvNilaiMentoring.setLayoutManager(new LinearLayoutManager(mentee_lihat_nilai.this,RecyclerView.VERTICAL,false));
    }



    private void getSmallClass(){
        findViewById(R.id.layout_error_internet).setVisibility(View.GONE);
        findViewById(R.id.progressloadingNilai).setVisibility(View.VISIBLE);
        AndroidNetworking.post(CLIENT_API.small_class_score)
                .addBodyParameter("nim", nim)
                .addBodyParameter("kelompok", kelompok)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        findViewById(R.id.containerShiningTeam).setVisibility(View.VISIBLE);
                        try {
                            for(int i=0;i<response.getJSONArray("data").length();i++){
                                data.add(new model_nilai_mentoring(
                                        response.optJSONArray("data").getJSONObject(i).optString("tipe"),
                                        response.optJSONArray("data").getJSONObject(i).optString("judul"),
                                        response.optJSONArray("data").getJSONObject(i).optString("score_1"),
                                        response.optJSONArray("data").getJSONObject(i).optString("score_2")
                                ));
                            }

                            String nilai_tubes=response.optJSONArray("shining_team").getJSONObject(0).optString("score_2");
                            String judul_tubes=response.optJSONArray("shining_team").getJSONObject(0).optString("judul");

                            if(nilai_tubes.equals("null")|| nilai_tubes.equals(null))
                                nilai_tubes="Belum Dinilai / Not Graded Yet";

                            if(judul_tubes.equals("null")||judul_tubes.equals(null)) {
                             judul_tubes=("Belum Mengupload Tubes");
                                nilai_tubes = "Belum Ada Nilai";
                            }

                            tvJudulShiningTeam.setText(judul_tubes);

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    rvNilaiMentoring.setAdapter(adaptNilaiMentoring);
                                    findViewById(R.id.layout_error_internet).setVisibility(View.GONE);
                                    findViewById(R.id.progressloadingNilai).setVisibility(View.GONE);
                                }
                            }, 1100);
                            tvNilaiShiningTeam.setText(nilai_tubes);
                            adaptNilaiMentoring = new adapterNilaiMentoring(data);


                        } catch (Exception e) {
                            Toast.makeText(mentee_lihat_nilai.this, "error "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        findViewById(R.id.layout_error_internet).setVisibility(View.VISIBLE);
                        SnackBarInternet();
                        findViewById(R.id.progressloadingNilai).setVisibility(View.GONE);
                    }
                });
    }

    private void init() {
        tvJudulShiningTeam=findViewById(R.id.tvTitleTubes);
        tvNilaiShiningTeam=findViewById(R.id.tvNilaiShiningTeam);
        rlShining=findViewById(R.id.rLShiningTeam);
        rvNilaiMentoring = findViewById(R.id.recyclerNilaiMentoring);
        nim = getIntent().getStringExtra(ConstantMentee.nim);
        id = getIntent().getStringExtra(ConstantMentee.mentee_id);
        nama = getIntent().getStringExtra(ConstantMentee.nama);
        nim = getIntent().getStringExtra(ConstantMentee.nim);
        jurusan = getIntent().getStringExtra(ConstantMentee.jurusan);
        fakultas = getIntent().getStringExtra(ConstantMentee.fakultas);
        kelompok = getIntent().getStringExtra(ConstantMentee.kelompok);
    }

    public void SnackBarInternet(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Gagal Terhubung Dengan Server");
        builder1.setCancelable(true);

        builder1.setNegativeButton(
                "Refresh",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getSmallClass();
                        dialog.cancel();
                    }
                });

        builder1.setPositiveButton(
                "Keluar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            finishAndRemoveTask();
                            System.exit(1);
                        }else{
                            finishAffinity();
                            System.exit(1);
                        }
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
