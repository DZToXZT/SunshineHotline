package com.rmj.sunshine.media;

import io.vov.vitamio.MediaPlayer;
import java.io.IOException;

/**
 * Created by G11 on 2014/4/16.
 */
public class MediaManager {
    public static MediaPlayer mMediaPlayer;
    private static MediaManager mInstance;
    public ProgrammeInfo mCurrentProgramme;
    boolean isNewProgramme;

    private MediaManager() {
    }

    public static MediaManager getInstance() {
        if (mInstance == null) {
            mInstance = new MediaManager();
        }
        return mInstance;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        if (mMediaPlayer == null) {
            mMediaPlayer = mediaPlayer;
        } else {
            mMediaPlayer.release();
            mMediaPlayer = mediaPlayer;
        }
    }

    public void setProgrammeInfo(ProgrammeInfo info) {
        mCurrentProgramme = info;
        isNewProgramme = true;
    }

    public ProgrammeInfo getProgrammeInfo() {
        return mCurrentProgramme;
    }

    public void initProgrammeInfo() {
        ProgrammeInfo _info = new ProgrammeInfo();
        _info.mUrl = Urls.mAudioLive;
        _info.mTitle = "4月14日节目：中国建设银行河北省分行嘉宾做客直播间";
        _info.mContent = "中国建设银行河北省分行党委书记、行长李秀昆带队做客《阳光热线》，解答咨询，受理投诉。";
        setProgrammeInfo(_info);
    }

    public void play() {
        if (isNewProgramme) {
            try {
                mMediaPlayer.setDataSource(mCurrentProgramme.mUrl);
                mMediaPlayer.prepare();
                isNewProgramme = false;
            } catch (IOException e) {}
        }
        mMediaPlayer.start();
    }

    public void pause() {
        mMediaPlayer.pause();

    }

    public void stop() {
        mMediaPlayer.stop();
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public void release() {
        mMediaPlayer.release();
        mMediaPlayer = null;
    }
}
