/**
 * edit activity for feels book
 * allows user to modify the date and comment
 * or delete the emotion
 * @author Harshal
 * @see com.example.owner.chhaniya_feelsbook.MainActivity
 */

package com.example.owner.chhaniya_feelsbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {
    private EditText commentText;
    private EditText dateText;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //gets emotion, comment, date from main activity
        final String emotion = getIntent().getExtras().getString("emotion");
        String comment = getIntent().getExtras().getString("comment");
        String date = getIntent().getExtras().getString("date");

        //initializing variables
        commentText = (EditText) findViewById(R.id.comment_editText);
        dateText = (EditText) findViewById(R.id.date_editText);
        TextView emotionText = (TextView) findViewById(R.id.emotion_textView);

        //setting the variables with values to display
        emotionText.setText(emotion);
        commentText.setText(comment);
        dateText.setText(date);

        // initializing buttons
        Button update = (Button) findViewById(R.id.update);
        Button delete = (Button) findViewById(R.id.delete);
        Button back = (Button) findViewById(R.id.back);

        // allows user to go back to main activity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("check","back");
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        // returns intent "delete" to main activity
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("check","delete");
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        // returns modified values of comment and date and returns intent "update" to main activity
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                String c = commentText.getText().toString();
                String d = dateText.getText().toString();
                returnIntent.putExtra("c",c);
                returnIntent.putExtra("d",d);
                returnIntent.putExtra("e",emotion);
                returnIntent.putExtra("check","update");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

    }
}
