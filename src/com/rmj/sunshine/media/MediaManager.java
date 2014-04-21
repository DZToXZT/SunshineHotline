package com.rmj.sunshine.media;

import android.content.Context;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.utils.ContextUtils;

import java.io.IOException;

/**
 * Created by G11 on 2014/4/16.
 */
public class MediaManager {
    public static MediaPlayer mMediaPlayer;
    private static MediaManager mInstance;
    public ProgrammeInfo mCurrentProgramme;
    boolean isNewProgramme;
    boolean isConnecting = false;

    private MediaManager() {
		initProgrammeInfo();
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
        _info.mVideoUrl = Urls.mVideoLive;
        setProgrammeInfo(_info);
    }

    public void play() {
        if (isNewProgramme) {
            try {
				stop();
                mMediaPlayer.setDataSource(mCurrentProgramme.mUrl);
                mMediaPlayer.prepare();
                isNewProgramme = false;
            } catch (IOException e) {
            }
        }
		isConnecting = false;
        mMediaPlayer.start();
    }

    public void pause() {
        mMediaPlayer.pause();

    }

    public void stop() {
        mMediaPlayer.stop();
        mMediaPlayer.reset();
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }
	public boolean isConnecting() {
    	return isConnecting;
    }

    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mInstance = null;
    }
}
