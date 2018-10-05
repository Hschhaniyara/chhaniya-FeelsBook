/**
 * class handles the exception if comment is too long
 * @author Harshal
 * @see Exception
 */
package com.example.owner.chhaniya_feelsbook;

public class CommentTooLongException extends Exception {

    /** shows message if tweet is long than max */
    CommentTooLongException() {
        super("The message is too long! Please keep your comment within 100 characters.");
    }
}
