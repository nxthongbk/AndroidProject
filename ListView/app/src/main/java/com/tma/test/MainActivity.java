package com.tma.test;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import static android.support.constraint.R.id.parent;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    String type;
    ArrayList<String> arrayLv;
    ArrayList<String> arrayLinks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        type = i.getStringExtra("type");


        switch (type) {
            case "0":
                setTitle("Sách Kinh Doanh");
                break;
            case "1":
                setTitle("Tiểu Thuyết");
                break;
            case "2":
                setTitle("Truyện Ngắn");
                break;
            default:
                setTitle("Sách Nghe");
                break;

        }


        lv = (ListView) findViewById(R.id.lvperson);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadDetail().execute("https://thuegovap.000webhostapp.com/xml/booklist.xml");
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                String message = arrayLinks.get(position);
                intent.putExtra(EXTRA_MESSAGE, message);
                intent.putExtra("Description", arrayLv.get(position));
                startActivity(intent);
            }
        });
    }

    class ReadDetail extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            arrayLv = new ArrayList<>();
            arrayLinks = new ArrayList<>();

            XMLParser parser = new XMLParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");


            for (int i = 0; i < nodeList.getLength(); i++) {
                Element e = (Element) nodeList.item(i);
                String currentType = parser.getValue(e,"type");
                if (currentType.equals(type)) {
                    arrayLv.add(parser.getValue(e, "title"));
                    arrayLinks.add(parser.getValue(e, "link"));
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayLv)
            //ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayLv)
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
            lv.setAdapter(adapter);
        }
    }


    private String docNoiDung_Tu_URL(String theUrl){
        StringBuilder content = new StringBuilder();
        try    {



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
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
        return content.toString();



    }
}

