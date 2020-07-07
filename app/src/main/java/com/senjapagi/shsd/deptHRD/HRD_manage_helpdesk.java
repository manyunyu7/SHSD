package com.senjapagi.shsd.deptHRD;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.senjapagi.shsd.ActivityGeneral.MainActivity;
import com.senjapagi.shsd.AdapterModel.adapterWhatsapp;
import com.senjapagi.shsd.R;
import com.senjapagi.shsd.Services.CLIENT_API;
import com.senjapagi.shsd.AdapterModel.model_whatsapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HRD_manage_helpdesk extends AppCompatActivity {
    ArrayList<model_whatsapp> data=new ArrayList<>();
    adapterWhatsapp adapterWA;
    RecyclerView rvDataKontak;
    EditText etNama,etKontak;
    SweetAlertDialog pDialog,changeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_contact);

        findViewById(R.id.lyt_add_helpdesk).setVisibility(View.GONE);

        etNama=findViewById(R.id.et_title_kontak);
        etKontak=findViewById(R.id.et_nomor_kontak);

        rvDataKontak=findViewById(R.id.recyclerDataKontak);
        rvDataKontak.setHasFixedSize(true);
        rvDataKontak.setLayoutManager(new LinearLayoutManager(HRD_manage_helpdesk.this,RecyclerView.VERTICAL,false));
        getContact();

        findViewById(R.id.btn_add_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.lyt_add_helpdesk).setVisibility(View.VISIBLE);
                findViewById(R.id.lyt_add_helpdesk).setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.item_animation_falldown));

            }
        });
        findViewById(R.id.btn_cancel_kontak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.lyt_add_helpdesk).setVisibility(View.GONE);
                findViewById(R.id.lyt_add_helpdesk).setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.item_animation_fallup));
            }
        });
        findViewById(R.id.btn_upload_kontak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadKontak();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getContact();
    }

    private void getContact(){
        findViewById(R.id.lyt_error_internet).setVisibility(View.GONE);
        findViewById(R.id.progressloadingNilai).setVisibility(View.VISIBLE);
        AndroidNetworking.post(CLIENT_API.getKontak)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int aq=response.getJSONArray("kontak").length();
                            if(aq<1){
                                Toast.makeText(HRD_manage_helpdesk.this, "Kontak Helpdesk Masih Kosong, Silakan Untuk Menambah Kontak", Toast.LENGTH_SHORT).show();
                            }
                            for(int i=0;i<response.getJSONArray("kontak").length();i++){
                                data.add(new model_whatsapp(
                                        response.optJSONArray("kontak").getJSONObject(i).optString("id"),
                                        response.optJSONArray("kontak").getJSONObject(i).optString("nama"),
                                        response.optJSONArray("kontak").getJSONObject(i).optString("whatsapp")
                                ));
                            }

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    rvDataKontak.setAdapter(adapterWA);
                                    findViewById(R.id.lyt_reset_password).setVisibility(View.GONE);
                                    findViewById(R.id.progressloadingNilai).setVisibility(View.GONE);
                                }
                            }, 1100);
                            adapterWA = new adapterWhatsapp(data);


                        } catch (Exception e) {
                            Toast.makeText(HRD_manage_helpdesk.this, "error "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        findViewById(R.id.lyt_error_internet).setVisibility(View.VISIBLE);
                        SnackBarInternet();
                        findViewById(R.id.progressloadingNilai).setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void uploadKontak(){
        String nama = etNama.getText().toString();
        String kontak = etKontak.getText().toString();

        if(nama.equals(""))
            etNama.setError("Isi Kolom Ini Terlebih Dahulu");
        if(kontak.equals(""))
            etKontak.setError("Isi Kolom Ini Terlebih Dahulu");
        else{
            findViewById(R.id.animation_lootie_loading).setVisibility(View.VISIBLE);
            AndroidNetworking.post(CLIENT_API.insertKontak)
                    .addBodyParameter("nomor",kontak)
                    .addBodyParameter("nama",nama)
                    .addBodyParameter("",nama)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            findViewById(R.id.animation_lootie_loading).setVisibility(View.GONE);
                            try {
                                String status = response.getJSONArray("insert_status").getJSONObject(0).getString("status_insert");
                                if (status.equals("berhasil")) {
                                    data.clear();
                                    Toast.makeText(HRD_manage_helpdesk.this, "Berhasil Upload Data", Toast.LENGTH_SHORT).show();
                                    pDialog = new SweetAlertDialog(HRD_manage_helpdesk.this, SweetAlertDialog.SUCCESS_TYPE);
                                    pDialog.setTitleText("Berhasil Tambah Kontak Helpdesk");
                                    pDialog.setCancelable(false);
                                    pDialog.setConfirmText("Alhamdulillah");
                                    pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            pDialog.hide();
                                            findViewById(R.id.lyt_add_helpdesk).setVisibility(View.GONE);
                                            findViewById(R.id.lyt_add_helpdesk).setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.item_animation_fallup));
                                        }
                                    });
                                    pDialog.show();
                                    getContact();
                                } else {
                                    Toast.makeText(HRD_manage_helpdesk.this, "Gagal Terhubung Dengan Server" +
                                            " Silakan Coba Lagi", Toast.LENGTH_SHORT).show();

                                    pDialog = new SweetAlertDialog(HRD_manage_helpdesk.this, SweetAlertDialog.WARNING_TYPE);
                                    pDialog.setTitleText("Gagal Tambah Kontak Helpdesk");
                                    pDialog.setContentText("Silakan Coba Kembali,Pastikan Format Sudah Sesuai !");
                                    pDialog.setCancelable(true);
                                    pDialog.setConfirmText("OK");
                                    pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            pDialog.hide();
                                            findViewById(R.id.lyt_add_helpdesk).setVisibility(View.GONE);
                                            findViewById(R.id.lyt_add_helpdesk).setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.item_animation_fallup));
                                        }
                                    });
                                    pDialog.show();

                                }
                            } catch (JSONException e) {
                                pDialog = new SweetAlertDialog(HRD_manage_helpdesk.this, SweetAlertDialog.WARNING_TYPE);
                                pDialog.setTitleText("Gagal Tambah Kontak Helpdesk");
                                pDialog.setContentText("Silakan Coba Kembali dan Pastikan Format Sudah Sesuai !");
                                pDialog.setCancelable(true);
                                pDialog.setConfirmText("OK");
                                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        pDialog.hide();
                                        findViewById(R.id.lyt_add_helpdesk).setVisibility(View.GONE);
                                        findViewById(R.id.lyt_add_helpdesk).setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.item_animation_fallup));
                                    }
                                });
                                pDialog.show();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            findViewById(R.id.animation_lootie_loading).setVisibility(View.GONE);
                            pDialog = new SweetAlertDialog(HRD_manage_helpdesk.this, SweetAlertDialog.WARNING_TYPE);
                            pDialog.setTitleText("Gagal Tambah Kontak Helpdesk");
                            pDialog.setContentText("Periksa Koneksi Internet Anda atau Coba Lagi Nanti");
                            pDialog.setCancelable(true);
                            pDialog.setConfirmText("OK");
                            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    pDialog.hide();
                                    findViewById(R.id.lyt_add_helpdesk).setVisibility(View.GONE);
                                    findViewById(R.id.lyt_add_helpdesk).setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.item_animation_fallup));
                                }
                            });
                            pDialog.show();
                        }
                    });
        }
    }

    public void SnackBarInternet(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(HRD_manage_helpdesk.this);
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
