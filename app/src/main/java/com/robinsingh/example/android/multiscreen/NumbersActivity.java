package com.robinsingh.example.android.multiscreen;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS|| focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK) {
                      //Pause playback and reset media player to the start of the file. so we can
                      //play the word from the beginning when we resume payback
                       mMediaPlayer.pause();
                       mMediaPlayer.seekTo(0);
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Stop playback and release resources
                        releaseMediaPlayer();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                       //Resume playback
                        mMediaPlayer.start();
                    }
                }
            };
    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mMediaPlayer.release();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //Create and setup the AudioManager to request auto focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

//        Adding arraylist to store words and their miWok translation
        final ArrayList<Words> words = new ArrayList<Words>();
        words.add(new Words("One", "Uno", R.drawable.number_one, R.raw.number_one));
        words.add(new Words("Two", "Dos", R.drawable.number_two, R.raw.number_two));
        words.add(new Words("Three", "Tres", R.drawable.number_three, R.raw.number_three));
        words.add(new Words("Four", "Cuatro", R.drawable.number_four, R.raw.number_four));
        words.add(new Words("Five", "Cinco", R.drawable.number_five, R.raw.number_five));
        words.add(new Words("Six", "Seis", R.drawable.number_six, R.raw.number_six));
        words.add(new Words("Seven", "Siete", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Words("Eight", "Ocho", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Words("Nine", "Nueve", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Words("Ten", "Diez", R.drawable.number_ten, R.raw.number_ten));

        /*
         * Adapter knows how to create layouts for each items in a list
         * */
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);
        ListView view = (ListView) findViewById(R.id.list);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Words word = words.get(position);
                releaseMediaPlayer();
                //Result audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                //    mAudioManager.registerMediaButtonEventReceiver(RemoteControlReceiver);
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getmAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(completionListener);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();

            mMediaPlayer = null;
            // Abandon audio focus when playback complete
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
