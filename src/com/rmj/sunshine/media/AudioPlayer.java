package com.rmj.sunshine.media;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.rmj.sunshine.R;
import io.vov.vitamio.MediaPlayer;

/**
 * Created by G11 on 2014/4/15.
 */
public class AudioPlayer extends Activity {
    public static Handler mHandler;
    ImageButton mPlayButton;
    Button mHotline1Button;
    Button mHotline2Button;
    ImageButton mVideoButton;
    ImageButton mBackButton;
    MediaManager mMediaManager;
    TextView mIntroduceTitle;
    TextView mIntroduceContent;
    ProgressBar mProgressBar;
    TextView mPlayTextView;
    SeekBar mAudioSeekBar;
    

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        initHandler();
        //LibChecker
        startMediaService();
        mMediaManager = MediaManager.getInstance();

        mPlayButton = (ImageButton) findViewById(R.id.media_btn_play);
        mHotline1Button = (Button) findViewById(R.id.media_btn_dial_1);
        mHotline2Button = (Button) findViewById(R.id.media_btn_dial_2);
        mIntroduceTitle = (TextView) findViewById(R.id.media_tv_title);
        mIntroduceContent = (TextView) findViewById(R.id.media_tv_content);
        mVideoButton = (ImageButton) findViewById(R.id.media_btn_video);
        mProgressBar = (ProgressBar) findViewById(R.id.probar);
        mBackButton = (ImageButton) findViewById(R.id.media_btn_back);
        mPlayTextView = (TextView) findViewById(R.id.media_tv_player_status);

        mAudioSeekBar = (SeekBar) findViewById(R.id.media_player_audio_seekbar);
        mAudioSeekBar.setEnabled(false);

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mMediaManager.isPlaying()) {
                    waiting();
                }
                MediaService.mHandler.sendEmptyMessage(Status.MEDIA_OPERATION_PLAY);
            }
        });
        mVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
                MediaService.mHandler.sendEmptyMessage(Status.MEDIA_OPERATION_PLAY_VIDEO);
            }
        });
        mHotline1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri _hotline1 = Uri.parse("tel:" + Urls.mHotline1);
                Intent _intent = new Intent(Intent.ACTION_DIAL,_hotline1);
                startActivity(_intent);
            }
        });
        mHotline2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri _hotline2 = Uri.parse("tel:" + Urls.mHotline2);
                Intent _intent = new Intent(Intent.ACTION_DIAL,_hotline2);
                startActivity(_intent);
            }
        });
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setIntroduce();
        if (MediaManager.mMediaPlayer == null) {
        	MediaManager.getInstance().setMediaPlayer(new MediaPlayer(getApplicationContext()));
		}
        if (mMediaManager.isPlaying()) {
            played();
        } else if (mMediaManager.isConnecting()) {
            waiting();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setIntroduce() {
        mIntroduceTitle.setText(mMediaManager.getProgrammeInfo().mTitle);
        mIntroduceContent.setText(mMediaManager.getProgrammeInfo().mContent);
    }

    public void startMediaService() {
        Intent _intent = new Intent(this,MediaService.class);
        startService(_intent);
    }

    public void played() {
        mPlayButton.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mPlayButton.setImageResource(getResources().getIdentifier("audio_player_pause", "drawable", getApplicationContext().getPackageName()));
        mPlayTextView.setText(getResources().getIdentifier("media_status_played","string",getApplicationContext().getPackageName()));
    }

    public void paused() {
        mPlayButton.setImageResource(getResources().getIdentifier("audio_player_play", "drawable", getApplicationContext().getPackageName()));
        mPlayTextView.setText(getResources().getIdentifier("media_status_paused","string",getApplicationContext().getPackageName()));
    }

    public void stop() {
        mPlayButton.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mPlayButton.setImageResource(getResources().getIdentifier("audio_player_play", "drawable", getApplicationContext().getPackageName()));
        mPlayTextView.setText(getResources().getIdentifier("media_status_paused","string",getApplicationContext().getPackageName()));
    }

    public void playVideo() {
        Intent _intent = new Intent(getApplicationContext(), VideoPlayer.class);
        _intent.putExtra("url", mMediaManager.getProgrammeInfo().mVideoUrl);
        startActivity(_intent);
    }

    public void waiting() {
        mPlayButton.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mPlayTextView.setText(getResources().getIdentifier("media_status_waiting","string",getApplicationContext().getPackageName()));
    }

    void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Status.MEDIA_STATUS_PLAYED:
                        played();
                        break;
                    case Status.MEDIA_STATUS_PAUSED:
                        paused();
                        break;
                    case Status.MEDIA_STATUS_STOPED:
                        break;
                    case Status.MEDIA_STATUS_WAITING:
                        waiting();
                        break;
                    case Status.MEDIA_OPETATION_START_VIDEO:
                        playVideo();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog _dialog = new AlertDialog.Builder(this).create();
            _dialog.setTitle("退出");
            _dialog.setMessage("您确定退出吗？");
            _dialog.setButton(AlertDialog.BUTTON_NEGATIVE,"退出",mExitDialogListener);
            _dialog.setButton(AlertDialog.BUTTON_NEUTRAL,"后台",mExitDialogListener);
            _dialog.setButton(AlertDialog.BUTTON_POSITIVE,"取消",mExitDialogListener);
            _dialog.show();
        }
        return false;
    }
    DialogInterface.OnClickListener mExitDialogListener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_NEGATIVE:
                    MediaService.mHandler.sendEmptyMessage(Status.MEDIA_STOP_SERVICE);
                    finish();
                    break;
                case AlertDialog.BUTTON_NEUTRAL:
                    finish();
                    break;
                case AlertDialog.BUTTON_POSITIVE:
                    break;
                default:
                    break;
            }
        }
    };

}