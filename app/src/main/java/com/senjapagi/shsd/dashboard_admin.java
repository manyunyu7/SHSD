package com.senjapagi.shsd;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.senjapagi.shsd.Beautifier.AdminBeautifier;
import com.senjapagi.shsd.Beautifier.LogoutConfirm;

import java.text.SimpleDateFormat;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class dashboard_admin extends AppCompatActivity {
    String dn;
    SweetAlertDialog pDialog,changeDialog;
    Handler handler = new Handler();
    AdminBeautifier beautifierGin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_menu_admin);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//date format for APIut
        beautifierGin = new AdminBeautifier(getApplicationContext(),getWindow().getDecorView());

        beautifierGin.menuGone();

        findViewById(R.id.containerHelpdesk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard_admin.this,admin_manage_contact.class));
            }
        });




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }


        pDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.setTitleText("Login Berhasil");
        pDialog.setContentText("Anda Login Sebagai Admin");
        pDialog.hide();
        pDialog.setCancelable(false);
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                pDialog.dismissWithAnimation();
                beautifierGin.menuAppear();
            }
        });

        pDialog.show();


    }

    @Override
    public void onBackPressed() {
        LogoutConfirm a = new LogoutConfirm(dashboard_admin.this);
        a.logoutConfirm();
    }

    private void dialogCheckPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(dashboard_admin.this, new String[]{android.Manifest.permission.CAMERA}, 50);
            Toast.makeText(this, "Aktifkan Permission Camera", Toast.LENGTH_SHORT).show();
        } else {
            //Do Nothing
        }
    }

    public void SnackBarInternet(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Gagal Terhubung Dengan Server");
        builder1.setCancelable(true);

        builder1.setNegativeButton(
                "Refresh",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setPositiveButton(
                "Keluar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            finishAndRemoveTask();
                            System.exit(1);
                        }else{
                            finishAffinity();
                            System.exit(1);
                        }
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }




}