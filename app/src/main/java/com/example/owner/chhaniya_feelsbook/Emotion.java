/**
 * class emotion for feels book
 * @author Harshal
 */

package com.example.owner.chhaniya_feelsbook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Emotion {
    private String date;
    private String message;
    private String emotion;

    private static final Integer MAX_CHARS = 100;

    //Empty argument constructor with default values
    Emotion() {
        this.date = "";
        this.message = "";
        this.emotion = "";
    }

    /** Overloading to specify the emotion content
     * takes in bellow parameter and creates an object emotion
     * @param message
     * @param feels
     */
    Emotion(String message, String feels) {
        //setting up date in iso8601 Date&Time format
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df.setTimeZone(tz);
        this.date = df.format(new Date());

        this.message = message;
        this.emotion = feels;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) throws CommentTooLongException {
        if (message.length() <= this.MAX_CHARS ) {
            this.message = message;
        } else {
            throw new CommentTooLongException();
        }
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmotion() {
        return this.emotion;
    }

    // returns string with the emotion list
    @Override
    public String toString() {
        return getDate() + " " + this.getEmotion() + " " + this.getMessage();
    }

}
