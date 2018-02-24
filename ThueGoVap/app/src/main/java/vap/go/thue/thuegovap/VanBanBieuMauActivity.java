package vap.go.thue.thuegovap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class VanBanBieuMauActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_ban_bieu_mau);

        WebView myWebView = (WebView) findViewById(R.id.wvVBBM);
        myWebView.getSettings().setBuiltInZoomControls(true);

        //This will zoom out the WebView
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.setInitialScale(1);
        myWebView.loadUrl("http://www.gdt.gov.vn/wps/portal/!ut/p/z1/hY7NDoIwEISfhQNXusSq6K0SBAQ1GIPYiwGDBVMogQqvb6Pe_Jvb7nwzGURRgmid9iVLZSnqlKv7SCcnOwrsOIgBrOXOAX89I7GFHRP2GB3-AVTZ8EUEVJ4-ENslHp6GqgG7AD5ebDeeHZngj17Aj44VooyL7DmX1NnIYoi2-SVv89a4tepdSNl0cx10GIbBYEIwnhtnURl9rcOnVCE6iZI3GDVVAtcx70OiaXcfR2hq/dz/d5/L2dBISEvZ0FBIS9nQSEh/");
    }
}
