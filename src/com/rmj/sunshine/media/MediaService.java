package com.rmj.sunshine.media;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import io.vov.vitamio.MediaPlayer;

/**
 * Created by G11 on 2014/4/16.
 */
public class MediaService extends Service {
    MediaManager mMediaManager;
    public static Handler mHandler;

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initHandler();
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

    void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Status.MEDIA_OPERATION_PLAY:
                        if (mMediaManager.isPlaying()) {
                            pause();
                        } else {
                            play();
                        }
                        break;
                    case Status.MEDIA_OPERATION_PLAY_VIDEO:
                        playVideo();
                        break;
                    case Status.MEDIA_OPERATION_STOP:
                        stop();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    void play() {
        mMediaManager.play();
        Player.mHandler.sendEmptyMessage(Status.MEDIA_STATUS_PLAYED);
    }

    void pause() {
        mMediaManager.pause();
        Player.mHandler.sendEmptyMessage(Status.MEDIA_STATUS_PAUSED);
    }

    void stop() {
        mMediaManager.stop();
    }

    void playVideo() {
//        if (mMediaManager.isPlaying()) {
//            pause();
//        }
        stop();
    }
}
