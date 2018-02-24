package iotsmartdevices.example.com.iotsmartdevices;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.net.wifi.WifiManager;
import android.net.wifi.WifiConfiguration;

import java.util.List;
import android.net.NetworkInfo;
import android.net.ConnectivityManager;
import android.content.Context;
public class QrCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        try {
            QrScanner(mScannerView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void QrScanner(View view){


        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);

        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();         // Start camera

    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here

        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();

        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);

        String[] separated = rawResult.getText().split("::");
        try {
            connectToDevice(separated[2],separated[3]);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        runUdpClient();
    }

    private void  connectToDevice (String txtNetworkName, String txtPassword) throws InterruptedException {

        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        for (WifiConfiguration wifiConfiguration1: wifiManager.getConfiguredNetworks()) {
            wifiManager.disableNetwork(wifiConfiguration1.networkId);
        }

        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        Log.d("txtNetworkName", txtNetworkName);
        Log.d("txtPassword", txtPassword);
        wifiConfiguration.SSID = String.format("\"%s\"", txtNetworkName);
        wifiConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);

        wifiConfiguration.status = WifiConfiguration.Status.ENABLED;
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.NONE);
        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);

        wifiConfiguration.preSharedKey = String.format("\"%s\"", txtPassword);

        int netId = wifiManager.addNetwork(wifiConfiguration);
        Log.d("netId", String.valueOf(netId));
        if (wifiManager.isWifiEnabled()) { //---wifi is turned on---
            //---disconnect it first---
            wifiManager.disconnect();
        } else { //---wifi is turned off---
            //---turn on wifi---
            wifiManager.setWifiEnabled(true);
        }

        Log.d("wifiManager11", "Ok111111111111111111111");
        boolean networkEnabled = wifiManager.enableNetwork(netId, true);
        if (networkEnabled) {
            Log.d("networkEnabled", "networkEnabled");
        } else {
            Log.d("networkEnabled", "No networkEnabled");
        }

        boolean reconnected = wifiManager.reconnect();
        if (reconnected) {
            Log.d("reconnected", "reconnected");
        } else {

            Log.d("reconnected", "No reconnected");
        }

        ConnectivityManager connectivityMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();;

        int iii = 0;
        while (!(networkInfo != null && networkInfo.isConnected())) {
  /*          if(iii>0){
                networkEnabled = wifiManager.enableNetwork(netId, true);
                if (networkEnabled) {
                    Log.d("networkEnabled", "networkEnabled");
                } else {
                    Log.d("networkEnabled", "No networkEnabled");
                }
                reconnected = wifiManager.reconnect();
                if (reconnected) {
                    Log.d("reconnected", "reconnected");
                } else {

                    Log.d("reconnected", "No reconnected");
                }
            }*/
            Log.d("Wait", "Wait for connected 33");
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            networkInfo = connectivityMgr.getActiveNetworkInfo();
            iii++;
        }
    }
    private static final int UDP_SERVER_PORT = 2390;
    private void runUdpClient()  {

        String udpMsg = "Y";

        DatagramSocket ds = null;
        Log.d("wifiManager11", "Ok33333333333333");
        try {

            ds = new DatagramSocket();

            InetAddress serverAddr = InetAddress.getByName("192.168.4.1");

            DatagramPacket dp;
            Log.d("wifiManager11", "Ok444444444444");
            dp = new DatagramPacket(udpMsg.getBytes(), udpMsg.length(), serverAddr, UDP_SERVER_PORT);
            Log.d("wifiManager11", "Ok5555555555555555");
            ds.send(dp);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.d("Sendudp", "A1111");
            ds.send(dp);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.d("Sendudp", "A2222");
            ds.send(dp);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.d("Sendudp", "A3333");

        } catch (SocketException e) {

            e.printStackTrace();

        }catch (UnknownHostException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (ds != null) {

                ds.close();
                Log.d("wifiManager11", "Ok77777777");
            }
            Log.d("wifiManager11", "Ok88888888");
        }

    }
}
