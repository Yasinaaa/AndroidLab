package com.example.ravil.chat.model;

/**
 * Created by ravil on 14/04/16.
 */
public class Message {

    private String mUsername;

    private String mText;

    public Message() {
    }

    public Message(String mUsername, String mText) {
        this.mUsername = mUsername;
        this.mText = mText;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }
}
