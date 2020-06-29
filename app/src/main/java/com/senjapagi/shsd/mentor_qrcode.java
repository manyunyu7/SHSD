package com.senjapagi.shsd;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.senjapagi.shsd.R;

import androidx.appcompat.app.AppCompatActivity;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class mentor_qrcode extends AppCompatActivity {

    String id,nama,nim,fakultas,jurusan;
    ImageView showQR;
    TextView replacement,idMentee,idMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_qr);
        findViewById(R.id.containerQR).setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.item_animation_falldown));

        String id = getIntent().getStringExtra(ConstantMentor.mentor_id);
        String nama = getIntent().getStringExtra(ConstantMentor.nama);
        String nim = getIntent().getStringExtra(ConstantMentor.nim);
        String jurusan = getIntent().getStringExtra(ConstantMentor.jurusan);
        String fakultas = getIntent().getStringExtra(ConstantMentor.fakultas);
        String password = getIntent().getStringExtra(ConstantMentor.password);

        init();
        String str = id+"-"+nim+"@"+".badanmentoring";

        String[] res = str.split("[@]", 0);
        String data = res[0];

        final QRGEncoder qre = new QRGEncoder(str, null, QRGContents.Type.TEXT, 500);
        Bitmap qrbm = qre.getBitmap();
        showQR.setImageBitmap(qrbm);

        idMain.setText(nama);
        replacement.setVisibility(View.INVISIBLE);
        idMentee.setText(fakultas+"-"+nim+"\nMentor Badan Mentoring");

    }

    private void init(){
        showQR=findViewById(R.id.show_qr);
        replacement=findViewById(R.id.reptext);
        idMentee=findViewById(R.id.id_mentee);
        idMain=findViewById(R.id.id_mentee_main);

    }


}
