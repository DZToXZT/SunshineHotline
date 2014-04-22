package com.rmj.sunshine.media;

import android.content.Context;
import android.widget.FrameLayout;

/**
 * Created by G11 on 2014/4/22.
 */
public class MediaController extends FrameLayout{

    public MediaController(Context context) {
        super(context);
    }

    public interface MediaPlayerControl {
        void start();
        void pause();
        long getDuration();
        long getCurrentPosition();
        void seekTo(long pos);
        boolean isPlaying();
        int getBufferPercentage();
    }
}
