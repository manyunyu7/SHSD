package com.senjapagi.shsd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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

public class mentee_helpdesk extends AppCompatActivity {

    String komplain,nama,nim,fakultas;
    EditText etKomplain,etNama,etJurusan,etNim;
    String initNama,initNim,initFakultas;
    ArrayList<String> kontak=new ArrayList<>();
    Spinner daftarKontak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_helpdesk);
        daftarKontak=findViewById(R.id.spinner_kontak);
        etKomplain=findViewById(R.id.et_komplain);
        etNim=findViewById(R.id.et_nim);
        etNama=findViewById(R.id.et_nama);
        etJurusan=findViewById(R.id.et_fakultas);

        initFakultas=getIntent().getStringExtra(ConstantMentee.jurusan);
        initNim=getIntent().getStringExtra(ConstantMentee.nim);
        initNama=getIntent().getStringExtra(ConstantMentee.nama);

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
                mentee_helpdesk.super.onBackPressed();
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
                                            (mentee_helpdesk.this, android.R.layout.simple_list_item_1, kontak);
                                    daftarKontak.setAdapter(adp1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        findViewById(R.id.animation_lootie_loading).setVisibility(View.INVISIBLE);
                        Toast.makeText(mentee_helpdesk.this, anError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

    }

    public void onClickWhatsApp() {
        PackageManager pm=getPackageManager();
        try {
            PackageManager packageManager = getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);
            String message = "Assalamualaikum kak , Nama Saya "+etNama.getText()+" "+etNim.getText()+ " dari Fakultas "+etJurusan.getText() +" \n"+
                  "Pertanyaan/Komplain :\n"+ etKomplain.getText().toString()+" Terima Kasih";
            String phone=daftarKontak.getSelectedItem().toString();
            try {
                String url = "https://api.whatsapp.com/send?phone="+ phone +"&text=" + URLEncoder.encode(message, "UTF-8");
                i.setPackage("com.whatsapp");
                i.setData(Uri.parse(url));
                if (i.resolveActivity(packageManager) != null) {
                   startActivity(i);
                }
            } catch (Exception e){
                e.printStackTrace();}} catch (Exception e) {
            e.printStackTrace();
        }
    }

}