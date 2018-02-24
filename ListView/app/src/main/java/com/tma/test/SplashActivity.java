package com.tma.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGTH = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, MenuActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
//        }
//        else {
//            AlertDialog.Builder dialog = new AlertDialog.Builder(SplashActivity.this);
//            dialog.setCancelable(false);
//            dialog.setTitle("Vui lòng kiểm tra kết nối mạng");
//            dialog.setMessage("Không kết nối được internet. Vui lòng mở Wifi hoặc 3G" );
//            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int id) {
//                    //Action for "Delete".
//                }
//            });
//
//            final AlertDialog alert = dialog.create();
//            alert.show();

       // }
    }

    public boolean isInternetAvailable() {
        try {
            final InetAddress address = InetAddress.getByName("www.google.com");
            boolean state = !address.equals("");


            Toast.makeText(SplashActivity.this,"ss"+!address.equals(""), Toast.LENGTH_LONG).show();
            return state;


        } catch (UnknownHostException e) {
            // Log error
        }
        return false;
    }

}
