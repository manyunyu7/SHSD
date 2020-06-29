package com.senjapagi.shsd;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleObserver;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.senjapagi.shsd.ActivityGeneral.MainActivity;
import com.senjapagi.shsd.Services.CLIENT_API;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class dashboard_mentee extends AppCompatActivity implements LifecycleObserver {

    TextView txtLandingNama,txtLandingMessage,txtNama,txtNim,
            txtNamaMentor,txtKelompokMentee,txtKontakMentor,txtLine;
    String realtimeGreeter="Halo";
    String realtimeMessage="Tetap Semangat Yaa";
    SweetAlertDialog pDialog,pADialog,changeDialog;
    EditText etJudulTugas,etLinkTugas,etCaptionTugas;
    CardView btnQRMentee,btnPanduanMentee,btnLihatNilai;
    String id, nama ,telp_mentee,line_mentee,nim,jurusan,fakultas,password ;
    ScrollView scrollMenuMentee;
    String kelompok;
    final Handler handler = new Handler();

    boolean updateContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_mentee);
        init();


        realtimeGreeting();

        telp_mentee=getIntent().getStringExtra(ConstantMentee.mentee_telp);
        line_mentee=getIntent().getStringExtra(ConstantMentee.mentee_line);
        password=getIntent().getStringExtra(ConstantMentee.password);


         id = getIntent().getStringExtra(ConstantMentee.mentee_id);
         nama = getIntent().getStringExtra(ConstantMentee.nama);
         nim = getIntent().getStringExtra(ConstantMentee.nim);
         jurusan = getIntent().getStringExtra(ConstantMentee.jurusan);
         fakultas = getIntent().getStringExtra(ConstantMentee.fakultas);
         kelompok = getIntent().getStringExtra(ConstantMentee.kelompok);
        final String line_mentor = getIntent().getStringExtra(ConstantMentee.mentor_line);
        final String kontak_mentor = getIntent().getStringExtra(ConstantMentee.mentor_telp);

        String mentors = getIntent().getStringExtra(ConstantMentee.mentor_name);
        String[] splitedMentor = mentors.split("\\s+");
        final String mentor=splitedMentor[0]+" "+splitedMentor[1];
        if(line_mentee.equals("")||line_mentee.equals(null) || telp_mentee.equals(null)||telp_mentee.equals(""))
            updateContact=false;
        else
            updateContact=true;

        txtNamaMentor.setText(mentor);
        txtKelompokMentee.setText(kelompok);
        txtKontakMentor.setText(kontak_mentor);
        txtLine.setText(line_mentor);

        String str = nama;
        String[] splited = str.split("\\s+");

        txtLandingNama.setText(realtimeGreeter+" "+splited[0]);
        txtNama.setText(splited[0]+" "+splited[1]);
        txtNim.setText(nim);
        txtLandingMessage.setText(realtimeMessage);


        btnQRMentee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard_mentee.this,mentee_qrcode.class);
                intent.putExtra(ConstantMentee.mentee_id,id);
                intent.putExtra(ConstantMentee.nama,nama);
                intent.putExtra(ConstantMentee.nim,nim);
                intent.putExtra(ConstantMentee.fakultas,fakultas);
                intent.putExtra(ConstantMentee.jurusan,jurusan);
                startActivity(intent);
            }
        });
        btnPanduanMentee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (dashboard_mentee.this,mentee_buku_panduan.class));
            }
        });
        btnLihatNilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard_mentee.this,mentee_lihat_nilai.class);
                intent.putExtra(ConstantMentee.nim, nim);
                intent.putExtra(ConstantMentee.kelompok, kelompok);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_lihat_kelompok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard_mentee.this,mentee_lihat_kelompok.class);
                intent.putExtra(ConstantMentee.mentor_name,mentor);
                intent.putExtra(ConstantMentee.mentor_line,line_mentor);
                intent.putExtra(ConstantMentee.mentor_telp,kontak_mentor);
                intent.putExtra(ConstantMentee.kelompok, kelompok);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_my_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard_mentee.this, mentee_settings.class);
                intent.putExtra(ConstantMentee.nim, nim);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_menu_tubes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.pseudocontainerMenu).setVisibility(View.VISIBLE);
                findViewById(R.id.containerMenu).setVisibility(View.GONE);
                findViewById(R.id.lyt_upload_tubes).setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.item_animation_falldown));
                findViewById(R.id.lyt_upload_tubes).setVisibility(View.VISIBLE);
                scrollMenuMentee.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
            }
        });

        findViewById(R.id.btn_menu_helpdesk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard_mentee.this, mentee_helpdesk_and_perizinan.class);
                intent.putExtra(ConstantMentee.nim,nim);
                intent.putExtra(ConstantMentee.mentee_id,id);
                intent.putExtra(ConstantMentee.password,password);
                intent.putExtra(ConstantMentee.fakultas,fakultas);
                intent.putExtra(ConstantMentee.jurusan,jurusan);
                intent.putExtra(ConstantMentee.nama,nama);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_upload_tubes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadTubes();
            }
        });
        findViewById(R.id.btn_cancel_tubes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.pseudocontainerMenu).setVisibility(View.GONE);
                findViewById(R.id.containerMenu).setVisibility(View.VISIBLE);
                findViewById(R.id.lyt_upload_tubes).setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.item_animation_fallup));
                findViewById(R.id.lyt_upload_tubes).setVisibility(View.GONE);
                scrollMenuMentee.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });
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
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getInitialContact();
                    }
                }, 1000);
           }
        });
        pDialog.show();

    }

    @Override
    public void onBackPressed() {
     logoutConfirm();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        findViewById(R.id.temp_loading).setVisibility(View.VISIBLE);
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                findViewById(R.id.temp_loading).setVisibility(View.GONE);
//            }
//        }, 5000);
    }

    private void init(){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        scrollMenuMentee=findViewById(R.id.scroll_menu_mentee);

        txtLandingMessage=findViewById(R.id.tv_landingMessage);
        txtLandingNama=findViewById(R.id.tv_landing_nama);
        txtNama=findViewById(R.id.tv_nama_mentee);
        txtNim=findViewById(R.id.tv_nim_mentee);

        etCaptionTugas=findViewById(R.id.et_caption_tugas);
        etJudulTugas=findViewById(R.id.et_title_tugas);
        etLinkTugas=findViewById(R.id.et_link_tugas);

        txtNamaMentor=findViewById(R.id.tv_nama_mentor);
        txtKelompokMentee=findViewById(R.id.tv_kelompok_mentee);
        txtKontakMentor=findViewById(R.id.tv_kontak_mentor);
        txtLine=findViewById(R.id.tv_link_line);

        btnQRMentee=findViewById(R.id.btn_qr_mentee);
        btnPanduanMentee=findViewById(R.id.btn_panduan_mentee);
        btnLihatNilai=findViewById(R.id.btn_lihat_nilai);


    }

    private void pleaseChange(){
        changeDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        changeDialog.setTitleText("Update Data Diri Sebelum Melanjutkan");
        changeDialog.setContentText("Silakan Update Line dan Kontak Anda di Menu Profil");
        changeDialog.setCancelable(false);
        changeDialog.setConfirmText("Update Profil");
        changeDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                changeDialog.hide();
                Intent intent = new Intent(dashboard_mentee.this, mentee_settings.class);
                intent.putExtra(ConstantMentee.nim, nim);
                startActivity(intent);
            }
        });
        changeDialog.show();
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

    private void getInitialContact(){
        AndroidNetworking.post(CLIENT_API.getBasicMentee)
                .addBodyParameter("nim",getIntent().getStringExtra(ConstantMentee.nim))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String line = response.getJSONArray("before").getJSONObject(0).getString("line");
                            String kontak = response.getJSONArray("before").getJSONObject(0).getString("kontak");

                            if(line.equals("")||line.equals(null)||kontak.equals("")||kontak.equals(null)){
                               pleaseChange();
                            }else{
                                //DO NOTHING
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(dashboard_mentee.this, "Unstable Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(dashboard_mentee.this, anError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadTubes(){
        String judul = etJudulTugas.getText().toString();
        String link = etLinkTugas.getText().toString();
        String caption = etCaptionTugas.getText().toString();
        if(judul.equals("")||judul.equals(null))
            etJudulTugas.setError("Isi Bidang Ini");
        if(link.equals("")||link.equals(null))
            etLinkTugas.setError("Isi Bidang Ini");
        if(caption.equals("")||caption.equals(null))
            etCaptionTugas.setError("Isi Bidang Ini");
        if(caption.length()<5){
            etCaptionTugas.setError("Tambahkan Deskripsi Lebih Panjang");
        }
        else {
            findViewById(R.id.animation_lootie_loading).setVisibility(View.VISIBLE);
        AndroidNetworking.post(CLIENT_API.uploadTubes)
                .addBodyParameter("nim",nim)
                .addBodyParameter("judul",judul)
                .addBodyParameter("link",link)
                .addBodyParameter("deskripsi",caption)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        findViewById(R.id.animation_lootie_loading).setVisibility(View.GONE);
                        try {
                            String status=response.getJSONArray("insert_status").getJSONObject(0).getString("status_insert");
                            if(status.equals("berhasil")){
                                Toast.makeText(dashboard_mentee.this, "Berhasil Upload Tugas", Toast.LENGTH_SHORT).show();
                                pADialog = new SweetAlertDialog(dashboard_mentee.this, SweetAlertDialog.SUCCESS_TYPE);
                                pADialog.setTitleText("Berhasil Upload Tugas");
                                pADialog.setConfirmText("Alhamdulillah");
                                pADialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                      pADialog.hide();
                                    }
                                });
                                pADialog.show();
                            }else{
                                Toast.makeText(dashboard_mentee.this, "Kelompok Anda Sudah Mengupload\n" +
                                        "Mohon Cek Menu Nilai Untuk Memastikan", Toast.LENGTH_SHORT).show();

                                pADialog = new SweetAlertDialog(dashboard_mentee.this, SweetAlertDialog.WARNING_TYPE);
                                pADialog.setTitleText("Gagal Upload Tugas");
                                pADialog.setContentText("Rekan Kelompok Anda Sudah Mengupload Tugas, Mohon Cek Menu Nilai Untuk Memastikan");
                                pADialog.setConfirmText("Cek Nilai");
                                pADialog.show();
                                pADialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        pADialog.hide();
                                        Intent intent = new Intent(dashboard_mentee.this,mentee_lihat_nilai.class);
                                        intent.putExtra(ConstantMentee.nim, nim);
                                        intent.putExtra(ConstantMentee.kelompok, kelompok);
                                        startActivity(intent);
                                    }
                                });
                                pADialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        pADialog.hide();
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(dashboard_mentee.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        findViewById(R.id.animation_lootie_loading).setVisibility(View.GONE);
                        Toast.makeText(dashboard_mentee.this, "Connection Unstable or Server Error", Toast.LENGTH_SHORT).show();
                    }
                });
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
