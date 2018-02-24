package vap.go.thue.thuegovap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class PhieuBaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieu_bao);

        WebView myWebView = (WebView) findViewById(R.id.wvPB);
        myWebView.getSettings().setBuiltInZoomControls(true);

        //This will zoom out the WebView
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.setInitialScale(1);
        myWebView.loadUrl("https://thuegovap.000webhostapp.com/phieubao/1.html");
    }
}
