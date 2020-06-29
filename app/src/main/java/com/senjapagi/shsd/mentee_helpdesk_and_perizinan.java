package com.senjapagi.shsd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.senjapagi.shsd.Services.CLIENT_API;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class mentee_helpdesk_and_perizinan extends AppCompatActivity {

    String nim, password, fakultas, id, jurusan, nama;
    EditText etKomplain, etNama, etJurusan, etNim;
    String initNama, initNim, initFakultas;
    ArrayList<String> kontak = new ArrayList<>();
    Spinner daftarKontak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_helpdesk_and_perizinan);

        nim = getIntent().getStringExtra(ConstantMentee.nim);
        id = getIntent().getStringExtra(ConstantMentee.mentee_id);
        password = getIntent().getStringExtra(ConstantMentee.password);
        fakultas = getIntent().getStringExtra(ConstantMentee.fakultas);
        jurusan = getIntent().getStringExtra(ConstantMentee.jurusan);
        nama = getIntent().getStringExtra(ConstantMentee.nama);

        daftarKontak = findViewById(R.id.spinner_kontak);
        etKomplain = findViewById(R.id.et_komplain);
        etNim = findViewById(R.id.et_nim);
        etNama = findViewById(R.id.et_nama);
        etJurusan = findViewById(R.id.et_fakultas);

        initFakultas = getIntent().getStringExtra(ConstantMentee.jurusan);
        initNim = getIntent().getStringExtra(ConstantMentee.nim);
        initNama = getIntent().getStringExtra(ConstantMentee.nama);

        etJurusan.setText(initFakultas);
        etNim.setText(initNim);
        etNama.setText(initNama);

        getAgenda();
        findViewById(R.id.btn_kirim_pesan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickWhatsApp();
            }
        });
        findViewById(R.id.btn_batal_kirim_pesan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.lyt_chat_pengurus).setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.item_animation_fallup));
                findViewById(R.id.lyt_chat_pengurus).setVisibility(View.GONE);
                findViewById(R.id.btn_perizinan).setEnabled(true);
                findViewById(R.id.btn_helpdesk).setEnabled(true);
            }
        });

        findViewById(R.id.btn_perizinan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.lyt_redirect_sibima).setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.item_animation_falldown));
                findViewById(R.id.lyt_redirect_sibima).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_ok_redirect).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.lyt_redirect_sibima).setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.item_animation_fallup));
                        findViewById(R.id.lyt_redirect_sibima).setVisibility(View.GONE);
                        Handler b = new Handler();
                        b.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String url = CLIENT_API.website;
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
//
//                                Intent a = new Intent(mentee_helpdesk_and_perizinan.this,mentee_upload_izin.class);
//                                a.putExtra(Constant.nim,nim);
//                                a.putExtra(Constant.password,password);
//                                a.putExtra(Constant.fakultas,fakultas);
//                                a.putExtra(Constant.mentee_id,id);

                                startActivity(i);
                            }
                        }, 1200);

                    }
                });

            }
        });

        findViewById(R.id.btn_helpdesk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.lyt_chat_pengurus).setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.item_animation_falldown));
                findViewById(R.id.lyt_chat_pengurus).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_perizinan).setEnabled(false);
                findViewById(R.id.btn_helpdesk).setEnabled(false);
            }
        });
    }

    private void getAgenda() {
        findViewById(R.id.animation_lootie_loading).setVisibility(View.VISIBLE);
        AndroidNetworking.post(CLIENT_API.getKontak)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        findViewById(R.id.animation_lootie_loading).setVisibility(View.INVISIBLE);
                        try {
                            for (int b = 0; b <= response.getJSONArray("kontak").length(); b++) {
                                String whatsapp = response.getJSONArray("kontak").getJSONObject(b).getString("whatsapp");
                                kontak.add(b, whatsapp);
                                ArrayAdapter<String> adp1 = new ArrayAdapter<String>
                                        (mentee_helpdesk_and_perizinan.this, android.R.layout.simple_list_item_1, kontak);
                                daftarKontak.setAdapter(adp1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        findViewById(R.id.animation_lootie_loading).setVisibility(View.INVISIBLE);
                        Toast.makeText(mentee_helpdesk_and_perizinan.this, anError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

    }

    public void onClickWhatsApp() {
        PackageManager pm = getPackageManager();
        try {
            PackageManager packageManager = getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);
            String message = "Assalamualaikum kak , Nama Saya " + etNama.getText() + " " + etNim.getText() + " dari Fakultas " + etJurusan.getText() + " \n" +
                    "Pertanyaan/Komplain :\n" +"-"+ etKomplain.getText().toString() + " Terima Kasih";
            String phone = daftarKontak.getSelectedItem().toString();
            try {
                String url = "https://api.whatsapp.com/send?phone=" + phone + "&text=" + URLEncoder.encode(message, "UTF-8");
                i.setPackage("com.whatsapp");
                i.setData(Uri.parse(url));
                if (i.resolveActivity(packageManager) != null) {
                    startActivity(i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}