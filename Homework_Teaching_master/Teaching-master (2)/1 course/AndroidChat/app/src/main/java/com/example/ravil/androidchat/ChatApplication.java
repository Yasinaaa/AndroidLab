package com.example.ravil.androidchat;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * @author ravil
 */
public class ChatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
