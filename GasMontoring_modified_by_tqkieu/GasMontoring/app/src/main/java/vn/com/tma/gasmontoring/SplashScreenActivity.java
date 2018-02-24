package vn.com.tma.gasmontoring;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import vn.com.tma.gasmontoring.utils.SharedPreferenceUtils;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreenActivity extends AppCompatActivity {
    private Handler showSplashScreen = new Handler();
    private static final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fullScreen();
        setContentView(R.layout.activity_splash_screen);

        showSplashScreen.postDelayed(showSplashRunnable, SPLASH_TIME_OUT);
            }

    private void fullScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    Runnable showSplashRunnable = new Runnable() {
        @Override
        public void run() {
                 Intent i = new Intent(SplashScreenActivity.this,MainActivity.class);
                startActivity(i);
                SplashScreenActivity.this.finish();
            }


    };
}
