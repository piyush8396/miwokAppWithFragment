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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class familyActivity extends AppCompatActivity {
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
        final ArrayList<numbers>families=new ArrayList<>();
        families.add(new numbers("Father","әpә",R.drawable.family_father,R.raw.family_father));
        families.add(new numbers("Mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        families.add(new numbers("Son","angsi",R.drawable.family_son,R.raw.family_son));
        families.add(new numbers("Daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        families.add(new numbers("Older Brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        families.add(new numbers("Younger Brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        families.add(new numbers("Older Sister","teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        families.add(new numbers("Younger Sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        families.add(new numbers("Grand Mother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        families.add(new numbers("Grand father","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));
        numberAdapter fAdapter=new numberAdapter(this,families,R.color.category_family);
        ListView familylist=(ListView)findViewById(R.id.list);
        familylist.setAdapter(fAdapter);
        familylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(numbersActivity.this,"clicked",Toast.LENGTH_SHORT).show();
                numbers num=families.get(i);
                releaseMedia();
                int result = audioManager.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mp=MediaPlayer.create(familyActivity.this,num.getMedia());
                mp.start();
                mp.setOnCompletionListener(m);}

            }
        });


    }
    private void releaseMedia()
    {
        if(mp!=null){
            mp.release();
            mp=null;}
        audioManager.abandonAudioFocus(afChangeListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        releaseMedia();
    }
}