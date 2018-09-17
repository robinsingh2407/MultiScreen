package com.robinsingh.example.android.multiscreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         * Action when Number TextView is clicked
         * */
        TextView numbersActivity = (TextView) findViewById(R.id.numbers);
        numbersActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent numbers = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(numbers);
            }
        });
        /*
         * Action when family TextView is clicked
         * */
        TextView familyActivity = (TextView) findViewById(R.id.family);
        familyActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent family = new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(family);
            }
        });
        /*
         * Action when color TextView is clicked
         * */
        TextView colorsActivity = (TextView) findViewById(R.id.colors);
        colorsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent colors = new Intent(MainActivity.this, ColorsActivity.class);
                startActivity(colors);
            }
        });

        /*
         * Action when phrases TextView is clicked
         * */
        TextView phrasesActivity = (TextView) findViewById(R.id.phrases);
        phrasesActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent phrases = new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(phrases);
            }
        });

    }
}
