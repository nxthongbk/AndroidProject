package com.tma.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import static android.R.id.list;
import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MenuActivity extends AppCompatActivity  {


    private GridView gdv1;

    static final String[] MENU = new String[] {
            "sachkinhdoanh", "tieuthuyet","truyenngan", "sachlichsu","truyenkiemhiep","truyencotich", "truyencuoi","camnangcuocsong","phatphap" };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    Intent newIntent = new Intent(MenuActivity.this,MainActivity.class);


                    newIntent.putExtra("type", "1");
                    startActivity(newIntent);

                    return true;

                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        gdv1 = (GridView) findViewById(R.id.gridView1);
        gdv1.setAdapter(new ImageAdapter(this, MENU));

        gdv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.putExtra("type", position+"");
                startActivity(intent);
            }
        });


    }

}
