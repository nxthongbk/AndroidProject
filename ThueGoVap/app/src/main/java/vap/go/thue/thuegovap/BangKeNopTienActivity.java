package vap.go.thue.thuegovap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class BangKeNopTienActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bang_ke_nop_tien);
        WebView myWebView = (WebView) findViewById(R.id.wvBKNT);
        myWebView.getSettings().setBuiltInZoomControls(true);

        //This will zoom out the WebView
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.setInitialScale(1);
        myWebView.loadUrl("https://thuegovap.000webhostapp.com/bangkenoptien/1.html");
    }
}
