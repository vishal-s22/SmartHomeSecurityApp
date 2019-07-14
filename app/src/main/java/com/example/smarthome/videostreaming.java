package com.example.smarthome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class videostreaming extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videostreaming);

        final VideoView videoView;

        videoView = (VideoView)findViewById(R.id.videoView);

        videoView.setVideoPath("http://192.168.1.168:8554");

        videoView.start();

    }

}