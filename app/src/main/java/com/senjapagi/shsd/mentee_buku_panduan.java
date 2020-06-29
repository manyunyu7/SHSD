package com.senjapagi.shsd;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.senjapagi.shsd.R;
import com.senjapagi.shsd.Services.CLIENT_API;

public class mentee_buku_panduan extends AppCompatActivity {

    FloatingActionButton btnHome;
    WebView websiteView;
    String webUrl = CLIENT_API.website+"/resource/panduan_mentor.pdf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_buku_panduan);
        Toast.makeText(this, "Tekan Tombol Refresh Jika Buku Tidak Termuat", Toast.LENGTH_SHORT).show();

        websiteView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = websiteView.getSettings();
        websiteView.getSettings().setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        websiteView.getSettings().setAllowFileAccessFromFileURLs(true);
        websiteView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        websiteView.getSettings().setBuiltInZoomControls(true);
        websiteView.setWebChromeClient(new WebChromeClient());

        websiteView.loadUrl("https://docs.google.com/gview?embedded=true&url="+ webUrl);
        websiteView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                findViewById(R.id.webview).setVisibility(View.VISIBLE);
                findViewById(R.id.animation_lootie_loading).setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                findViewById(R.id.animation_lootie_loading).setVisibility(View.VISIBLE);
            }
            public void onReceivedError(WebView view, int errorCod,String description, String failingUrl) {
                Toast.makeText(mentee_buku_panduan.this, "Your Internet Connection May not be active Or " + description , Toast.LENGTH_LONG).show();
                findViewById(R.id.layout_error_internet).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                websiteView.loadUrl("https://docs.google.com/gview?embedded=true&url="+ webUrl);
            }
        });

        findViewById(R.id.btn_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mentee_buku_panduan.super.onBackPressed();
            }
        });


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        WebView webview = (WebView) findViewById(R.id.webview);

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webview.canGoBack()) {
                        webview.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState )
    {
        WebView webview = (WebView) findViewById(R.id.webview);

        super.onSaveInstanceState(outState);
        webview.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        WebView webview = (WebView) findViewById(R.id.webview);
        super.onRestoreInstanceState(savedInstanceState);
        webview.restoreState(savedInstanceState);
    }


}

