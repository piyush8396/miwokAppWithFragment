package com.example.mowiklanguageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class colorActivity extends AppCompatActivity {
private MediaPlayer mp;
    AudioManager audioManager ;
    AudioManager.OnAudioFocusChangeListener afChangeListener= new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Permanent loss of audio focus
                // Pause playback immediately
                releaseMedia();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                mp.pause();
                mp.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Lower the volume, keep playing
                mp.pause();
                mp.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Your app has been granted audio focus again
                // Raise volume to normal, restart playback if necessary
                mp.start();
            }
        }
    };
    private MediaPlayer.OnCompletionListener m=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMedia();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<numbers> colors=new ArrayList<>();
        colors.add(new numbers("red","weṭeṭṭi",R.drawable.color_red,R.raw.color_red));
        colors.add(new numbers("green","chokokki",R.drawable.color_green,R.raw.color_green));
        colors.add(new numbers("brown","ṭakaakki",R.drawable.color_brown,R.raw.color_brown));
        colors.add(new numbers("Gray","ṭopoppi",R.drawable.color_gray,R.raw.color_gray));
        colors.add(new numbers("Black","kululli",R.drawable.color_black,R.raw.color_black));
        colors.add(new numbers("White","kelelli",R.drawable.color_white,R.raw.color_white));
        colors.add(new numbers("Dusty Yellow","ṭopiisә",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        colors.add(new numbers("Mustard Yellow","chiwiiṭә",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));
         numberAdapter cadapter=new numberAdapter(this,colors,R.color.category_colors);
        ListView listView=(ListView) findViewById(R.id.list);
        listView.setAdapter(cadapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(numbersActivity.this,"clicked",Toast.LENGTH_SHORT).show();
                numbers num = colors.get(i);
                releaseMedia();
                int result = audioManager.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mp = MediaPlayer.create(colorActivity.this, num.getMedia());
                    mp.start();
                    mp.setOnCompletionListener(m);
                }


            }
    });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMedia();
    }

    private void releaseMedia()
    {
        if(mp!=null){
            mp.release();
            mp=null;}
        audioManager.abandonAudioFocus(afChangeListener);
    }
}