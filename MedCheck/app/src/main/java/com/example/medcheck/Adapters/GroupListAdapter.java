package com.example.medcheck.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medcheck.Chat_Activity;
import com.example.medcheck.R;

import java.util.ArrayList;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupHolder> {
    private ArrayList<String> mGroupList;
    private Context mContext;

    public GroupListAdapter(Context context, ArrayList<String> groupList) {
        mContext = context;
        mGroupList = groupList;
    }

    @Override
    public int getItemCount() {
        return mGroupList.size();
    }

    // Inflates the appropriate layout according to the ViewType.
    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_item, parent, false);
        return new GroupHolder(view);
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(@NonNull GroupHolder holder, int position) {
        String group = mGroupList.get(position);
        holder.bind(group);
    }

    class GroupHolder extends RecyclerView.ViewHolder {
        Button groupButton;

        GroupHolder(View itemView) {
            super(itemView);

            groupButton = itemView.findViewById(R.id.group_button);
        }

        void bind(String group) {
            groupButton.setText(group);
            groupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String groupName = groupButton.getText().toString();
                    Intent intent = new Intent(mContext, Chat_Activity.class);
                    intent.putExtra("GroupName", groupName);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
