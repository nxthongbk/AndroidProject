package vn.com.tma.programmablepowersupply;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;


public class MainActivity extends AppCompatActivity {


    EditText ed;
    Switch sb,sb2,sb3,sb4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sb = (Switch)findViewById(R.id.switch1);
        sb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String statusSwitch1, statusSwitch2;
                if (sb.isChecked()) {
                    //Toast.makeText(MainActivity.this, "ON", Toast.LENGTH_SHORT).show();
                    for (int i=1;i<5;i++)
                    {
                        sendMessage("11");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    for (int i=1;i<5;i++)
                    {
                        sendMessage("10");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //Toast.makeText(MainActivity.this, "OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sb2 = (Switch)findViewById(R.id.switch2);
        sb2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String statusSwitch1, statusSwitch2;
                if (sb2.isChecked()) {
                    //Toast.makeText(MainActivity.this, "ON", Toast.LENGTH_SHORT).show();
                    for (int i=1;i<5;i++)
                    {
                        sendMessage("21");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                             e.printStackTrace();
                        }
                    }

                }
                else {
                    for (int i=1;i<5;i++)
                    {
                        sendMessage("20");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //Toast.makeText(MainActivity.this, "OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });


        sb3 = (Switch)findViewById(R.id.switch3);
        sb3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String statusSwitch1, statusSwitch2;
                if (sb3.isChecked()) {
                    //Toast.makeText(MainActivity.this, "ON", Toast.LENGTH_SHORT).show();
                    for (int i=1;i<5;i++)
                    {
                        sendMessage("31");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
                else {
                    for (int i=1;i<5;i++)
                    {
                        sendMessage("30");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //Toast.makeText(MainActivity.this, "OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sb4 = (Switch)findViewById(R.id.switch4);
        sb4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String statusSwitch1, statusSwitch2;
                if (sb4.isChecked()) {
                    //Toast.makeText(MainActivity.this, "ON", Toast.LENGTH_SHORT).show();
                    for (int i=1;i<5;i++)
                    {
                        sendMessage("41");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
                else {
                    for (int i=1;i<5;i++)
                    {
                        sendMessage("40");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //Toast.makeText(MainActivity.this, "OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }





    private void sendMessage(final String message) {

        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {

            String stringData;

            @Override
            public void run() {

                DatagramSocket ds = null;
                try {
                    ds = new DatagramSocket();
                    // IP Address below is the IP address of that Device where server socket is opened.
                    InetAddress serverAddr = InetAddress.getByName("192.168.4.1");
                    DatagramPacket dp;
                    dp = new DatagramPacket(message.getBytes(), message.length(), serverAddr,  2390);
                    ds.send(dp);
                    //Toast.makeText(MainActivity.this, "sent", Toast.LENGTH_SHORT).show();

//                    byte[] lMsg = new byte[1000];
//                    dp = new DatagramPacket(lMsg, lMsg.length);
//                    ds.receive(dp);
//                    stringData = new String(lMsg, 0, dp.getLength());
//                    Toast.makeText(MainActivity.this, stringData, Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "error"+ e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } finally {
                    if (ds != null) {
                        ds.close();
                    }
                }

//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        String s = mTextViewReplyFromServer.getText().toString();
//                        if (stringData.trim().length() != 0)
//                            mTextViewReplyFromServer.setText(s + "\nFrom Server : " + stringData);
//
//                    }
//                });
            }
        });

        thread.start();
    }
}

