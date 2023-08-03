package com.example.myapplication.component;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class ToastTop {
    private Toast toast;
    private String msg;
    private Context context;

    public ToastTop(Context context, String msg) {
        this.context = context;
        this.msg = msg;
        createToast();
    }

    private void createToast() {
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);

        LayoutInflater inflater = LayoutInflater.from(context);
        View toastView = inflater.inflate(R.layout.custom_toast, null);
        TextView toastText = toastView.findViewById(R.id.toast_text);
        toastText.setText(msg);

        toast.setView(toastView);
    }

    public void activateToast() {
        toast.show();
    }
}
