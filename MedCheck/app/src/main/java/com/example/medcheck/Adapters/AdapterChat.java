package com.example.medcheck.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medcheck.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.myHolder>{

    private static final int MSG_TYPE_RIGHT = 0;
    private static final int MSG_TYPE_LEFT = 0;
    FirebaseUser fUser;

    Context context;
    List<com.example.medcheck.Message> chatList;
public AdapterChat(Context context, List<com.example.medcheck.Message> chatList){
    this.context = context;
    this.chatList = chatList;
}
    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

    if (viewType == MSG_TYPE_RIGHT){
        View view = LayoutInflater.from(context).inflate(R.layout.chat_right, viewGroup, false);
        return new myHolder(view);
    }else{
        View view = LayoutInflater.from(context).inflate(R.layout.chat_left, viewGroup, false);
        return new myHolder(view);
    }

    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int i) {
// get data for messages
    String message = chatList.get(i).getBody();
    String date = chatList.get(i).getSend_Date();

    //set Data
        holder.messageTv.setText(message);
        holder.timeTv.setText(date);

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
    public int getItemViewType(int position){
    fUser = FirebaseAuth.getInstance().getCurrentUser();
    if (chatList.get(position).getUid().equals(fUser.getUid())){
        return MSG_TYPE_RIGHT;
    }else {
        return MSG_TYPE_LEFT;
    }
    }


    class myHolder extends RecyclerView.ViewHolder{
        TextView messageTv, timeTv;

        public myHolder(@NonNull View itemView) {
            super(itemView);

            timeTv = itemView.findViewById(R.id.timeTv);
            messageTv = itemView.findViewById(R.id.messageTv);

        }
    }
}
