package vn.com.tma.sachhay;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.TextureView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private TextView mTextMessage;

    TextView selection;
    ArrayList<String> items = new ArrayList<String>();

    ListView list;
    String[] web = {
            "Phiếu Báo",
            "Bảng Kê nộp Tiền",
            "Thu Nhập Cá Nhân"

    } ;
    Integer[] imageId = {
            R.drawable.book,
            R.drawable.book,
            R.drawable.book,


    };


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //getBookList();

        Toast.makeText(this,  "hh",Toast.LENGTH_LONG).show();
        }


        //setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items));




        // Binding data
//        ArrayAdapter adapter = new ArrayAdapter(this,
//                android.R.layout.simple_list_item_1, items);
//
//
//
//        list=(ListView)findViewById(R.id.list);
//        list.setAdapter(adapter);
        //list.setListAdapter(adapter);
     //   setListAdapter(adapter);


}

}
