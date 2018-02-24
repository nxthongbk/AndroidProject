package vn.com.tma.gasmontoring;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by nhokm on 08/16/2017.
 */
public class GasIntentService extends IntentService {
    private static final String TAG ="vn.com.tma.gasmonitoring";
    public GasIntentService(){
        super("GasIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG,"The service starting");
    }
}
