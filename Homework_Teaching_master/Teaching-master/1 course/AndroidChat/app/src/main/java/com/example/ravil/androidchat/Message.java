package com.example.ravil.androidchat;

/**
 * @author ravil
 */
public class Message {

    private String mUsername;

    private String mMessageText;

    public Message() {
    }

    public Message(String username, String messageText) {
        mUsername = username;
        mMessageText = messageText;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getMessageText() {
        return mMessageText;
    }

    public void setMessageText(String messageText) {
        mMessageText = messageText;
    }
}
