package com.example.videoeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity {

    PlayerView videoView;
    ExoPlayer exoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = findViewById(R.id.ep_video_view);

        initializePlayer();

    }


    private void initializePlayer() {

        DefaultTrackSelector trackSelector = new  DefaultTrackSelector();
        DefaultLoadControl loadControl =new DefaultLoadControl();
        DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(this);

        exoPlayer = ExoPlayerFactory.newSimpleInstance(
                renderersFactory, trackSelector, loadControl);
    play();
    }

    private void play()
    {
        //1
        String userAgent = Util.getUserAgent(this, this.getString(R.string.app_name));
        //2
        MediaSource mediaSource = new ExtractorMediaSource.Factory(new DefaultDataSourceFactory(this, userAgent))
                .setExtractorsFactory(new DefaultExtractorsFactory())
                .createMediaSource(Uri.parse("https://www.youtube.com/watch?v=JpgkVvePY2w"));
        //3
        exoPlayer.prepare(mediaSource);
        //4
        exoPlayer.setPlayWhenReady(true);
    }


}
