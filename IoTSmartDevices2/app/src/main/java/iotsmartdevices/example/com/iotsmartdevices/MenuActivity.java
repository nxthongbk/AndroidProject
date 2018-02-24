package iotsmartdevices.example.com.iotsmartdevices;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.util.Log;

public class MenuActivity extends AppCompatActivity {

    RadioGroup rgSelectionSystem;
    RadioButton rbWithMaster;
    RadioButton rbOnlyWifi;
    Button bQRCodeRead;
    Button bWifiConfiguration;
    Button bListOfDevices;
    Button bMonitorSystem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Bundle extras = getIntent().getExtras();
        String valueToken ="invalid";
        if (extras != null) {
            valueToken = extras.getString("Token");
        }

        rgSelectionSystem = (RadioGroup) findViewById(R.id.selection_system);
        rbWithMaster = (RadioButton) findViewById(R.id.with_master);
        rbOnlyWifi = (RadioButton) findViewById(R.id.only_wifi);
        bQRCodeRead = (Button) findViewById(R.id.qr_code_read);
        bWifiConfiguration = (Button) findViewById(R.id.wifi_configuration);
        bListOfDevices = (Button) findViewById(R.id.list_of_devices);
        bMonitorSystem = (Button) findViewById(R.id.monitor_system);

        bQRCodeRead.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String resultToken = "";
                Intent intent = new Intent(MenuActivity.this, QrCodeActivity.class);
                intent.putExtra("Token", resultToken);
                startActivity(intent);
            }

        });

        bListOfDevices.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ONCLICK", "Ok");
                String resultToken3 = "";
                Intent intent3 = new Intent(MenuActivity.this, ListDevicesActivity.class);
                intent3.putExtra("Token2", resultToken3);
                startActivity(intent3);
            }

        });

        bMonitorSystem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ONCLICK", "Ok");
                String resultToken2 = "";
                Intent intent2 = new Intent(MenuActivity.this, WebView1Activity.class);
                intent2.putExtra("Token2", resultToken2);
                startActivity(intent2);
            }

        });
    }

}
