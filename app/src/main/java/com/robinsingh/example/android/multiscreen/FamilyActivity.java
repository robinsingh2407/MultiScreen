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
import java.util.List;

public class FamilyActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //Create and setup the AudioManager to request auto focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Words> words = new ArrayList<>();
        words.add(new Words("Father", "Padre", R.drawable.family_father, R.raw.family_father));
        words.add(new Words("Mother", "Madre", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Words("Son", "Hijo", R.drawable.family_son, R.raw.family_son));
        words.add(new Words("Daughter", "Hija", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Words("Grandfather", "Abuelo", R.drawable.family_grandfather, R.raw.family_grandfather));
        words.add(new Words("Grandmother", "Abuela", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Words("Older Brother", "Hermano mayor", R.drawable.family_older_brother, R.raw.family_older_sister));
        words.add(new Words("Older Sister", "Hermana mayor", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Words("Younger Brother", "Hermano menor", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Words("Younger Sister", "Hermana menor", R.drawable.family_younger_sister, R.raw.family_younger_sister));

        //Knows how to create layout for list
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_family);
        ListView item = (ListView) findViewById(R.id.list);
        item.setAdapter(adapter);
        item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Words word = words.get(position);
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getmAudioResourceId());
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
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
