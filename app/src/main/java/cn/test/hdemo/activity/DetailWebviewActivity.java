package cn.test.hdemo.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.test.hdemo.App;
import cn.test.hdemo.R;

public class DetailWebviewActivity extends BaseActivity {

    private String H5_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_webview);
        initWebView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            if(getIntent().getStringExtra("title")==null){
                setTitle("详情");
            } else {
                setTitle(String.format("%s",getIntent().getStringExtra("title")));
            }
            H5_url = getIntent().getStringExtra("H5_url");
            webView.loadUrl(H5_url);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //setAd();
    }

    /**
     * ad
     */
    private void setAd() {
        final View view1 = LayoutInflater.from(this).inflate(R.layout.item_detial_webview_ad,null);
        final View view2 = LayoutInflater.from(this).inflate(R.layout.item_detial_webview_ad,null);
        final View view3 = LayoutInflater.from(this).inflate(R.layout.item_detial_webview_ad,null);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(App.getApp(),"点击了神策推荐",Toast.LENGTH_SHORT).show();
            }
        });
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(App.getApp(),"点击了神策推荐",Toast.LENGTH_SHORT).show();
            }
        });
        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(App.getApp(),"点击了神策推荐",Toast.LENGTH_SHORT).show();
            }
        });
        linearLayout.addView(view1);
        linearLayout.addView(view2);
        linearLayout.addView(view3);

    }

    private WebView webView=null;
    private LinearLayout linearLayout =null;
    private void initWebView() {
        setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailWebviewActivity.this.finish();
            }
        });

        webView=findViewById(R.id.webview);
        linearLayout=findViewById(R.id.ll_detail_webview);
        linearLayout.setVisibility(View.INVISIBLE);
        WebSettings webSettings = webView.getSettings();
        //
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);



        // TODO 打通 App 和 H5
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return false;
            }



        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                return super.shouldOverrideUrlLoading(view, request);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                //linearLayout.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }
}
