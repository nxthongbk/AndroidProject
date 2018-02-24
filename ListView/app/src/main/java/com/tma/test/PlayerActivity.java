package com.tma.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
       // String resultReceiver = intent.getParcelableExtra("links");


        Toast.makeText(PlayerActivity.this,"ss"
                , Toast.LENGTH_LONG).show();

    }
}
