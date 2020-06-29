package com.senjapagi.shsd;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.senjapagi.shsd.ActivityGeneral.MainActivity;
import com.senjapagi.shsd.Adapter.adapterKelompok;
import com.senjapagi.shsd.Services.CLIENT_API;

import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class mentor_intro_kelompok extends AppCompatActivity {
    ArrayList<model_kelompok> data = new ArrayList<>();
    com.senjapagi.shsd.Adapter.adapterKelompok adapterKelompok;
    RecyclerView rvDataKelompok;
    SweetAlertDialog pDialog, changeDialog;
    String nimMentor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_intro_kelompok);



        nimMentor=getIntent().getStringExtra(ConstantMentor.nim);

        rvDataKelompok = findViewById(R.id.recyclerDataKelompok);
        rvDataKelompok.setHasFixedSize(true);
        rvDataKelompok.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        getContact();

        findViewById(R.id.btn_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mentor_intro_kelompok.super.onBackPressed();
            }
        });

    }


    private void getContact() {
        Toast.makeText(this, nimMentor, Toast.LENGTH_SHORT).show();
        findViewById(R.id.layout_error_internet).setVisibility(View.GONE);
        findViewById(R.id.progressloadingNilai).setVisibility(View.VISIBLE);
        AndroidNetworking.post(CLIENT_API.getKelompok)
                .setPriority(Priority.HIGH)
                .addBodyParameter("nim",nimMentor)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int aa = response.getJSONArray("kelompok").length();
                            if(aa<1){
                                Toast.makeText(mentor_intro_kelompok.this, "Anda Belum Mendapat Kelompok", Toast.LENGTH_SHORT).show();
                            }else {
                                for (int i = 0; i < response.getJSONArray("kelompok").length(); i++) {
                                    data.add(new model_kelompok(
                                            response.getJSONArray("kelompok").getJSONObject(i).optString("id"),
                                            response.getJSONArray("kelompok").getJSONObject(i).optString("kode_kelompok"),
                                            response.getJSONArray("kelompok").getJSONObject(i).optString("nama_mentor"),
                                            response.getJSONArray("kelompok").getJSONObject(i).optString("line_id"),
                                            response.getJSONArray("kelompok").getJSONObject(i).optString("no_telp"),
                                            response.getJSONArray("kelompok").getJSONObject(i).optString("id_kelompok")
                                    ));
                                }

                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        rvDataKelompok.setAdapter(adapterKelompok);
                                        findViewById(R.id.layout_error_internet).setVisibility(View.GONE);
                                        findViewById(R.id.progressloadingNilai).setVisibility(View.GONE);
                                    }
                                }, 1100);
                                adapterKelompok = new adapterKelompok(data);
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Terjadi Kesalahan : " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
    @Override
    public void onBackPressed() {
     super.onBackPressed();
    }


    public void SnackBarInternet(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
        builder1.setMessage("Gagal Terhubung Dengan Server");
        builder1.setCancelable(true);

        builder1.setNegativeButton(
                "Refresh",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getContact();
                        dialog.cancel();
                    }
                });

        builder1.setPositiveButton(
                "Keluar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        Intent a = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(a);
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
