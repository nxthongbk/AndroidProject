package vn.com.nxt.smartconfigesp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import vn.com.nxt.smartconfigesp.demo_activity.EsptouchDemoActivity;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static vn.com.nxt.smartconfigesp.R.*;

public class MainActivity extends AppCompatActivity {

    Firebase root;
    boolean bState= false;
    boolean bExist = false;
    boolean bCreated = false;
    int current_position;
    ImageView imgState;
    private ListView lvContact;
    int[] color = {Color.RED, Color.GREEN,Color.GRAY, Color.YELLOW,Color.BLACK, Color.BLUE,Color.CYAN, Color.DKGRAY,Color.MAGENTA, Color.LTGRAY};

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        lvContact = (ListView) findViewById(R.id.lv_Contact);
        final TextView tvDevices = findViewById(R.id.tv_Devices);
        final SharedPreferences pre=getSharedPreferences("device_list", MODE_PRIVATE);
        final ArrayList<Device> arrDevice = new ArrayList<>();;



        //-------Display List of Device -----------------
        //Get preferences
        String device_list = pre.getString("device_list", "");
        if(!device_list.equalsIgnoreCase(""))
        {



            String[] devices = device_list.split(";");
            for (int i=0;i<devices.length;i++)
            {
                Device device1 = new Device(devices[i].split(",")[0],devices[i].split(",")[1], color[i%10]);
                arrDevice.add(device1);
            }

            //Toast.makeText(MainActivity.this, arrDevice.get(0).getDescription()+"", Toast.LENGTH_SHORT).show();

            tvDevices.setText("Total: "+arrDevice.size()+"  " );

            CustomAdapter customAdaper = new CustomAdapter(this,R.layout.row_listview,arrDevice);
            lvContact.setAdapter(customAdaper);
            lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    long viewId = view.getId();

                    if (viewId == R.id.tvEditDevice) {
                        Toast.makeText(MainActivity.this, "click Edit Test", Toast.LENGTH_SHORT).show();
                    }
                    bExist= false;
                    bCreated = false;
                    current_position = position;

                    //Check device is exist or not first
                    //isDeviceExist = "no";

                    Firebase.setAndroidContext(MainActivity.this);
                    final Firebase online_status = new Firebase("https://sonoff-6b6ea.firebaseio.com/");
                    online_status.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild("device"+arrDevice.get(current_position).getDescription())) {
                                bExist= true;
                                if(bExist && !bCreated) {
                                    Intent detailDevice = new Intent(MainActivity.this, DeviceDetail.class);
                                    detailDevice.putExtra("device_name", arrDevice.get(current_position).getName() + "");
                                    detailDevice.putExtra("description", arrDevice.get(current_position).getDescription() + "");
                                    MainActivity.this.startActivity(detailDevice);
                                    bCreated = true;
                                }

                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Device ID is not exist.Please check... " , Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                        }
                    });


//                    Intent detailDevice = new Intent(MainActivity.this, DeviceDetail.class);
//                    detailDevice.putExtra("device_name", arrDevice.get(current_position).getName() + "");
//                    detailDevice.putExtra("description", arrDevice.get(current_position).getDescription() + "");
//                    MainActivity.this.startActivity(detailDevice);
                }
            });
        }



        // Adding Device ---------------------------
        final Button button = findViewById(R.id.btnAdd);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                String current_device_list = pre.getString("device_list", "");
//                SharedPreferences.Editor editor = pre.edit();
//                editor.putString("device_list", current_device_list+"Device 1:bed room 1;");
//                editor.apply();

                Intent createNewDevice = new Intent(v.getContext(), CreateNewDevice.class);
                startActivityForResult(createNewDevice,1);
                //v.getContext()(createNewDevice);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to

        if (resultCode == RESULT_OK) {
            Intent refresh = new Intent(this,MainActivity.class);
            startActivity(refresh);
            this.finish();
        }
    }


}
