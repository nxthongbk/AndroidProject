package com.tma.watermonitoring;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {
    LatLng sys1,sys2,sys3,sys4,sys5;
    String water;
    public int waterInt;
    private GoogleMap mMap;
    public String access_token = new String();
    final Handler handler = new Handler();
    private MainThread thread;
    boolean next_request = true;
    int trying_count = 0;
    IconGenerator iconFactory;
    boolean sent_notification = false;

    private NotificationCompat.Builder notBuilder;

    private static final int MY_NOTIFICATION_ID = 12345;

    private static final int MY_REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        iconFactory= new IconGenerator(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        this.notBuilder = new NotificationCompat.Builder(this);

        // Thông báo sẽ tự động bị hủy khi người dùng click vào Panel

        this.notBuilder.setAutoCancel(true);


        GetAccesstoken("https://eu.airvantage.net/api/oauth/token?grant_type=password&username=nxthong@tma.com.vn&password=1_Abc_123&client_id=b3190131af6b43b4ba1427aed5030bda&client_secret=b4c128c300f64b108e8e3177f00d0adc");
        Thread thread = new Thread() {
            @Override
            public void run() {
                while(true) {
//                        Toast.makeText(MainActivity.this, "Thread is running", Toast.LENGTH_SHORT).show();
                    if (access_token != "") {
                        if (next_request) {
                            GetCurrentWater(access_token);
                            next_request = false;

                            //gaugeChart.setAngle(10);
                            try {
                                Thread.sleep(5000);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            trying_count++;
                            if (trying_count > 5) {
                                next_request = true;
                                trying_count = 0;
                            }
                        }
//                            Toast.makeText(MainActivity.this, String.valueOf(_an), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        thread.start();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sys1 = new LatLng(10.7709998,106.6860042);
        sys1 = new LatLng(10.7893147,106.7130203);
        sys2 = new LatLng(10.7825233,106.6706836);
        sys3 = new LatLng(10.810344,106.728881);
        sys4 = new LatLng(10.8167119,106.704271);
        sys5 = new LatLng(10.8101246,106.6915425);


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sys1,15));

    }


    public void GetAccesstoken(String data) {

        RequestQueue requestQueue = Volley.newRequestQueue(MapsActivity.this);
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
            public void onErrorResponse(com.android.volley.VolleyError error) {

            }

        });
        requestQueue.add(stringRequest);
    }
    public void GetCurrentWater(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MapsActivity.this);
        String url = "https://eu.airvantage.net/api/v1/systems/data/raw?&targetIds=d5b629ad287444a2a14f24d711ac400d&dataIds=le_waterMonitor.1000.0.1&client_id=b3190131af6b43b4ba1427aed5030bda&access_token=" + data;
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectMain = jsonObject.getJSONObject("d5b629ad287444a2a14f24d711ac400d");
                            JSONArray jsonArrayObject = jsonObjectMain.getJSONArray("le_waterMonitor.1000.0.1");
                            JSONObject jsonSubObject = jsonArrayObject.getJSONObject(0);
                            water = jsonSubObject.getString("v");
                            Double water1 = Double.valueOf(water);



                            // waterInt = Integer.parseInt(water);
                            ///  Toast.makeText(MapsActivity.this, String.valueOf(waterInt), Toast.LENGTH_SHORT).show();

                            // Double Weight1 = Math.round(Weight * 100.0) / 100.0;
                            //tvWeight.setText(String.valueOf(Weight1 + "Kg"));
                            //  _an = Integer.parseInt(weight);
//                            if (gauge_access) {
//                                gaugeChart.setAngle(60);


//                            }
                            System.out.println(water);


                            if(water1 >= 5.0)
                            {

                                mMap.addMarker(new MarkerOptions()
                                        .title("Nguyễn Hữu Cảnh")
                                        .position(sys1)
                                        .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Ngập: "+water+" cm")))
                                        .anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV()));
                                Circle circle = mMap.addCircle(new CircleOptions()
                                        .center(sys1)
                                        .radius(water1*8)
                                        .strokeColor(Color.RED)
                                        .fillColor(Color.BLUE));
                                //Location 2
                                mMap.addMarker(new MarkerOptions()
                                        .title("Cách Mạng Tháng Tám")
                                        .position(sys2)
                                        .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Ngập: 17 cm")))
                                        .anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV()));
                                Circle circle2 = mMap.addCircle(new CircleOptions()
                                        .center(sys2)
                                        .radius(water1*8)
                                        .strokeColor(Color.RED)
                                        .fillColor(Color.BLUE));


                                //Location 3
                                mMap.addMarker(new MarkerOptions()
                                        .title("Nguyễn Văn Huởng")
                                        .position(sys3)
                                        .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Ngập: 45 cm")))
                                        .anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV()));
                                Circle circle3 = mMap.addCircle(new CircleOptions()
                                        .center(sys3)
                                        .radius(water1*8)
                                        .strokeColor(Color.RED)
                                        .fillColor(Color.BLUE));

                                //Location 4
                                mMap.addMarker(new MarkerOptions()
                                        .title("Nguyễn Xí")
                                        .position(sys4)
                                        .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Ngập: 10 cm")))
                                        .anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV()));
                                Circle circle4 = mMap.addCircle(new CircleOptions()
                                        .center(sys4)
                                        .radius(water1*8)
                                        .strokeColor(Color.RED)
                                        .fillColor(Color.BLUE));

                                //Location 5
                                mMap.addMarker(new MarkerOptions()
                                        .title("Lê Quang D")
                                        .position(sys5)
                                        .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Ngập: 12 cm")))
                                        .anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV()));
                                Circle circle5 = mMap.addCircle(new CircleOptions()
                                        .center(sys5)
                                        .radius(water1*8)
                                        .strokeColor(Color.RED)
                                        .fillColor(Color.BLUE));
                                if (sent_notification == false) {
                                    notiButtonClicked(MapsActivity.this);
                                    sent_notification = true;
                                }
                                else {

                                }
                             }
                            else
                            {
                                sent_notification = false;
                                mMap.addMarker(new MarkerOptions()
                                        .title("TMA Solutions")
                                        .snippet("Ngập: "+water)
                                        .position(sys1)
                                        .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Ngập: "+water+" cm")))
                                        .anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV()));
                                Circle circle = mMap.addCircle(new CircleOptions()
                                        .center(sys1)
                                        .radius(water1*2)
                                        .strokeColor(Color.RED)
                                        .fillColor(Color.BLUE));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(com.android.volley.VolleyError error) {
            }

        });
        requestQueue.add(stringRequest);
    }


    @Override
    public void onMapLoaded() {

    }
    public void notiButtonClicked(MapsActivity view)  {


        this.notBuilder.setSmallIcon(R.drawable.noti_icon_filter_green);
        this.notBuilder.setTicker("This is a ticker");
///set time
        //configure some title

        this.notBuilder.setWhen(System.currentTimeMillis()+ 10* 10);
        this.notBuilder.setContentTitle("Duờng Nguyễn Hữu Cảnh!!!");
        this.notBuilder.setContentText("Ngập hơn " + water + "cm");


        Intent intent = new Intent(this, MapsActivity.class);



        PendingIntent pendingIntent = PendingIntent.getActivity(this, MY_REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);


        this.notBuilder.setContentIntent(pendingIntent);

        // Lấy ra dịch vụ thông báo

        NotificationManager notificationService  =
                (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);

        // Xây dựng thông báo và gửi nó lên hệ thống.

        Notification notification =  notBuilder.build();
        notificationService.notify(MY_NOTIFICATION_ID, notification);

    }
}
