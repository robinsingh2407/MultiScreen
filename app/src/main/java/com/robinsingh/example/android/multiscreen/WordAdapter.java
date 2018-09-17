package com.robinsingh.example.android.multiscreen;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Words> {
    private int mColorId;

    public WordAdapter(Activity Context, ArrayList<Words> words, int colorId) {
        super(Context, 0, words);
        mColorId = colorId;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Check if the existing View is reused otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        final Words currentWord = getItem(position);

        TextView miWokTextView = listItemView.findViewById(R.id.miWok_translate);
        miWokTextView.setText(currentWord.getmMiWokTranslation());

        TextView defaultTextView = listItemView.findViewById(R.id.default_translate);
        defaultTextView.setText(currentWord.getmDefaultTranslation());

        ImageView icon = listItemView.findViewById(R.id.image);
        if (currentWord.hasImage()) {
            icon.setImageResource(currentWord.getmImageResourceId());
        } else {
            icon.setVisibility(View.GONE);
        }
        View textContainer = listItemView.findViewById(R.id.text_container);

        int color = ContextCompat.getColor(getContext(), mColorId);

        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
