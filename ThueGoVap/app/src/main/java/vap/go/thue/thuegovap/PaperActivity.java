package vap.go.thue.thuegovap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class PaperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper);

        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("https://thuegovap.000webhostapp.com/hoidap.html");
    }
}
