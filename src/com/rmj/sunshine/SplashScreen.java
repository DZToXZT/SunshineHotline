package com.rmj.sunshine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.rmj.sunshine.media.AudioPlayer;
import com.rmj.sunshine.media.MediaService;
import com.rmj.sunshine.media.Status;
import io.vov.vitamio.LibsChecker;

public class SplashScreen extends Activity {
    /**
     * Called when the activity is first created.
     */

    public static Handler mHandler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        startService(new Intent(this, MediaService.class));
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Status.MEDIA_SERVICE_INITRIALIZED:
                        Intent _intent = new Intent(getApplicationContext(),AudioPlayer.class);
                        startActivity(_intent);
                        mHandler = null;
                    default:
                        finish();
                }
            }
        };
    }

}
