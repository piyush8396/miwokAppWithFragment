package com.example.mowiklanguageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class phrasesActivity extends AppCompatActivity {
 private MediaPlayer mp;
    AudioManager audioManager ;
    AudioManager.OnAudioFocusChangeListener afChangeListener= new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Permanent loss of audio focus
                // Pause playback immediately
                releaseMedia();
                Log.v("phrasesActivity","loss");
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                mp.pause();
                mp.seekTo(0);
                Log.v("phrasesActivity","loss transient");
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Lower the volume, keep playing
                mp.pause();
                mp.seekTo(0);
                Log.v("phrasesActivity","loss duck");
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Your app has been granted audio focus again
                // Raise volume to normal, restart playback if necessary
                mp.start();
                Log.v("phrasesActivity","loss gain");
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
        final ArrayList<numbers>Phrases=new ArrayList<>();
        Phrases.add(new numbers("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        Phrases.add(new numbers("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        Phrases.add(new numbers("My name is..","oyaaset...",R.raw.phrase_my_name_is));
        Phrases.add(new numbers("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        Phrases.add(new numbers("I’m feeling good.", "kuchi achit",R.raw.phrase_im_feeling_good));
        Phrases.add(new numbers("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        Phrases.add(new numbers("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        Phrases.add(new numbers("I’m coming.","әәnәm",R.raw.phrase_im_coming));
        Phrases.add(new numbers("Let’s go.", "yoowutis",R.raw.phrase_lets_go));
        Phrases.add(new numbers("Come here.","әnni'nem",R.raw.phrase_come_here));
        numberAdapter phrase=new  numberAdapter(this,Phrases,R.color.category_phrases);
        ListView phraselist=(ListView)findViewById(R.id.list);
        phraselist.setAdapter(phrase);
        phraselist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(numbersActivity.this,"clicked",Toast.LENGTH_SHORT).show();
                numbers num=Phrases.get(i);
                releaseMedia();
                int result = audioManager.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mp= MediaPlayer.create(phrasesActivity.this,num.getMedia());
                mp.start();
                mp.setOnCompletionListener(m);}

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