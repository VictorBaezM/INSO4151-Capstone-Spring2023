package com.example.medcheck.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medcheck.Message;
import com.example.medcheck.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.MyHolder>{

    FirebaseAuth auth = FirebaseAuth.getInstance();

    private static int MESSAGE_TYPE_MY_MESSAGE = 0;
    private static int MESSAGE_TYPE_OTHER_MESSAGE = 1;

    private Context context;
    private ArrayList<Message> messageList;

    public AdapterChat(Context context, ArrayList<Message> messageList,int MESSAGE_TYPE_MY_MESSAGE, int MESSAGE_TYPE_OTHER_MESSAGE) {
        this.context = context;
        this.messageList = messageList;
        this.MESSAGE_TYPE_MY_MESSAGE = MESSAGE_TYPE_MY_MESSAGE;
        this.MESSAGE_TYPE_OTHER_MESSAGE =MESSAGE_TYPE_OTHER_MESSAGE;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_TYPE_MY_MESSAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_right, parent, false);
            return new MyHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_left, parent, false);
            return new MyHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String message = messageList.get(position).getContent();
        String timestamp = messageList.get(position).getSend_Time();

        holder.messageTv.setText(message);
        holder.timeTv.setText(timestamp);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).getUid() == auth.getUid()) {
            return MESSAGE_TYPE_MY_MESSAGE;
        } else {
            return MESSAGE_TYPE_OTHER_MESSAGE;
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView messageTv, timeTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            messageTv = itemView.findViewById(R.id.messageTv);
            timeTv = itemView.findViewById(R.id.timeTv);
        }
    }
}
