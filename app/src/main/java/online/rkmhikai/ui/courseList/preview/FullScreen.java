package online.rkmhikai.ui.courseList.preview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import online.rkmhikai.R;

public class FullScreen extends AppCompatActivity {
    PlayerView playerViewFull;
    ProgressBar progressBarFull;
    ImageView btnFullscreenFull;

    SimpleExoPlayer simpleExoPlayerFull;
    long seek;
    Uri videoUrl;
    int subjectId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_full_screen);

        //Initialization
        playerViewFull = findViewById(R.id.player_view_fullscreen);
        progressBarFull = findViewById(R.id.progress_bar);
        btnFullscreenFull = findViewById(R.id.bt_fullscreen);
        btnFullscreenFull.setImageDrawable(getResources().getDrawable(R.drawable.exo_controls_fullscreen_exit));


        Bundle bundle = getIntent().getExtras();
        seek = bundle.getLong("seek");
        videoUrl = Uri.parse(bundle.getString("path"));
        subjectId=bundle.getInt("Subject_ID");
        iniExoplayer(seek,videoUrl);

        btnFullscreenFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Log.i("TAG","sjdvbdskjvb");

                Intent intent = new Intent(FullScreen.this, PlayerActivity.class);
                intent.putExtra("seek",simpleExoPlayerFull.getCurrentPosition());
                intent.putExtra("path",videoUrl.toString());
                intent.putExtra("Subject_ID",subjectId);
                Log.d("FULLSCREEN", "subject_ID: "+subjectId);
                simpleExoPlayerFull.release();
                FullScreen.this.finish();
                startActivity(intent);

            }
        });
    }
    //To Initialise Simple Exoplayer
    private void iniExoplayer(long seek,Uri videoUrl) {

        //Initialize load control
        LoadControl loadControl = new DefaultLoadControl();
        //Initialize band width meter
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        //Initialize track selector
        TrackSelector trackSelector = new DefaultTrackSelector(
                new AdaptiveTrackSelection.Factory(bandwidthMeter)
        );

        simpleExoPlayerFull = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);

        playerViewFull.setPlayer(simpleExoPlayerFull);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this,"appname"));
        MediaSource videosource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource((videoUrl));
        simpleExoPlayerFull.prepare(videosource);
        simpleExoPlayerFull.seekTo(seek-2);
        simpleExoPlayerFull.setPlayWhenReady(true);

        simpleExoPlayerFull.addListener(new com.google.android.exoplayer2.Player.DefaultEventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                super.onPlayerStateChanged(playWhenReady, playbackState);

                //Check condition
                if (playbackState == com.google.android.exoplayer2.Player.STATE_BUFFERING) {
                    //Show progress bar
                    progressBarFull.setVisibility(View.VISIBLE);
                } else if (playbackState == com.google.android.exoplayer2.Player.STATE_READY) {
                    //hide progress bar
                    progressBarFull.setVisibility(View.GONE);
                }
            }
        });
    }
}