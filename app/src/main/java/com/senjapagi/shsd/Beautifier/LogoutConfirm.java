package com.senjapagi.shsd.Beautifier;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.senjapagi.shsd.ActivityGeneral.MainActivity;

public class LogoutConfirm extends AppCompatActivity {

    Context mContext;

    public LogoutConfirm(Context mContext) {
        this.mContext = mContext;
    }

    public void logoutConfirm(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
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
                        Intent a = new Intent(mContext, MainActivity.class);
                        mContext.startActivity(a);
                        finish();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
