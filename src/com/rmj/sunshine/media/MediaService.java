package com.rmj.sunshine.media;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import io.vov.vitamio.MediaPlayer;

/**
 * Created by G11 on 2014/4/16.
 */
public class MediaService extends Service {
    MediaManager mMediaManager;
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaManager = MediaManager.getInstance();
        mMediaManager.setMediaPlayer(new MediaPlayer(this));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mMediaManager.release();
        super.onDestroy();
    }
}
