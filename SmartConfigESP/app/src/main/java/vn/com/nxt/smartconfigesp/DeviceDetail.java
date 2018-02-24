package vn.com.nxt.smartconfigesp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.IOException;

public class DeviceDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_detail);
        final TextView tvStatus = findViewById(R.id.tvDetailStatus);
        final TextView tvDeviceTitle = findViewById(R.id.tvDetailTitle);
        final EditText edtRepeat = findViewById(R.id.edtDetailTime);
        final TextView tvDeviceID = findViewById(R.id.tvDetailDeviceID);
        final EditText edtDeviceTimer = findViewById(R.id.edtDetailTimer);
        final TextView tvTurnOnTime = findViewById(R.id.tvDetailTurnOnTime);
        final TextView tvTurnOffTime = findViewById(R.id.tvDetailTurnOffTime);
        final TextView tvConnectionState = findViewById(R.id.tvConnecttionState);

        Intent i = getIntent();
        final String deviceName = i.getStringExtra("device_name").trim();
        final String deviceID = i.getStringExtra("description").trim();

        tvDeviceTitle.setText("DEVICE: "+deviceName);
        tvDeviceID.setText("ID: "+deviceID);

        final Button btnSend = findViewById(R.id.btnDetailSet);
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Firebase state = new Firebase("https://sonoff-6b6ea.firebaseio.com/device"+deviceID);
                state.child("repeat").setValue(Integer.parseInt(edtRepeat.getText().toString()));
                state.child("timer").setValue(Integer.parseInt(edtDeviceTimer.getText().toString()));
                state.child("state").setValue(3);
                state.child("running").setValue(3);
            }
        });

        final Button btnTurnOn = findViewById(R.id.btnDetailTurnOn);
        btnTurnOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Firebase state = new Firebase("https://sonoff-6b6ea.firebaseio.com/device"+deviceID);
                state.child("state").setValue(1);
                state.child("running").setValue(1);
            }
        });

        final Button btnTurnOff = findViewById(R.id.btnDetailTurnOff);
        btnTurnOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Firebase state = new Firebase("https://sonoff-6b6ea.firebaseio.com/device"+deviceID);
                state.child("state").setValue(0);
                state.child("running").setValue(2);
            }
        });


        //Check State ONLINE or OFFLINE---------------

        final Firebase online_firebase = new Firebase("https://sonoff-6b6ea.firebaseio.com/device"+deviceID);
        online_firebase.child("running").setValue(4);
        online_firebase.child("online").setValue(0);

        try{
            Firebase.setAndroidContext(this);
            final Firebase online = new Firebase("https://sonoff-6b6ea.firebaseio.com/device"+deviceID+"/online");
            online.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue().toString().equals("1"))
                    {

                        tvConnectionState.setText("online");
                        tvConnectionState.setTextColor(Color.GREEN);
                        tvStatus.setVisibility(View.VISIBLE);
                        btnSend.setVisibility(View.VISIBLE);
                        btnTurnOff.setVisibility(View.VISIBLE);
                        btnTurnOn.setVisibility(View.VISIBLE);
//                        Firebase online_revert = new Firebase("https://sonoff-6b6ea.firebaseio.com/device"+deviceID);
//                        online_revert.child("online").setValue(0);
                    }
                    else
                    {
                        tvConnectionState.setText("offline");
                        tvConnectionState.setTextColor(Color.BLACK);
                        //Toast.makeText(DeviceDetail.this, "Device is offline" , Toast.LENGTH_SHORT).show();
                        btnSend.setVisibility(View.GONE);
                        btnTurnOff.setVisibility(View.GONE);
                        btnTurnOn.setVisibility(View.GONE);
                        tvStatus.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(DeviceDetail.this, "Can not connect " , Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(DeviceDetail.this, "Device " + device_number, Toast.LENGTH_SHORT).show();
        TextView tvConnectionStatus = findViewById(R.id.tvConnecttionState);
        tvConnectionStatus.setText("yes");
        tvConnectionStatus.setTextColor(Color.GREEN);

        //get repeat
        //Setup Firebase
        //-REPEAT--------------------
        try{
        Firebase.setAndroidContext(this);
        final Firebase repeat = new Firebase("https://sonoff-6b6ea.firebaseio.com/device"+deviceID+"/repeat");
        repeat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                edtRepeat.setText( dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        }
        catch (Exception e)
        {
            Toast.makeText(DeviceDetail.this, "Can not connect " , Toast.LENGTH_SHORT).show();
        }

        //-TIMER--------------------
        try{
            Firebase.setAndroidContext(this);
            final Firebase timer = new Firebase("https://sonoff-6b6ea.firebaseio.com/device"+deviceID+"/timer");
            timer.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    edtDeviceTimer.setText( dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(DeviceDetail.this, "Can not connect " , Toast.LENGTH_SHORT).show();
        }

        //-HISTORY--------------------
        try{
            Firebase.setAndroidContext(this);
            final Firebase history = new Firebase("https://sonoff-6b6ea.firebaseio.com/device"+deviceID+"/turnon");
            history.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    tvTurnOnTime.setText( dataSnapshot.getValue().toString()+" ");
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(DeviceDetail.this, "Can not connect " , Toast.LENGTH_SHORT).show();
        }

        try{
            Firebase.setAndroidContext(this);
            final Firebase history = new Firebase("https://sonoff-6b6ea.firebaseio.com/device"+deviceID+"/turnoff");
            history.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    tvTurnOffTime.setText(dataSnapshot.getValue().toString()+" ");
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(DeviceDetail.this, "Can not connect " , Toast.LENGTH_SHORT).show();
        }


        //STATE----------------------

        try {
            Firebase.setAndroidContext(this);
            final Firebase root = new Firebase("https://sonoff-6b6ea.firebaseio.com/device" + deviceID + "/state");
            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue().toString().equals("1")) {
                        //Toast.makeText(DeviceDetail.this, "Device: ON " , Toast.LENGTH_SHORT).show();
                        tvStatus.setText("ON");
                        tvStatus.setTextColor(Color.GREEN);

                    } else {
                        //Toast.makeText(DeviceDetail.this, "Device: OFF " , Toast.LENGTH_SHORT).show();
                        tvStatus.setText("OFF");
                        tvStatus.setTextColor(Color.BLACK);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(DeviceDetail.this, "Can not connect " , Toast.LENGTH_SHORT).show();
        }
    }
}
