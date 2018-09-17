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

public class PhrasesActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange== AudioManager.AUDIOFOCUS_LOSS || focusChange== AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK){
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
            }else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };
    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mMediaPlayer.release();
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Words> words = new ArrayList<>();
        words.add(new Words("How are you?", "¿Cómo estás?", R.raw.phrase_come_here));
        words.add(new Words("What are you doing? ", "¿Qué estás haciendo?", R.raw.phrase_where_are_you_going));
        words.add(new Words("What is your name?", "¿Cuál es tu nombre?", R.raw.phrase_what_is_your_name));
        words.add(new Words("Why are you shouting?", "¿Por qué estás gritando?", R.raw.phrase_im_coming));
        words.add(new Words("Where do you live?", "¿Dónde vives?", R.raw.phrase_im_feeling_good));
        words.add(new Words("Where are you going?", "¿Dónde estás?", R.raw.phrase_where_are_you_going));
        words.add(new Words("Tell me about yourself", "Cuéntame sobre ti", R.raw.phrase_lets_go));
        words.add(new Words("Winter is coming", "Viene el invierno", R.raw.phrase_my_name_is));
        words.add(new Words("How you doing?", "¿Como estas?", R.raw.phrase_how_are_you_feeling));
        words.add(new Words("Limits exists only in mind", "Los límites existen solo en mente", R.raw.phrase_what_is_your_name));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_phrases);
        ListView item = (ListView) findViewById(R.id.list);
        item.setAdapter(adapter);
        item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Words word = words.get(position);
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(mOnAudioChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result== AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getmAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(completionListener);
                }
            }
        });
    }
    @Override
    protected void onStop(){
        super.onStop();

        releaseMediaPlayer();
    }
    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();

            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioChangeListener);
        }
    }
}
