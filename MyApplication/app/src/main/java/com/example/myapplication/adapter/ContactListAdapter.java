package com.example.myapplication.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.entity.Contact;
import com.example.myapplication.entity.LastMessage;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends ArrayAdapter<Contact> {
    private LayoutInflater mInflater;

    private AdapterView.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public ContactListAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
        mInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Contact current = getItem(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.singlecontact, parent, false);
        }

        TextView displayName = convertView.findViewById(R.id.display_name);
        TextView lastMsg = convertView.findViewById(R.id.last_massage);
        TextView time = convertView.findViewById(R.id.time);
        ImageView profilePic = convertView.findViewById(R.id.profile_image);
        displayBase64Image(current.getUser().getProfilePic(),profilePic);

        if (current != null) {
            displayName.setText(current.getUser().getDisplayName());
            LastMessage lastMessage = current.getLastMessage();
            if(lastMessage != null) {
                lastMsg.setText(lastMessage.getContent());
                time.setText(lastMessage.getCreated());
            } else {
                lastMsg.setText("");
                time.setText("");
            }
        }

        convertView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(null, view, position, getItemId(position));
            }
        });

        return convertView;
    }

    public void setContacts(List<Contact> contacts) {
        clear();
        if (contacts != null) {
            addAll(contacts);
        }
        notifyDataSetChanged();
    }

    public void displayBase64Image(String base64ImageString, ImageView profilePic) {
        try {
            // Remove the data:image/png;base64, prefix from the base64 string
            String base64Image = base64ImageString.replace("data:image/png;base64,", "");

            // Decode the base64 string into a byte array
            byte[] imageBytes = Base64.decode(base64Image, Base64.DEFAULT);

            // Create a bitmap from the byte array
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

            // Set the bitmap in the ImageView
            profilePic.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}

