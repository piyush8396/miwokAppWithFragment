package com.example.mowiklanguageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class numbersActivity extends AppCompatActivity {
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
        audioManager = (AudioManager) numbersActivity.this.getSystemService(Context.AUDIO_SERVICE);
       final ArrayList<numbers> numbers=new ArrayList<>();
        numbers.add(new numbers("lutti","One",R.drawable.number_one,R.raw.number_one));
        numbers.add(new numbers("otiiko","Two",R.drawable.number_two,R.raw.number_two));
        numbers.add(new numbers("tolookosu","Three",R.drawable.number_three,R.raw.number_three));
        numbers.add(new numbers("oyyisa","Four",R.drawable.number_four,R.raw.number_four));
        numbers.add(new numbers("massokka","Five",R.drawable.number_five,R.raw.number_five));
        numbers.add(new numbers("temmokka","Six",R.drawable.number_six,R.raw.number_six));
        numbers.add(new numbers("kenekaku","Seven",R.drawable.number_seven,R.raw.number_seven));
        numbers.add(new numbers("kawinta","Eight",R.drawable.number_eight,R.raw.number_eight));
        numbers.add(new numbers("wo’e","Nine",R.drawable.number_nine,R.raw.number_nine));
        numbers.add(new numbers("na’aacha","Ten",R.drawable.number_ten,R.raw.number_ten));





        numberAdapter itemsAdapter = new numberAdapter(numbersActivity.this,  numbers,R.color.category_numbers);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              // Toast.makeText(numbersActivity.this,"clicked",Toast.LENGTH_SHORT).show();
                numbers num=numbers.get(i);
                releaseMedia();
             //   Log.v("numbersActivity","Current object"+num.toString());


                int result = audioManager.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mp = MediaPlayer.create(numbersActivity.this, num.getMedia());
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