package com.senjapagi.shsd.ActivityGeneral;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.google.firebase.auth.FirebaseAuth;
import com.senjapagi.shsd.Beautifier.RegisterBeautifier;
import com.senjapagi.shsd.R;
import com.senjapagi.shsd.Services.CLIENT_API;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONObject;

import java.io.File;

public class RegisterActivity extends AppCompatActivity {

    ImageView ivProfilePic;
    Uri chosenImage;
    EditText etNim,etNama,etEmail,etPassword,etVerifyPassword,
            etKelas,etJk,etAngkatan;
    FirebaseAuth mAuth;
    RadioGroup rgGender;
    RadioButton radioButton;
    ProgressDialog progressDialog;
    Image imageUpload;
    File imageFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_register);
        final RegisterBeautifier beautifierGhin = new RegisterBeautifier(getApplicationContext(), getWindow().getDecorView());
        beautifierGhin.animFirst();

        mAuth = FirebaseAuth.getInstance();
        rgGender = findViewById(R.id.rg_gender);
        int selectedId = rgGender.getCheckedRadioButtonId();

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Uploading Image, Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);


        // find the radiobutton by returned id
        radioButton = (RadioButton) findViewById(selectedId);


        etKelas = findViewById(R.id.et_kelas);
        etNim = findViewById(R.id.et_nim);
        etNama = findViewById(R.id.et_nama);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etVerifyPassword = findViewById(R.id.et_verify_password);

        findViewById(R.id.btn_cancel_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beautifierGhin.animGone();
            }
        });


        ivProfilePic = findViewById(R.id.iv_profile_pic);
        findViewById(R.id.container_profile_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestPermission();
                } else {
                    openGallery();
                }
            }


        });

        findViewById(R.id.fab_reg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
    }

    private void checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Accept All Permission Request", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        } else openGallery();
    }

    private void openGallery() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(RegisterActivity.this);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                ivProfilePic.setImageURI(resultUri);
                imageFile = new File(resultUri.getPath());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void verifyRegister(){
        findViewById(R.id.fab_reg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama,kelas,nim,email,password,verifPassword;
                nama=etNama.getText().toString();nim=etNim.getText().toString();email=etEmail.getText().toString();
                kelas=etKelas.getText().toString();password=etPassword.getText().toString();verifPassword=etVerifyPassword.getText().toString();
                if(nama.isEmpty() || kelas.isEmpty() || nim.isEmpty() || email.isEmpty() || password.isEmpty() || verifPassword.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Fill the Empty Form First !", Toast.LENGTH_SHORT).show();
                }else{
                    findViewById(R.id.fab_reg).setVisibility(View.GONE);
                    findViewById(R.id.loading_register).setVisibility(View.VISIBLE);
                    if(password.equals(verifPassword)){
                    }else Toast.makeText(RegisterActivity.this, "Password don't match ! ", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void uploadData(){
        progressDialog.show();
        AndroidNetworking.upload(CLIENT_API.REGISTASI_PESERTA)
                .addMultipartFile("imageupload",imageFile)
                .addMultipartParameter("nim",etNim.getText().toString())
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        float progress = bytesUploaded/totalBytes;
                        progressDialog.setProgress(Integer.valueOf((int) progress));
                    }
                })  .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        float progress = bytesUploaded/totalBytes;
                        progressDialog.setProgress(Integer.valueOf((int) progress));
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.cancel();
                        Toast.makeText(RegisterActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.cancel();
                        Toast.makeText(RegisterActivity.this, anError.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    public void showURL(String url){
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Image URL : "+url);
        builder1.setCancelable(true);
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}