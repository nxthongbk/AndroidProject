package iotsmartdevices.example.com.iotsmartdevices;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebView1Activity extends Activity {

    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view1);

        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://tma-farm-iot.herokuapp.com/dashboard.html");

    }

}