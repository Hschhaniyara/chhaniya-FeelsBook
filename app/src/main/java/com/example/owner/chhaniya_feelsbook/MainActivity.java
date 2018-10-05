/**
 * main application activity for feels book
 * @author Harshal
 * @see java.io
 * @since 1.0
 * @version 1
 */
package com.example.owner.chhaniya_feelsbook;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private EditText commentText;       // allows user to enter comment
    private ListView oldEmotionList;    // history of emotion list
    private Spinner emotionSpinner;     // used spinner instead of 6 buttons to make it look cleaner
    private int pos;                    // placeholder to pass parameter outside the method

    ArrayList<Emotion> emotionList;     // array list of emotions
    ArrayAdapter<Emotion> adapter;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emotionSpinner = (Spinner) findViewById(R.id.emotion_spinner);  // initializing spinner

        /** setting up the spinner with adapter to update the view as user selects */
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.emotion_array));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emotionSpinner.setAdapter(myAdapter);


        commentText = (EditText) findViewById(R.id.comment_editText);
        Button recordButton = (Button) findViewById(R.id.record_button);
        Button countButton = (Button) findViewById(R.id.count_button);
        oldEmotionList = (ListView) findViewById(R.id.emotion_list);

        /**
         * method to save the emotion
         * gets comment and emotion that user enters, updates emotionList,
         * saves the emotionList in file and notify adapter
         * @param v
         */
        recordButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                String text = commentText.getText().toString();
                String emotionString = emotionSpinner.getSelectedItem().toString();
                Emotion emotion = new Emotion(text, emotionString);
                emotionList.add(emotion);

                saveInFile();
                adapter.notifyDataSetChanged();
            }
        });

        /**
         * method to view the emotion count
         * calculates the count using countEmotion() method
         * opens new activity to display the count
         * @param v
         */
        countButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                //calculating the emotion count
                String loveCount = countEmotion("Love");
                String sadnessCount = countEmotion("Sadness");
                String fearCount = countEmotion("Fear");
                String joyCount = countEmotion("Joy");
                String surpriseCount = countEmotion("Surprise");
                String angerCount = countEmotion("Anger");

                //passing the emotion count to new activity
                Intent intent = new Intent(MainActivity.this, DisplayCountActivity.class);
                intent.putExtra("love", loveCount);
                intent.putExtra("sadness", sadnessCount);
                intent.putExtra("fear", fearCount);
                intent.putExtra("joy", joyCount);
                intent.putExtra("surprise", surpriseCount);
                intent.putExtra("anger", angerCount);
                startActivity(intent);
            }
        });

        /**
         * method to edit and delete the emotion form emotion history
         * gets the vales for the item selected in item list
         * opens new activity to allow user to modify
         * @see EditActivity
         */
        oldEmotionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String emotion, comment;
                String date;
                Emotion e;

                pos = position;
                e = emotionList.get(position);
                emotion = e.getEmotion();
                date = e.getDate();
                comment = e.getMessage();

                //passing the selected emotion to new activity
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("emotion", emotion);
                intent.putExtra("date", date);
                intent.putExtra("comment", comment);
                (MainActivity.this).startActivityForResult(intent, 1);

            }
        });
    }

    /**
     * method which actually modifies the emotion form emotion history
     * gets the vales for the emotion modified by user
     * updates the emotionList based on user choice
     * @see EditActivity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            String check = data.getStringExtra("check");
            if (resultCode == Activity.RESULT_OK && check.equals("update")) {   // user wants to update
                String c = data.getStringExtra("c");
                String d = data.getStringExtra("d");
                String e = data.getStringExtra("e");
                emotionList.remove(pos);
                Emotion emo = new Emotion(c, e);
                emo.setDate(d);
                emotionList.add(emo);
                saveInFile();
                adapter.notifyDataSetChanged();
            } else if (resultCode == Activity.RESULT_OK && check.equals("delete")) {    // user wants to delete
                emotionList.remove(pos);
                saveInFile();
                adapter.notifyDataSetChanged();
            } else {    // back to main activity without any changes

            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    /**
     * method counts the emotion in emotionList
     * by comparing the input emotion with emotion in emotionList
     * @return count
     */
    public String countEmotion(String emotionToMatch) {
        int count = 0;

        for (Emotion emotion: emotionList) {
            if (emotion.getEmotion().equals(emotionToMatch)) {
                count++;
           }
        }
        return String.valueOf(count);
    }

    /** on start of the activity sets the adapter */
    protected void onStart() {
        super.onStart();

        loadFromFile();

        // adapter.notifyDataSetChanged();
        adapter = new ArrayAdapter<Emotion>(this,
                R.layout.list_item, emotionList);
        oldEmotionList.setAdapter(adapter);
    }

    /**
     * loads array from file
     * @throws FileNotFoundException
     */
    private void loadFromFile() {
        //    ArrayList<String> tweets = new ArrayList<String>();
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson(); //library to save objects
            Type listType = new TypeToken<ArrayList<Emotion>>(){}.getType();

            emotionList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            emotionList = new ArrayList<Emotion>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * saves the list of array in file
     * @throws FileNotFoundException
     */
    private void saveInFile() {
        try {

            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(emotionList, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
