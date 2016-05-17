package com.example.ravil.androidchat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ravil
 */
public class ChatActivity extends AppCompatActivity {

    public static final String KEY = "username";

    private RecyclerView mRecyclerView;
    private String mUsername;
    private EditText mMessageEditText;
    private MessageAdapter mAdapter;

    private Firebase mFirebase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mUsername = getIntent().getStringExtra(KEY);

        mRecyclerView = (RecyclerView) findViewById(R.id.message_recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMessageEditText = (EditText) findViewById(R.id.message_edit_text);

        mAdapter = new MessageAdapter();

        mRecyclerView.setAdapter(mAdapter);

        mFirebase = new Firebase("https://wowwowchat.firebaseio.com/").child("Messages");
        mFirebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);

                mAdapter.addMessage(message);

                mRecyclerView.scrollToPosition(mAdapter.getLastPosition());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void sendMessage(View view) {
        String messageText = mMessageEditText.getText().toString();
        Message message = new Message(mUsername, messageText);

        mMessageEditText.setText("");

        mFirebase.push().setValue(message);
    }
}
