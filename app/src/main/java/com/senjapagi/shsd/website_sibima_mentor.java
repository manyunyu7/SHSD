package com.senjapagi.shsd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.senjapagi.shsd.Services.CLIENT_API;

public class website_sibima_mentor extends AppCompatActivity {

    FloatingActionButton btnHome;
    WebView websiteView;
    String webUrl = CLIENT_API.website+"/";
    String nim,password;

    static WebView mWebView;
    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_sibima);
        findViewById(R.id.animation_lootie_loading).setVisibility(View.VISIBLE);
        nim=getIntent().getStringExtra(ConstantMentee.nim);
        password=getIntent().getStringExtra(ConstantMentee.password);
        websiteView = (WebView) findViewById(R.id.webview);
        findViewById(R.id.webview).setVisibility(View.INVISIBLE);
        WebSettings webSettings = websiteView.getSettings();
        websiteView.getSettings().setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        websiteView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(website_sibima_mentor.this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        websiteView.clearCache(true);
        websiteView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        websiteView.loadUrl(webUrl);
        Toast.makeText(website_sibima_mentor.this, "Silahkan Input Nilai Melalui Menu Berita Mentoring SIBIMA", Toast.LENGTH_LONG).show();
        Handler a = new Handler();
        a.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(website_sibima_mentor.this, "Tekan Tombol Home di Pojok Kanan Bawah Bila Sudah Selesai (Jangan Logout Akun pada Website di Menu Ini)", Toast.LENGTH_LONG).show();
            }
        },3000);
        websiteView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
//                if(websiteView.getUrl().equals(CLIENT_API.website+"/mentor/berita-mentoring")||websiteView.getUrl().equals(CLIENT_API.website+"/mentor/berita-mentoring/rekap")){
                    findViewById(R.id.webview).setVisibility(View.VISIBLE);
                    findViewById(R.id.animation_lootie_loading).setVisibility(View.INVISIBLE);
//                }else{
//                    findViewById(R.id.webview).setVisibility(View.INVISIBLE);
//                    findViewById(R.id.animation_lootie_loading).setVisibility(View.VISIBLE);
//                }

//                String javaScript =
//                        "javascript:function autoMasuk(){" +
//                                "document.getElementsByName('nim')[0].value='1202184201';" +
//                                "document.getElementsByName('password')[0].value='mentee123';" +
//                                "document.getElementsByTagName('form')[0].submit();" +
//                                "}autoMasuk" +
//                                "();";
                    String js = "javascript:function autoMasuk(){" +
                            "var linkatas = document.location.href;" +
                            "if(linkatas=='"+CLIENT_API.website+"/login'){" +
                            "document.getElementsByName('nim')[0].value='" + nim + "';" +
                            "document.getElementsByName('password')[0].value='" + password + "';" +
                            "document.getElementsByTagName('form')[0].submit();" +
                            "}" +
                            "}" +
                            "autoMasuk();";
                    String a = "javascript:function autoMasuk(){" +
                            "var linkatas = document.location.href;" +
                            "if(linkatas=='"+CLIENT_API.website+"/login'){" +
                            "document.getElementsByName('nim')[0].value='" + nim + "';" +
                            "document.getElementsByName('password')[0].value='" + password + "';" +
                            "document.getElementsByTagName('form')[0].submit();" +
                            "}else if(linkatas=='"+CLIENT_API.website+"/mentor/dashboard'){" +
                            "window.location.href='"+CLIENT_API.website+"/mentor/berita-mentoring';" +
                            "}else if(linkatas=='"+CLIENT_API.website+"/mentor/berita-mentoring'){" +
                            "document.querySelector('button.navbar-toggler').remove();" +
                            "document.querySelector('ul.nav-profile a.dropdown-toggle').removeAttribute('data-toggle');" +
                            "document.querySelector('ul.nav-profile a.dropdown-toggle img').remove();" +
                            "document.querySelector('ul.nav-profile a.dropdown-toggle span').removeAttribute('class');" +
                            "document.querySelector('ul.nav-profile a.dropdown-toggle').setAttribute('class','nav-link');" +
                            "document.querySelectorAll('a.breadcrumb-item')[0].href='#';" +
                            "}" +
                            "}" +
                            "autoMasuk();";

//                if(websiteView.getUrl().toString().equals(CLIENT_API.website+"/mentee/perizinan")
//                ||websiteView.getUrl().toString().equals(CLIENT_API.website+"/mentee/profile")){
//                    findViewById(R.id.webview).setVisibility(View.VISIBLE);
//                    findViewById(R.id.animation_lootie_loading).setVisibility(View.INVISIBLE);
//                }else{
//                    findViewById(R.id.webview).setVisibility(View.INVISIBLE);
//                    findViewById(R.id.animation_lootie_loading).setVisibility(View.VISIBLE);
//
//                }
                websiteView.loadUrl(js);
//                if(websiteView.getUrl().equals(CLIENT_API.website+"/mentor/berita-mentoring")||websiteView.getUrl().equals(CLIENT_API.website+"/mentor/berita-mentoring/rekap")){
//                    findViewById(R.id.webview).setVisibility(View.VISIBLE);
//                    findViewById(R.id.animation_lootie_loading).setVisibility(View.INVISIBLE);
//                }else{
//                    findViewById(R.id.webview).setVisibility(View.INVISIBLE);
//                    findViewById(R.id.animation_lootie_loading).setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            public void onReceivedError(WebView view, int errorCod,String description, String failingUrl) {
                Toast.makeText(website_sibima_mentor.this, "Your Internet Connection May not be active Or " + description , Toast.LENGTH_LONG).show();
                findViewById(R.id.lyt_error_internet).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                websiteView.loadUrl(webUrl);
            }
        });

        findViewById(R.id.btn_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                website_sibima_mentor.super.onBackPressed();
                CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(website_sibima_mentor.this);
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeAllCookie();
            }
        });


    }
    @Override
    protected void onStop()
    {
        super.onStop();
        CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(website_sibima_mentor.this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        // insert here your instructions
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(website_sibima_mentor.this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
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

