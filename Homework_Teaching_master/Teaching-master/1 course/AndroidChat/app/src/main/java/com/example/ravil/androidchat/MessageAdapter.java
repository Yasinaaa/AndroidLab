package com.example.ravil.androidchat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @author ravil
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    private List<Message> mMessages;

    public MessageAdapter(List<Message> messages) {
        mMessages = messages;
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);


        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        Message message = mMessages.get(position);

        holder.mUsername.setText(message.getUsername());
        holder.mMessageText.setText(message.getMessageText());
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
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
