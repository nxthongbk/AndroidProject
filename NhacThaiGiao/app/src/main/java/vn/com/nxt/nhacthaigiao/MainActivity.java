package vn.com.nxt.nhacthaigiao;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    ListView lvBook;
    ArrayList<String> arrayLvTitle;
    ArrayList<String> arrayLinksBook;
    String xmlLinks;
    private boolean isContinuously = false;

    private ImageButton buttonPlayPause;
    private SeekBar seekBarProgress;
    private ImageButton buttonNext;
    private ImageButton buttonPrevious;
    int currentPosition;
    private boolean isPlaying= false;

    int audioDuration;
    int currentAudioProgress=0;

    private MediaPlayer mediaPlayer;
    private ProgressBar progressBar;
    public ProgressBar loading;
    private int mediaFileLengthInMilliseconds; // this value contains the song duration in milliseconds. Look at getDuration() method in MediaPlayer class
    MyReceiver myReceiver; //my global var receiver

    private final Handler handler = new Handler();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Intent i = getIntent();
        //xmlLinks = i.getStringExtra(EXTRA_MESSAGE);

        xmlLinks = "https://nhacthaigiao.000webhostapp.com/xml/audio.xml";

        //setTitle(i.getStringExtra("Description"));

        buttonPlayPause  = (ImageButton) findViewById(R.id.btnPlay);
        buttonNext  = (ImageButton) findViewById(R.id.btnNext);
        buttonPrevious  = (ImageButton) findViewById(R.id.btnPrevious);

        loading = (ProgressBar)findViewById(R.id.loading);
        seekBarProgress = (SeekBar) findViewById(R.id.songProgressBar);

        //loading.setVisibility(View.GONE);

        registerReceiver();

        try {
            //Init Audio
            lvBook = (ListView) findViewById(R.id.lvDetail);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new ReadDetail().execute(xmlLinks);
                }
            });

            lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    //loading.setVisibility(View.VISIBLE);
                    try {
                        currentPosition = position;

                        //                   Toast.makeText(DetailActivity.this,
                        //                           arrayLinksBook.get(currentPosition), Toast.LENGTH_LONG).show();


                        Intent objIntent = new Intent(MainActivity.this, PlayAudio.class);


                        objIntent.putExtra("links", arrayLinksBook.get(position));
                        startService(objIntent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL
                    //loading.setVisibility(View.GONE);
                }

            });
        }
        catch(Exception e)
        {
            Toast.makeText(MainActivity.this,"Tạm thời không thể kết nối tới Server", Toast.LENGTH_LONG).show();
        }

        buttonPlayPause.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                isPlaying =!isPlaying;
//                Toast.makeText(DetailActivity.this,
//                        "CHange state", Toast.LENGTH_LONG).show();
                Intent objIntent = new Intent(MainActivity.this, PlayAudio.class);
                objIntent.putExtra("links","invert" );
                startService(objIntent);
                if(isPlaying)
                {
                    buttonPlayPause.setImageResource(R.drawable.btn_pause);
                }
                else
                {
                    buttonPlayPause.setImageResource(R.drawable.btn_play);
                }
            }
        });

        primarySeekBarProgressUpdater();

        buttonNext.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
//                Toast.makeText(DetailActivity.this,
//                        "111", Toast.LENGTH_LONG).show();

//                Toast.makeText(DetailActivity.this,
//                        "Next", Toast.LENGTH_LONG).show();
                currentPosition = currentPosition+1;
                Intent objIntent = new Intent(MainActivity.this, PlayAudio.class);
                objIntent.putExtra("links",arrayLinksBook.get(currentPosition) );


                startService(objIntent);
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
//                Toast.makeText(DetailActivity.this,
//                        "Next", Toast.LENGTH_LONG).show();
                currentPosition = currentPosition-1;
                Intent objIntent = new Intent(MainActivity.this, PlayAudio.class);
                objIntent.putExtra("links",arrayLinksBook.get(currentPosition) );
                startService(objIntent);
            }
        });


    }


    private void primarySeekBarProgressUpdater() {

        seekBarProgress.setProgress((int)(currentAudioProgress));
        //Toast.makeText(DetailActivity.this, audioDuration, Toast.LENGTH_LONG).show();
        currentAudioProgress=currentAudioProgress+1000;
        Runnable notification = new Runnable() {
            public void run() {
                primarySeekBarProgressUpdater();
            }
        };
        handler.postDelayed(notification,1000);

    }



    @Override
    protected void onDestroy() {
        Intent objIntent = new Intent(MainActivity.this, PlayAudio.class);
        stopService(objIntent);
        super.onDestroy();
    }

    private void registerReceiver(){
        //Register BroadcastReceiver
        //to receive event from our service
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PlayAudio.SENDMESAGGE);
        registerReceiver(myReceiver, intentFilter);
    }


    // class of receiver, the magic is here...
    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {

            if (arg1.getStringExtra("message").equals("loading"))
            {
                loading.setVisibility(View.VISIBLE);
            }
            else if (arg1.getStringExtra("message").equals("loaddone"))
            {

                loading.setVisibility(View.GONE);
                buttonPlayPause.setImageResource(R.drawable.btn_pause);
                isPlaying = true;

            }
            else if (arg1.getStringExtra("message").contains("Duration"))
            {
                audioDuration = Integer.parseInt(arg1.getStringExtra("message").replace("Duration",""));
                currentAudioProgress =0;
                seekBarProgress.setMax(audioDuration);
            }

            else
            {
                System.out.println(arg1.getStringExtra("message"));
            }
        }
    }


    public void checkStatusService(){
        if(PlayAudio.serviceStatus!=null){
            if(PlayAudio.serviceStatus == true){
                //do something
                //textview.text("Service is running");
            }else{
                //do something
                //textview.text("Service is not running");
            }
        }
    }


    private void initView() {

        buttonPlayPause = (ImageButton)findViewById(R.id.btnPlay);
        buttonPlayPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getId() == R.id.btnPlay){



                    /** ImageButton onClick event handler. Method which start/pause mediaplayer playing */
                    try {

//                        Toast.makeText(DetailActivity.this,
//                                "Your Message", Toast.LENGTH_LONG).show();


                        mediaPlayer.setDataSource(""); // setup song from https://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                        mediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL

                    if(!mediaPlayer.isPlaying()){
                        mediaPlayer.start();
                        buttonPlayPause.setImageResource(R.drawable.img_btn_pause);
                    }else {
                        mediaPlayer.pause();
                        buttonPlayPause.setImageResource(R.drawable.img_btn_pause);
                    }

                    primarySeekBarProgressUpdater();
                }

            }
        });
    }

    class ReadDetail extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            arrayLvTitle = new ArrayList<>();
            arrayLinksBook = new ArrayList<>();

            XMLParser parser = new XMLParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element e = (Element) nodeList.item(i);
                arrayLvTitle.add(parser.getValue(e, "title"));
                arrayLinksBook.add(parser.getValue(e, "link"));

            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayLvTitle)
            {
                @Override
                public View getView(int position, View convertView, ViewGroup parent){
                    // Get the Item from ListView
                    View view = super.getView(position, convertView, parent);

                    // Initialize a TextView for ListView each Item
                    TextView tv = (TextView) view.findViewById(android.R.id.text1);

                    // Set the text color of TextView (ListView Item)
                    tv.setTextColor(Color.WHITE);

                    // Generate ListView Item using TextView
                    return view;
                }

            };
            lvBook.setAdapter(adapter);
        }
    }

    private String docNoiDung_Tu_URL(String theUrl){

        try    {
            StringBuilder content = new StringBuilder();
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
            return content.toString();
        }
        catch(Exception e)    {


            return "";
            //e.printStackTrace();
        }

    }
}
