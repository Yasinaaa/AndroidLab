package com.example.ravil.chat.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ravil.chat.R;
import com.example.ravil.chat.chat.ChatActivity;


public class LoginActivity extends AppCompatActivity {

    private EditText mUsernameEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsernameEditText = (EditText) findViewById(R.id.username);
    }

    public void logIn(View view) {
        String username = mUsernameEditText.getText().toString();

        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(ChatActivity.USERNAME_KEY, username);

        startActivity(intent);
    }
}
