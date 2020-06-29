package com.senjapagi.shsd;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.senjapagi.shsd.ActivityGeneral.MainActivity;
import com.senjapagi.shsd.Services.CLIENT_API;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class dashboard_mentor extends AppCompatActivity {

    String nama,id,fakultas,nim,jk,kontakMentor,line,password;
    Spinner daftarKontak;
    EditText etKomplain,etNama,etJurusan,etNim;
    ArrayList<String> kontak=new ArrayList<>();
    TextView landingNamaGreeter,landingNim,landingMessage,landingNama;
    String realtimeGreeter="Halo";
    String realtimeMessage="Tetap Semangat Yaa";
    Handler handler = new Handler();

    SweetAlertDialog pDialog,pADialog,changeDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_mentor);
        findViewById(R.id.temp_loading).setVisibility(View.VISIBLE);
        findViewById(R.id.containerMenu).setVisibility(View.GONE);
        landingMessage=findViewById(R.id.tv_landingMessageMentor);
        landingNamaGreeter=findViewById(R.id.tv_landing_nama_mentor_greeter);
        landingNama=findViewById(R.id.tv_landing_nama_mentor);
        landingNim=findViewById(R.id.tv_landing_nim_mentor);

        nama=getIntent().getStringExtra(ConstantMentor.nama);
        id=getIntent().getStringExtra(ConstantMentor.mentor_id);
        fakultas=getIntent().getStringExtra(ConstantMentor.fakultas);
        nim=getIntent().getStringExtra(ConstantMentor.nim);
        kontakMentor=getIntent().getStringExtra(ConstantMentor.mentor_telp);
        line=getIntent().getStringExtra(ConstantMentor.mentor_line);
        password=getIntent().getStringExtra(ConstantMentor.password);

        String str = nama;
        String[] splited = str.split("\\s+");
        landingNamaGreeter.setText(realtimeGreeter+" "+splited[0]);
        landingNama.setText(splited[0]+" "+splited[1]);
        landingNim.setText(nim);
        landingMessage.setText(realtimeMessage);



        daftarKontak = findViewById(R.id.spinner_kontak);
        etKomplain=findViewById(R.id.et_komplain);
        etNim=findViewById(R.id.et_nim);
        etNama=findViewById(R.id.et_nama);
        etJurusan=findViewById(R.id.et_fakultas);

        etNama.setText(nama);
        etNim.setText(nim);
        etJurusan.setText(fakultas);

        getAgenda();
        findViewById(R.id.btn_helpdesk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.lyt_chat_pengurus).setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.item_animation_falldown));
                findViewById(R.id.lyt_chat_pengurus).setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.btn_kirim_pesan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickWhatsApp();
            }
        });
        findViewById(R.id.btn_panduan_mentor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(dashboard_mentor.this,mentor_buku_panduan.class);
                startActivity(a);
            }
        });
        findViewById(R.id.btn_website_sibima_mentor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(dashboard_mentor.this,website_sibima_mentor.class);
                a.putExtra(ConstantMentor.nama,nama);
                a.putExtra(ConstantMentor.nim,nim);
                a.putExtra(ConstantMentor.mentor_telp,kontakMentor);
                a.putExtra(ConstantMentor.mentor_line,line);
                a.putExtra(ConstantMentor.mentor_id,id);
                a.putExtra(ConstantMentor.fakultas,fakultas);
                a.putExtra(ConstantMentor.password,password);
                startActivity(a);
            }
        });
        findViewById(R.id.btn_my_account_mentor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(dashboard_mentor.this,mentor_settings.class);
                a.putExtra(ConstantMentor.nama,nama);
                a.putExtra(ConstantMentor.nim,nim);
                a.putExtra(ConstantMentor.mentor_telp,kontakMentor);
                a.putExtra(ConstantMentor.mentor_line,line);
                a.putExtra(ConstantMentor.mentor_id,id);
                a.putExtra(ConstantMentor.fakultas,fakultas);
                startActivity(a);
            }
        });
        findViewById(R.id.btn_mentor_qr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(dashboard_mentor.this,mentor_qrcode.class);
                a.putExtra(ConstantMentor.nama,nama);
                a.putExtra(ConstantMentor.nim,nim);
                a.putExtra(ConstantMentor.mentor_telp,kontakMentor);
                a.putExtra(ConstantMentor.mentor_line,line);
                a.putExtra(ConstantMentor.mentor_id,id);
                a.putExtra(ConstantMentor.fakultas,fakultas);
                startActivity(a);
            }
        });
        findViewById(R.id.btn_batal_kirim_pesan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.lyt_chat_pengurus).setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.item_animation_fallup));
                findViewById(R.id.lyt_chat_pengurus).setVisibility(View.GONE);
            }
        });
        findViewById(R.id.btn_lihat_kelompok_mentor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(dashboard_mentor.this,mentor_intro_kelompok.class);
                a.putExtra(ConstantMentor.nama,nama);
                a.putExtra(ConstantMentor.nim,nim);
                a.putExtra(ConstantMentor.mentor_telp,kontakMentor);
                a.putExtra(ConstantMentor.mentor_line,line);
                a.putExtra(ConstantMentor.mentor_id,id);
                a.putExtra(ConstantMentor.fakultas,fakultas);
                startActivity(a);
            }
        });
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.setTitleText("Login Berhasil");
        pDialog.setContentText("Anda Login Sebagai "+nama);
        pDialog.hide();
        pDialog.setCancelable(false);
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                pDialog.hide();
                findViewById(R.id.containerMenu).setVisibility(View.VISIBLE);
                findViewById(R.id.containerMenu).setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.item_animation_falldown));
                findViewById(R.id.temp_loading).setVisibility(View.GONE);
            }
        });
        pDialog.show();
    }
    @Override
    public void onBackPressed() {
        logoutConfirm();
    }
    public void onClickWhatsApp() {
        PackageManager pm=getPackageManager();
        try {
            PackageManager packageManager = getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);
            String message = "Assalamualaikum  , Saya "+etNama.getText().toString()+" "+etNim.getText().toString()+ " Mentor dari Fakultas "+etJurusan.getText().toString()+" \n"+
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
                                        (dashboard_mentor.this, android.R.layout.simple_list_item_1, kontak);
                                daftarKontak.setAdapter(adp1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        findViewById(R.id.animation_lootie_loading).setVisibility(View.INVISIBLE);
                        Toast.makeText(dashboard_mentor.this, anError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

    }
    private void realtimeGreeting(){
        Calendar calendar = Calendar.getInstance();
        String timeOfDays = new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());
        int timeOfDay = Integer.valueOf(timeOfDays);
        if (timeOfDay >= 00 && timeOfDay < 04) {
            realtimeGreeter = ("Selamat Pagi");
            realtimeMessage = ("Jangan Lupa Sholat Tahajjud yaa");
        } else if (timeOfDay >= 04 && timeOfDay < 05) {
            realtimeGreeter = ("Selamat Pagi");
            realtimeMessage = ("Jangan Lupa Sholat Shubuh nih");
        }else if (timeOfDay >= 05 && timeOfDay < 06) {
            realtimeGreeter=("Selamat Pagi");
            realtimeMessage=("Yuk Baca Dzikir Pagi...");
        } else if (timeOfDay >= 06 && timeOfDay < 12) {
//            greeting_img!!.setImageResource(R.drawable.bg_header_daylight)//sunrise
            realtimeGreeter=("Selamat Pagi");
            realtimeMessage=("Tetap Semangat !!");
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
//            greeting_img!!.setImageResource(R.drawable.bg_header_daylight)//daylight
            realtimeGreeter=("Selamat Siang");
            realtimeMessage=("Semangat Terus Jalani Hari !!");
        } else if (timeOfDay >= 16 && timeOfDay < 18) {
//            greeting_img!!.setImageResource(R.drawable.bg_header_evening)//evening
            realtimeGreeter=("Selamat Sore");
            realtimeMessage=("Tetap Semangat !!");
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
//            greeting_img!!.setImageResource(R.drawable.bg_header_daylight)//night
            realtimeGreeter=("Selamat Malam");
            realtimeMessage=("Selamat Beristirahat");
        }
    }
    public void logoutConfirm(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Anda Yakin Ingin Logout ???");
        builder1.setCancelable(true);

        builder1.setNegativeButton(
                "Tidak",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setPositiveButton(
                "Ya",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent a = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(a);
                        finish();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}