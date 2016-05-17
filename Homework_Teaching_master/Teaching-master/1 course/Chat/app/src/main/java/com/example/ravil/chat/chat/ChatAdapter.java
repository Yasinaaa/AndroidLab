package com.example.ravil.chat.chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ravil.chat.R;
import com.example.ravil.chat.model.Message;

import java.util.ArrayList;
import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageHolder> {

    private List<Message> mMessage;

    public ChatAdapter() {
        mMessage = new ArrayList<>();
    }

    public ChatAdapter(List<Message> mMessage) {
        this.mMessage = mMessage;
    }

    public void setMessage(List<Message> message) {
        mMessage = message;

        notifyDataSetChanged();
    }

    public void addMessage(Message message) {
        mMessage.add(message);

        notifyItemChanged(mMessage.size() - 1);
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);

        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        Message message = mMessage.get(position);

        holder.mUsername.setText(message.getUsername());
        holder.mMessageText.setText(message.getText());
    }

    @Override
    public int getItemCount() {
        return mMessage.size();
    }

    public static class MessageHolder extends RecyclerView.ViewHolder {

        TextView mUsername;

        TextView mMessageText;

        public MessageHolder(View itemView) {
            super(itemView);

            mUsername = (TextView) itemView.findViewById(R.id.username_text_view);
            mMessageText = (TextView) itemView.findViewById(R.id.message_text_view);
        }
    }
}
