package com.rmj.sunshine.media;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.rmj.sunshine.R;
import io.vov.vitamio.LibsChecker;

/**
 * Created by G11 on 2014/4/15.
 */
public class Player extends Activity {
    ImageButton mPlayButton;
    Button mHotline1Button;
    Button mHotline2Button;
    ImageButton mVideoButton;
    MediaManager mMediaManager;
    TextView mIntroduceTitle;
    TextView mIntroduceContent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        startMediaService();
        mMediaManager = MediaManager.getInstance();
        mMediaManager.initProgrammeInfo();

        mPlayButton = (ImageButton) findViewById(R.id.media_btn_play);
        mHotline1Button = (Button) findViewById(R.id.media_btn_dial_1);
        mHotline2Button = (Button) findViewById(R.id.media_btn_dial_2);
        mIntroduceTitle = (TextView) findViewById(R.id.media_tv_title);
        mIntroduceContent = (TextView) findViewById(R.id.media_tv_content);
        mVideoButton = (ImageButton) findViewById(R.id.media_btn_video);

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMediaManager.isPlaying()) {
                    pause();
                } else {
                    play();
                }
            }
        });
        mVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMediaManager.isPlaying()) {
                    pause();
                }
                Intent _intent = new Intent(getApplicationContext(), VideoPlayer.class);
                _intent.putExtra("url", mMediaManager.getProgrammeInfo().mVideoUrl);
                startActivity(_intent);
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        setIntroduce();
    }

    public void setIntroduce() {
        mIntroduceTitle.setText(mMediaManager.getProgrammeInfo().mTitle);
        mIntroduceContent.setText(mMediaManager.getProgrammeInfo().mContent);
    }

    public void startMediaService() {
        Intent _intent = new Intent(this,MediaService.class);
        startService(_intent);
    }

    public void play() {
        mPlayButton.setImageResource(getResources().getIdentifier("audio_player_pause", "drawable", getApplicationContext().getPackageName()));
        mMediaManager.play();
    }

    public void pause() {
        mMediaManager.pause();
        mPlayButton.setImageResource(getResources().getIdentifier("audio_player_play", "drawable", getApplicationContext().getPackageName()));
    }
}