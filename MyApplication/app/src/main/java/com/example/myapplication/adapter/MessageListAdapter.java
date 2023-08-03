package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entity.Contact;
import com.example.myapplication.entity.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    String username;
    Context context;
    ArrayList<Message> messagesArrayList;

    int ITEM_SEND = 1;
    int ITEM_RECIEVE = 2;

    public MessageListAdapter(Context context, ArrayList<Message> messagesArrayList,String username) {
        this.username = username;
        this.context = context;
        this.messagesArrayList = messagesArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_SEND) {
            View view = LayoutInflater.from(context).inflate(R.layout.msgsender, parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.msgreciver, parent, false);
            return new RecieverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Message messages = messagesArrayList.get(position);
        if (holder.getClass() == SenderViewHolder.class) {
            SenderViewHolder viewHolder = (SenderViewHolder) holder;
            viewHolder.textViewmessaage.setText(messages.getContent());
            viewHolder.timeofmessage.setText(messages.getCreated());
        } else {
            RecieverViewHolder viewHolder = (RecieverViewHolder) holder;
            viewHolder.textViewmessaage.setText(messages.getContent());
            viewHolder.timeofmessage.setText(messages.getCreated());
        }

    }
    public String getFormatedTime(String string) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSS");
        try {
            Date date = inputFormat.parse(string);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            assert date != null;
            return outputFormat.format(date);
        } catch (ParseException e) {
            return string;
        }
    }


    //    @Override
    public int getItemViewType(int position) {
        Message message = messagesArrayList.get(position);
        if (message.getSender().getUsername().equals(username)) {
            return ITEM_SEND;
        } else {
            return ITEM_RECIEVE;
        }
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }


    class SenderViewHolder extends RecyclerView.ViewHolder {

        TextView textViewmessaage;
        TextView timeofmessage;


        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewmessaage = itemView.findViewById(R.id.sender_message);
            timeofmessage = itemView.findViewById(R.id.time_message_sender);
        }
    }

    class RecieverViewHolder extends RecyclerView.ViewHolder {

        TextView textViewmessaage;
        TextView timeofmessage;


        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewmessaage = itemView.findViewById(R.id.reciver_message);
            timeofmessage = itemView.findViewById(R.id.time_message_reciver);
        }
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messagesArrayList = messages;
        notifyDataSetChanged();
    }

    public int getArraySize() {
        return messagesArrayList.size() - 1;
    }
}