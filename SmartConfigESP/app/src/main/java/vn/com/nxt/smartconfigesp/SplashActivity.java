package vn.com.nxt.smartconfigesp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

               Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
               SplashActivity.this.startActivity(mainIntent);
               SplashActivity.this.finish();
              


            }
        }, SPLASH_DISPLAY_LENGTH);
    }


    public static boolean hasActiveInternetConnection(Context context){
        if(isNetworkAvailable(context)) {
            try {
                HttpURLConnection connection = (HttpURLConnection) (new URL("http://clients3.google.com/generate_204")).openConnection();
                connection.setRequestProperty("User-Agent", "Test");
                connection.setRequestProperty("Connection", "close");
                connection.setReadTimeout(1500);
                connection.connect();
                return (connection.getResponseCode() == 204 && connection.getContentLength() == 0);
            } catch (IOException e){
                Log.e("ERROR", "Error checking internet connection");
            }
        } else Log.e("ERROR", "No network available");
        return false;
    }


    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null;
    }
}
