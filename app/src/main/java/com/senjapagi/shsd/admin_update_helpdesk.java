package com.senjapagi.shsd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.senjapagi.shsd.Services.CLIENT_API;

import org.json.JSONException;
import org.json.JSONObject;

public class admin_update_helpdesk extends AppCompatActivity {


    String nama,kontak,id;
    EditText etNama,etKontak;
    Intent a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_helpdesk);
        a = new Intent(admin_update_helpdesk.this,admin_manage_contact.class);
        String parameter = "";
        etNama=findViewById(R.id.et_title_helpdesk_update);
        etKontak=findViewById(R.id.et_nomor_kontak_update);


        id=getIntent().getStringExtra("id");
        kontak=getIntent().getStringExtra("nomor");
        nama=getIntent().getStringExtra("nama");

        etKontak.setText(kontak);
        etNama.setText(nama);

        findViewById(R.id.btn_cancel_update_helpdes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin_update_helpdesk.super.onBackPressed();
                finish();
            }
        });
        findViewById(R.id.btn_update_helpdes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDataHelpdesk();
            }
        });
        findViewById(R.id.btn_hapus_helpdesk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteHelpdesk();
            }
        });
    }

    private void deleteHelpdesk() {
        findViewById(R.id.animation_lootie_loading).setVisibility(View.VISIBLE); //SHOWING LOADING INDICATOR
        AndroidNetworking.post(CLIENT_API.updateHelpdesk)
                .addBodyParameter("id",id)
                .addBodyParameter("kontak",etKontak.getText().toString())
                .addBodyParameter("nama",etNama.getText().toString())
                .addBodyParameter("param","Delete")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        findViewById(R.id.animation_lootie_loading).setVisibility(View.GONE); //HIDING LOADING INDICATOR
                        try {
                            String b= response.getJSONArray("update_status").getJSONObject(0).getString("status_update");
                            if(b.equals("berhasil")){
                                Toast.makeText(admin_update_helpdesk.this, "Berhasil Hapus Data", Toast.LENGTH_SHORT).show();
                                admin_update_helpdesk.super.onBackPressed();
                                finish();
                            }else{
                                Toast.makeText(admin_update_helpdesk.this, "Gagal Menghapus Data / Failed", Toast.LENGTH_SHORT).show();
                                admin_update_helpdesk.super.onBackPressed();
                                finish();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(admin_update_helpdesk.this, "Gagal Menghapus Data / Failed", Toast.LENGTH_SHORT).show();
                            admin_update_helpdesk.super.onBackPressed();
                            finish();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        findViewById(R.id.animation_lootie_loading).setVisibility(View.GONE); //HIDING LOADING INDICATOR
                        Toast.makeText(admin_update_helpdesk.this, "Gagal Update Data, Connection Unstable Try Again Later", Toast.LENGTH_SHORT).show();
                        Intent a = new Intent(admin_update_helpdesk.this,admin_manage_contact.class);
                        admin_update_helpdesk.super.onBackPressed();
                        finish();
                    }
                });
    }

    private void updateDataHelpdesk(){
        findViewById(R.id.animation_lootie_loading).setVisibility(View.VISIBLE); //SHOWING LOADING INDICATOR
        AndroidNetworking.post(CLIENT_API.updateHelpdesk)
                .addBodyParameter("id",id)
                .addBodyParameter("kontak",etKontak.getText().toString())
                .addBodyParameter("nama",etNama.getText().toString())
                .addBodyParameter("param","")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        findViewById(R.id.animation_lootie_loading).setVisibility(View.GONE); //HIDING LOADING INDICATOR
                        try {
                            String b= response.getJSONArray("update_status").getJSONObject(0).getString("status_update");
                            if(b.equals("berhasil")){
                                Toast.makeText(admin_update_helpdesk.this, "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                                admin_update_helpdesk.super.onBackPressed();
                                finish();
                            }else{
                                Toast.makeText(admin_update_helpdesk.this, "Gagal Mengupdate Data / Failed", Toast.LENGTH_SHORT).show();
                               admin_update_helpdesk.super.onBackPressed();
                                finish();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(admin_update_helpdesk.this, "Gagal Mengupdate Data / Failed", Toast.LENGTH_SHORT).show();
                            admin_update_helpdesk.super.onBackPressed();
                            finish();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        findViewById(R.id.animation_lootie_loading).setVisibility(View.GONE); //HIDING LOADING INDICATOR
                        Toast.makeText(admin_update_helpdesk.this, "Gagal Update Data, Connection Unstable Try Again Later", Toast.LENGTH_SHORT).show();
                       admin_update_helpdesk.super.onBackPressed();
                    }
                });
    }
}