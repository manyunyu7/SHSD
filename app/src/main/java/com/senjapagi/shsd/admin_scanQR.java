package com.senjapagi.shsd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.senjapagi.shsd.Services.CLIENT_API;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class admin_scanQR extends AppCompatActivity {
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> listScanned = new ArrayList<String>();
    MaterialSpinner agenda;
    CodeScannerView scannerView;
    private CodeScanner mCodeScanner;
    SweetAlertDialog pDialog;
    TextView tvResultScan;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_scan_qr);
        agenda=findViewById(R.id.spinner_agenda);
        tvResultScan=findViewById(R.id.scan_result_name);
        scannerView = findViewById(R.id.scanner_view);

        scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        getAgenda();
        dialogCheckPermission();
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {

                admin_scanQR.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scanned(result.toString());
                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }



    private void dialogCheckPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(admin_scanQR.this, new String[]{android.Manifest.permission.CAMERA}, 50);
            Toast.makeText(this, "Aktifkan Permission Camera", Toast.LENGTH_SHORT).show();
        } else {
            //Do Nothing
        }
    }

    private void getAgenda() {
        findViewById(R.id.animation_lootie_loading).setVisibility(View.VISIBLE);
        AndroidNetworking.post(CLIENT_API.getAgendaGeneral)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        list.add("PILIH AGENDA");
                        findViewById(R.id.animation_lootie_loading).setVisibility(View.INVISIBLE);
                        try {
                            if (response.getJSONArray("agenda").length() == 0) {
                                Toast.makeText(admin_scanQR.this, "Belum Ada Agenda", Toast.LENGTH_SHORT).show();
                                ArrayAdapter<String> adp1 = new ArrayAdapter<String>
                                        (admin_scanQR.this, android.R.layout.simple_list_item_1, list);
                                agenda.setAdapter(adp1);
                            } else {
                                mCodeScanner.startPreview();
                                for (int b = 0; b <= response.getJSONArray("agenda").length(); b++) {
                                    String judul_agenda = response.getJSONArray("agenda").getJSONObject(b).getString("judul");
                                    String id = response.getJSONArray("agenda").getJSONObject(b).getString("id_agenda");
                                    String faculty = response.getJSONArray("agenda").getJSONObject(b).getString("fakultas");
                                    String deadline = response.getJSONArray("agenda").getJSONObject(b).getString("waktu_akhir");
                                    list.add(b+1, id+"-"+faculty + "-" + judul_agenda);
                                    ArrayAdapter<String> adp1 = new ArrayAdapter<String>
                                            (admin_scanQR.this, android.R.layout.simple_list_item_1, list);
                                    agenda.setAdapter(adp1);


                                }
                                Toast.makeText(admin_scanQR.this, "Panjang Array List : " + list.size(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                    @Override
                    public void onError(ANError anError) {
                        findViewById(R.id.animation_lootie_loading).setVisibility(View.INVISIBLE);
                        Toast.makeText(admin_scanQR.this, anError.getMessage(), Toast.LENGTH_LONG).show();
                        mCodeScanner.stopPreview();
                        Toast.makeText(admin_scanQR.this, "Periksa Koneksi Internet Anda, Silakan Coba Lagi Nanti", Toast.LENGTH_LONG).show();
                        Handler a = new Handler();
                        a.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                admin_scanQR.super.onBackPressed();
                            }
                        },700);
                    }
                });
    }

    private void scanPeserta(String mentee_id,String agenda_id){
        AndroidNetworking.post(CLIENT_API.insertPresensiGeneral)
                .setPriority(Priority.HIGH)
                .addBodyParameter("mentee_id",mentee_id)
                .addBodyParameter("agenda_id",agenda_id)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                           String status = response.getJSONArray("presensi_status").getJSONObject(0).getString("status");
                           String desc = response.getJSONArray("presensi_status").getJSONObject(0).getString("desc");
                           String waktu = response.getJSONArray("presensi_status").getJSONObject(0).getString("waktu");
                           String late = response.getJSONArray("presensi_status").getJSONObject(0).getString("late");
                           String nama = response.getJSONArray("presensi_status").getJSONObject(0).getString("nama");

                           if(status.equals("failed")){
                               if(desc.equals("duplicate_row"))
                               error("1");
                               if(desc.equals("wrong_fac"))
                               error("2");
                               if(desc.equals("mentee0"))
                               error("3");
                               if(desc.equals("insert_failed"))
                               error("4");

                           }else{
                               String  i = String.valueOf(listScanned.size()+1);
                               int io = listScanned.size();
                               listScanned.add(i+". " + nama +"-"+waktu+"\n");
                               tvResultScan.setText(tvResultScan.getText()+listScanned.get(io));
                               success();
                           }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        error("5");
                    }
                });
    }

    private void scanned(String result){
        if(agenda.getText().toString().equals("PILIH AGENDA")){
            error("0");
        }else{
            try {
                MediaPlayer.create(admin_scanQR.this, R.raw.beep).start();
//                              String idAgenda = dataScan.substring(dataScan.indexOf("("),dataScan.indexOf(")"));
//                              String idAgenda = "";
//                              String idAgenda = dataScan.split("[\\(\\)]")[1];
                Toast.makeText(admin_scanQR.this, agenda.getText().toString(), Toast.LENGTH_SHORT).show();
                String dataScan=String.valueOf(result);
                String[] res1 = dataScan.split("[-]" );
                String[] res2 = agenda.getText().toString().split("[-]" );
                String idMentee = res1[0];
                String idAgenda= res2[0];

                Toast.makeText(admin_scanQR.this, "ID MENTEE : "+idMentee, Toast.LENGTH_SHORT).show();
                Toast.makeText(admin_scanQR.this, "ID AGENDA : "+idAgenda, Toast.LENGTH_SHORT).show();
                scanPeserta(idMentee,idAgenda);

            }catch (Exception x){
                Toast.makeText(admin_scanQR.this, agenda.getText().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(admin_scanQR.this, "ERROR RESULT :\n"+x.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void success() {
        MediaPlayer.create(admin_scanQR.this, R.raw.success).start();
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.setTitleText("ALHAMDULILLAH");
        pDialog.setContentText("Presensi Berhasil Diinput Ke SIBIMA");
        pDialog.setConfirmText("OKE");
        pDialog.show();
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                pDialog.dismissWithAnimation();
            }
        });
    }

    private void error(String errorType){
        MediaPlayer.create(admin_scanQR.this, R.raw.error).start();
        String message ="";

        if(errorType.equals("0"))//Mentee Sudah Absensi
            message="Silakan Pilih Agenda Terlebih Dahulu";
        if(errorType.equals("1"))//Mentee Sudah Absensi
            message="Mentee Sudah Melakukan Absensi";
        if(errorType.equals("2"))//Fakultas Agenda Salah
            message="Fakultas Mentee Tidak Sesuai Dengan Agenda";
        if(errorType.equals("3"))//Mentee Tidak Ditemukan
            message="QR Code Mentee Tidak Ditemukan di Server";
        if(errorType.equals("4"))//Gagal Insert
            message="Gagal Terhubung Dengan Database";
        if(errorType.equals("5")) //Gagal Terhubung Dengan Server
            message="Gagal Terhubung Dengan Server";

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText("Error");
        pDialog.setContentText(message);
        pDialog.setConfirmText("OKE");
        pDialog.show();
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                pDialog.dismissWithAnimation();
            }
        });
    }
}