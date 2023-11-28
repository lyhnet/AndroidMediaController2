package com.example.volcont2;




import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;

public class VolumeService extends Service {
    private static final String TAG = "VolumeService";
    private VolumeReceiver volumeReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        volumeReceiver = new VolumeReceiver();
        registerReceiver(volumeReceiver, new IntentFilter("android.media.VOLUME_CHANGED_ACTION"));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        unregisterReceiver(volumeReceiver);
    }

    private class VolumeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
                int keyCode = intent.getIntExtra("android.media.EXTRA_VOLUME_KEY_CODE", -1);
                int currentVolume = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", -1);
                Log.d(TAG, "Current Volume: " + currentVolume);
                if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                    Log.d(TAG, "Volume Up button clicked");
                    // Handle Volume Up button click
                } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                    Log.d(TAG, "Volume Down button clicked");
                    // Handle Volume Down button click
                }
                // Add your custom logic here to handle volume changes
            }
        }
    }
}
