package vn.com.nxt.smartconfigesp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import vn.com.nxt.smartconfigesp.demo_activity.EsptouchDemoActivity;

public class CreateNewDevice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_device);
        final SharedPreferences pre=getSharedPreferences("device_list", MODE_PRIVATE);
        final EditText edtNewDeviceName = findViewById(R.id.edtNewDeviceName);
        final EditText edtNewDeviceID = findViewById(R.id.edtNewDeviceID);

        final Button button = findViewById(R.id.btnAddNew);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String current_device_list = pre.getString("device_list", "");
                SharedPreferences.Editor editor = pre.edit();

                String currentName = edtNewDeviceName.getText().toString();
                String currentID   = edtNewDeviceID.getText().toString();
                if(currentID.contains(":")) {
                    editor.putString("device_list", current_device_list + currentName + "," + currentID + ";");
                    editor.apply();
                    setResult(RESULT_OK,null);

//                    Intent mainDevice = new Intent(v.getContext(), MainActivity.class);
//                    v.getContext().startActivity(mainDevice);
                    finish();
                }
                else
                {
                    Toast.makeText(CreateNewDevice.this, "Device ID is not correct " , Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView tvAdvatage = findViewById(R.id.tvAdvantage);
        tvAdvatage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent smartConfig = new Intent(view.getContext(), EsptouchDemoActivity.class);
                view.getContext().startActivity(smartConfig);
            }
        });


    }
}
