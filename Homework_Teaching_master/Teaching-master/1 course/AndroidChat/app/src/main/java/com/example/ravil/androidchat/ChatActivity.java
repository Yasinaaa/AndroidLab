package com.example.ravil.androidchat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ravil
 */
public class ChatActivity extends AppCompatActivity {

    public static final String KEY = "username";

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mRecyclerView = (RecyclerView) findViewById(R.id.message_recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 10; i ++) {
            messages.add(new Message("Саша", "Привет"));
        }


        MessageAdapter adapter = new MessageAdapter(messages);

        mRecyclerView.setAdapter(adapter);

    }
}
