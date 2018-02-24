package vn.com.nxt.nhacthaigiao;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import java.io.IOException;
import java.util.Set;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Created by AutoUsr on 29/08/2017.
 */

public class PlayAudio extends Service  {

    private static final String LOGCAT = null;
    MediaPlayer objPlayer;
    String resultReceiver;
    int length;

    final static String SENDMESAGGE = "passMessage";
    public static Boolean serviceStatus = false;

    public void onCreate(){
        super.onCreate();
        serviceStatus=true;

        objPlayer = new MediaPlayer();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            resultReceiver = intent.getStringExtra("links");


            if (resultReceiver.contains("mp3")) {
                if(objPlayer.isPlaying())
                {
                    objPlayer.stop();
                    objPlayer.release();
                }
                serviceStatus=true;
                passMessageToActivity("loading");

                objPlayer = new MediaPlayer();
                objPlayer.setDataSource(getApplicationContext(), Uri.parse(resultReceiver));
                objPlayer.prepareAsync();
                objPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {

                        passMessageToActivity("Duration"+objPlayer.getDuration());
                        objPlayer.start();
                        passMessageToActivity("loaddone");
                    }
                });

            }
            else if(resultReceiver.equals("invert")){
                if(objPlayer.isPlaying())
                {
                    objPlayer.pause();
                    length=objPlayer.getCurrentPosition();
                }
                else
                {
                    objPlayer.seekTo(length);
                    objPlayer.start();

                }
            }
            else
            {


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Service.START_STICKY;
    }

    public void onStop(){
        objPlayer.stop();
        objPlayer.release();
    }

    public void onPause(){
        objPlayer.stop();
        objPlayer.release();
    }

    public void onDestroy(){
        passMessageToActivity("destroy");
        serviceStatus=false;

        objPlayer.stop();
        objPlayer.release();
    }

    private void passMessageToActivity(String message){
        Intent intent = new Intent();
        intent.setAction(SENDMESAGGE);
        intent.putExtra("message",message);
        sendBroadcast(intent);
    }

    @Override
    public IBinder onBind(Intent objIndent) {
        return null;
    }
}
