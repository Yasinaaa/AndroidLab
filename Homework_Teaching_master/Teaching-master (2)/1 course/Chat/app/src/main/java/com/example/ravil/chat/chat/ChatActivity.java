package com.example.ravil.chat.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ravil.chat.R;
import com.example.ravil.chat.model.Message;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class ChatActivity extends AppCompatActivity {

    public static final String USERNAME_KEY = "USERNAME";

    private String mUsername;
    private RecyclerView mRecyclerView;
    private Button mSendButton;
    private Firebase mFirebaseRef;
    private ChatAdapter mChatAdapter;
    private EditText mMessageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mUsername = getIntent().getExtras().getString(USERNAME_KEY);

        mMessageText = (EditText) findViewById(R.id.message_edit_text);

        mSendButton = (Button) findViewById(R.id.send_button);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.app_name);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mChatAdapter = new ChatAdapter();
        mRecyclerView.setAdapter(mChatAdapter);

        initFirebase();
    }

    private void initFirebase() {
        mFirebaseRef = new Firebase("https://wowwowchat.firebaseio.com/").child("Messages");
        mFirebaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);

                mChatAdapter.addMessage(message);

                mRecyclerView.scrollToPosition(mChatAdapter.getItemCount() - 1);
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

    public void sendMessage() {
        mFirebaseRef.push().setValue(new Message(mUsername, mMessageText.getText().toString()));

        mMessageText.setText("");

    }
}
