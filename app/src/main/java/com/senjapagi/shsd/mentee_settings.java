package com.senjapagi.shsd;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.senjapagi.shsd.ActivityGeneral.MainActivity;
import com.senjapagi.shsd.Services.CLIENT_API;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class mentee_settings extends AppCompatActivity {

    EditText EtUpdateline, EtUpdatekontak, etNewPassword, etVerifyNewPassword;
    TextView nama, nim, fakultas, jurusan;
    TextView tvCurrentMenteeLine, tvCurrentMenteePhone;
    SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_settings);
        findViewById(R.id.animation_lootie_loading).setVisibility(View.VISIBLE); //SHOWING LOADING INDICATOR
        EtUpdateline = findViewById(R.id.et_line);
        EtUpdatekontak = findViewById(R.id.et_no_hp);
        nama = findViewById(R.id.tv_nama_mentee);
        nim = findViewById(R.id.tv_nim_mentee);
        fakultas = findViewById(R.id.tv_fakultas_mentee);
        jurusan = findViewById(R.id.tv_jurusan_mentee);
        tvCurrentMenteeLine = findViewById(R.id.tv_current_mentee_line);
        tvCurrentMenteePhone = findViewById(R.id.tv_current_mentee_phone);

        etNewPassword = findViewById(R.id.tv_new_password);
        etVerifyNewPassword = findViewById(R.id.tv_new_password_again);


        getBeforeData();

        findViewById(R.id.btn_save_basic_mentee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(EtUpdatekontak.getText().toString().equals(""))
//                   EtUpdatekontak.setError("Lengkapi Data Terlebih Dahulu");
//                if(EtUpdateline.getText().toString().equals(""))
//                    EtUpdateline.setError("Lengkapi Data Terlebih Dahulu");
                updateData();
            }
        });

        findViewById(R.id.btn_cancel_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.lyt_change_password).setVisibility(View.GONE);
            }
        });

        findViewById(R.id.btn_start_change_pass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.lyt_change_password).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.btn_save_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });

        findViewById(R.id.btn_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mentee_settings.super.onBackPressed();
            }
        });
    }



    private void onBackPresseds(){
        super.onBackPressed();
    }

    private void updateData(){
        findViewById(R.id.animation_lootie_loading).setVisibility(View.VISIBLE); //SHOWING LOADING INDICATOR
        AndroidNetworking.post(CLIENT_API.updateBasicMentee)
                .addBodyParameter("kontak",EtUpdatekontak.getText().toString())
                .addBodyParameter("line",EtUpdateline.getText().toString())
                .addBodyParameter("nim",getIntent().getStringExtra(ConstantMentee.nim))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        findViewById(R.id.animation_lootie_loading).setVisibility(View.GONE); //HIDING LOADING INDICATOR
                        try {
                            if(response.getJSONArray("update_status").getJSONObject(0).getString("status_update").equals("berhasil")){
                                Toast.makeText(mentee_settings.this, "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                                tvCurrentMenteePhone.setText(response.getJSONArray("after").getJSONObject(0).getString("no_telp"));
                                tvCurrentMenteeLine.setText(response.getJSONArray("after").getJSONObject(0).getString("line_id"));
                            }else{
                                Toast.makeText(mentee_settings.this, "Gagal Mengupdate Data", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(mentee_settings.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        findViewById(R.id.animation_lootie_loading).setVisibility(View.GONE); //HIDING LOADING INDICATOR
                        Toast.makeText(mentee_settings.this, "Gagal Update Data, Connection Unstable Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void updatePassword(){
        String password,newPassword;
        password=etNewPassword.getText().toString();
        newPassword=etVerifyNewPassword.getText().toString();
        if(password.equals(newPassword)){
            if(etVerifyNewPassword.getText().toString().equals("")||etVerifyNewPassword.getText().toString().equals(""))
                etVerifyNewPassword.setError("Password Tidak Boleh Kosong");
            if(etNewPassword.getText().toString().equals("")||etNewPassword.getText().toString().equals(""))
                etNewPassword.setError("Password Tidak Boleh Kosong");
            if(etNewPassword.getText().toString().length()<6)
                etNewPassword.setError("Minimum 6 Karakter");
            else {
                findViewById(R.id.animation_lootie_loading).setVisibility(View.VISIBLE); //SHOWING LOADING INDICATOR
                AndroidNetworking.post(CLIENT_API.updatePasswordMentee)
                        .addBodyParameter("nim", getIntent().getStringExtra(ConstantMentee.nim))
                        .addBodyParameter("password", etVerifyNewPassword.getText().toString())
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                findViewById(R.id.animation_lootie_loading).setVisibility(View.GONE); //HIDING LOADING INDICATOR
                                try {
                                    String status=response.getJSONArray("update_status").getJSONObject(0).getString("status_update");
                                    if(status.equals("berhasil")){
                                        findViewById(R.id.lyt_change_password).setVisibility(View.GONE);
                                        Handler a = new Handler();
                                        a.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent a = new Intent(mentee_settings.this, MainActivity.class);
                                                Toast.makeText(mentee_settings.this, "Silahkan Login Kembali", Toast.LENGTH_SHORT).show();
                                                startActivity(a);
                                            }
                                        },1000);
                                    }else{
                                        Toast.makeText(mentee_settings.this, "Update Password Gagal/Failed", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(mentee_settings.this, e.getLocalizedMessage()+" Gagal Mengupdate Password / Failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                findViewById(R.id.animation_lootie_loading).setVisibility(View.GONE); //HIDING LOADING INDICATOR
                                Toast.makeText(mentee_settings.this, "Gagal Update Data, Connection Unstable Try Again Later", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }else{
            etNewPassword.setError("Sesuaikan Password");
            etVerifyNewPassword.setError("Sesuaikan Password");
        }

    }

    private void getBeforeData(){
        findViewById(R.id.animation_lootie_loading).setVisibility(View.VISIBLE); //SHOWING LOADING INDICATOR
        AndroidNetworking.post(CLIENT_API.getBasicMentee)
                .addBodyParameter("nim",getIntent().getStringExtra(ConstantMentee.nim))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        findViewById(R.id.animation_lootie_loading).setVisibility(View.GONE); //HIDING LOADING INDICATOR
                        try {
                            nama.setText(response.getJSONArray("before").getJSONObject(0).getString("nama"));
                            nim.setText(response.getJSONArray("before").getJSONObject(0).getString("nim"));
                            jurusan.setText(response.getJSONArray("before").getJSONObject(0).getString("jurusan"));
                            fakultas.setText(response.getJSONArray("before").getJSONObject(0).getString("fakultas"));
                            String line = response.getJSONArray("before").getJSONObject(0).getString("line");
                            String kontak = response.getJSONArray("before").getJSONObject(0).getString("kontak");

                            if(line.equals("")||line.equals(null)){
                                line = "-";
                            }

                            if(kontak.equals("")||kontak.equals(null)){
                                kontak="-";
                            }

                            tvCurrentMenteeLine.setText(line);
                            tvCurrentMenteePhone.setText(kontak);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        findViewById(R.id.animation_lootie_loading).setVisibility(View.GONE); //HIDING LOADING INDICATOR
                        Toast.makeText(mentee_settings.this, "Connection Unstable Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}