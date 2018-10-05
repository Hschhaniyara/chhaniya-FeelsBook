/**
 * activity which displays the emotion count for feels book
 * @author Harshal
 * @see com.example.owner.chhaniya_feelsbook.MainActivity
 */

package com.example.owner.chhaniya_feelsbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class DisplayCountActivity extends AppCompatActivity {

    /**
     * method updates the value of all emotion counts
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        // set up all text box for emotion count
        TextView loveCount = (TextView) findViewById(R.id.Love_count);
        TextView sadnessCount = (TextView) findViewById(R.id.Sadness_count);
        TextView fearCount = (TextView) findViewById(R.id.Fear_count);
        TextView joyCount = (TextView) findViewById(R.id.Joy_count);
        TextView surpriseCount = (TextView) findViewById(R.id.Surprise_count);
        TextView angerCount = (TextView) findViewById(R.id.Anger_count);

        // get all count from main activity
        String love = getIntent().getExtras().getString("love");
        String sadness = getIntent().getExtras().getString("sadness");
        String fear = getIntent().getExtras().getString("fear");
        String joy = getIntent().getExtras().getString("joy");
        String surprise = getIntent().getExtras().getString("surprise");
        String anger = getIntent().getExtras().getString("anger");

        //update all count
        loveCount.setText(love);
        sadnessCount.setText(sadness);
        fearCount.setText(fear);
        joyCount.setText(joy);
        surpriseCount.setText(surprise);
        angerCount.setText(anger);
    }

}
