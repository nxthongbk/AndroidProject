package vn.com.tma.gasmontoring;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.numetriclabz.numandroidcharts.ChartData;
import com.numetriclabz.numandroidcharts.GaugeChart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView tvWeight;
    ProgressDialog pd;
    GoogleMap map;

    GaugeChart gaugeChart;
    final Handler handler = new Handler();

    public String access_token = new String();
    boolean next_request = true;
    int trying_count = 0;
    public int _an;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);
        tvWeight = (TextView) findViewById(R.id.tvWeight);
        List values = new ArrayList<>();

        gaugeChart = (GaugeChart) findViewById(R.id.gauge_chart);
        values.add(new ChartData(25f));
        values.add(new ChartData(35f));
        values.add(new ChartData(55f));
        gaugeChart.setData(values);

        gaugeChart.setAngle(0);


        Getaccesstoken("https://eu.airvantage.net/api/oauth/token?grant_type=password&username=nxthong@tma.com.vn&password=1_Abc_123&client_id=b3190131af6b43b4ba1427aed5030bda&client_secret=b4c128c300f64b108e8e3177f00d0adc");

        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (access_token != "") {
                        if (next_request) {

                            System.out.println(_an);

//                            List tmp_val = new ArrayList<>();
//                            tmp_val.add(new ChartData(25f));
//                            tmp_val.add(new ChartData(35f));
//                            tmp_val.add(new ChartData(55f));
//                            gaugeChart.setData(tmp_val);
                            gaugeChart.setAngle(_an);
                            gaugeChart.postInvalidate();

                            GetCurrentGas(access_token);

                            next_request = false;


                            //gaugeChart.setAngle(10);
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            trying_count++;
                            if (trying_count > 2) {
                                next_request = true;
                                trying_count = 0;
                            }
                        }
//                            Toast.makeText(MainActivity.this, String.valueOf(_an), Toast.LENGTH_SHORT).show();
                    } else {
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        thread.start();

    }

    public void Getaccesstoken(String data) {

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = data;
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            access_token = jsonObject.getString("access_token");
//                            tvWeight.setText(access_token);
//                            _finish = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(stringRequest);

    }

    public void GetCurrentGas(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://eu.airvantage.net/api/v1/systems/data/raw?&targetIds=e123d57f9cc345efa3ebc2f718780113&dataIds=le_gasMonitoring.1000.0.1&client_id=b3190131af6b43b4ba1427aed5030bda&access_token=" + data;

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectMain = jsonObject.getJSONObject("e123d57f9cc345efa3ebc2f718780113");
                            JSONArray jsonArrayObject = jsonObjectMain.getJSONArray("le_gasMonitoring.1000.0.1");
                            JSONObject jsonSubObject = jsonArrayObject.getJSONObject(0);
                            String weight = jsonSubObject.getString("v");
                            Double Weight = Double.parseDouble(weight);
                            Double Weight1 = Math.round(Weight * 100.0) / 100.0;
                            tvWeight.setText(String.valueOf(Weight1 + "Kg"));
                            Double tmp = Weight * 9;
                            _an = tmp.intValue();

//                            System.out.println(Weight);
//                            System.out.println(_an);

                            next_request = true;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng sys = new LatLng(10.8559956, 106.62886);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sys, 15));

        map.addMarker(new MarkerOptions()
                .title("Vietnam work")
                .snippet("Center")
                .position(sys));
    }


    public class MyIntentService extends IntentService {

        public MyIntentService(String name) {
            // Used to name the worker thread
            // Important only for debugging
            super(MyIntentService.class.getName());

            setIntentRedelivery(true);

        }

        @Override
        protected void onHandleIntent(Intent intent) {
            // Invoked on the worker thread
            // Do some work in background without affecting the UI thread

        }
    }
}
