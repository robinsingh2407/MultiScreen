package com.robinsingh.example.android.multiscreen;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS || focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaplayer();
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

        //ArrayList to add color and their miwok translation
        final ArrayList<Words> words = new ArrayList<>();
        words.add(new Words("Red", "rojo", R.drawable.color_red, R.raw.color_red));
        words.add(new Words("Brown", "Azul", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Words(" Black", "Negro", R.drawable.color_black, R.raw.color_black));
        words.add(new Words("White", "Blanco", R.drawable.color_white, R.raw.color_white));
        words.add(new Words("Green", "Verde", R.drawable.color_green, R.raw.color_green));
        words.add(new Words("Grey", "Gris", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Words("Dust Yellow", "Rosado", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Words("Mustard Yellow", "Amarillo", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        //Adapter knows how to create layouts for each item in a List
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_colors);
        ListView item = (ListView) findViewById(R.id.list);
        item.setAdapter(adapter);
        item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Words word = words.get(position);
                releaseMediaplayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //    mAudioManager.registerMediaButtonEventReceiver(RemoteControlReceiver);
                    mMediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getmAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(completionListener);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        releaseMediaplayer();
    }

    private void releaseMediaplayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();

            mMediaPlayer = null;
            // Abandon audio focus when playback complete
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
