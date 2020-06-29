package com.senjapagi.shsd;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;


public class mentee_upload_izin extends AppCompatActivity {
    String idMentee;
    String fakultas;
    Spinner agenda;
    EditText etDetail;
    ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_upload_izin);

        fakultas = getIntent().getStringExtra(ConstantMentee.fakultas);
        idMentee = getIntent().getStringExtra(ConstantMentee.mentee_id);

//        String bkre=agenda.getSelectedItem().toString();
//        String[] kategori=bkre.split("-");
//        String id_agenda=kategori[0];
//        etDetail=findViewById(R.id.et_detail_izin);
//        String detail=etDetail.getText().toString();

        agenda=findViewById(R.id.spinner_agenda);
        Toast.makeText(this, "Fakultas :"+fakultas, Toast.LENGTH_SHORT).show();
        getAgenda();
        findViewById(R.id.btn_batal_upload_izin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mentee_upload_izin.super.onBackPressed();
            }
        });
    }


    private void getAgenda() {
        findViewById(R.id.animation_lootie_loading).setVisibility(View.VISIBLE);
        AndroidNetworking.post(CLIENT_API.getAgenda)
                .addBodyParameter("fakultas", fakultas)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        findViewById(R.id.animation_lootie_loading).setVisibility(View.INVISIBLE);
                        try {
                            if (response.getJSONArray("agenda").length() == 0) {
                                findViewById(R.id.container_empty_agenda).setVisibility(View.VISIBLE);
                                findViewById(R.id.container_form_izin).setVisibility(View.GONE);
                            }else {
                                findViewById(R.id.container_empty_agenda).setVisibility(View.GONE);
                                findViewById(R.id.container_form_izin).setVisibility(View.VISIBLE);
                                for (int b = 0; b <= response.getJSONArray("agenda").length(); b++) {
                                    String judul_agenda = response.getJSONArray("agenda").getJSONObject(b).getString("judul");
                                    String id = response.getJSONArray("agenda").getJSONObject(b).getString("id_agenda");
                                    Toast.makeText(mentee_upload_izin.this, "Judul :" + judul_agenda, Toast.LENGTH_SHORT).show();
                                    list.add(b, id+"-"+judul_agenda);
                                    ArrayAdapter<String> adp1 = new ArrayAdapter<String>
                                            (mentee_upload_izin.this, android.R.layout.simple_list_item_1, list);
                                    agenda.setAdapter(adp1);
                                }
                                Toast.makeText(mentee_upload_izin.this, "Panjang Array List : " + list.size(), Toast.LENGTH_SHORT).show();
                             }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        findViewById(R.id.animation_lootie_loading).setVisibility(View.INVISIBLE);
                        Toast.makeText(mentee_upload_izin.this, anError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

    }

    }
