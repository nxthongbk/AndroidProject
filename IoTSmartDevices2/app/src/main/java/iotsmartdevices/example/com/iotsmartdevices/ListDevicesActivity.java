package iotsmartdevices.example.com.iotsmartdevices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;

public class ListDevicesActivity extends AppCompatActivity {
    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_devices);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list_of_devices);

        // Defined Array values to show in ListView
        String[] values = new String[] { "M080808081",
                "V201000001",
                "V201000002",
                "V101000001",
                "V102000001",
                "V103000001"
        };

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        //listView.setOnItemClickListener(new OnItemClickListener() {

          //  @Override
           // public void onItemClick(AdapterView<?> parent, View view,
            //                        int position, long id) {

                // ListView Clicked item index
               // int itemPosition     = position;

                // ListView Clicked item value
              //  String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                //Toast.makeText(getApplicationContext(),
                 //       "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                 //       .show();

           // }

       // });
    }

}
