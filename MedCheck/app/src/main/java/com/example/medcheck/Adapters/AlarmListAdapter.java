package com.example.medcheck.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medcheck.Alarm;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medcheck.Chat_Activity;
import com.example.medcheck.Group_Select;
import com.example.medcheck.Home_Activity;
import com.example.medcheck.R;
import com.example.medcheck.View_Alarm_Info_Activity;

import java.util.ArrayList;

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.AlarmHolder> {
    private ArrayList<Alarm> mAlarmList;
    private Context mContext;

    public AlarmListAdapter(Context context, ArrayList<Alarm> alarmList) {
        mContext = context;
        mAlarmList = alarmList;
    }

    @Override
    public int getItemCount() {
        return mAlarmList.size();
    }

    // Inflates the appropriate layout according to the ViewType.
    @NonNull
    @Override
    public AlarmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_item, parent, false);
        return new AlarmHolder(view);
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(@NonNull AlarmHolder holder, int position) {
        Alarm alarm = mAlarmList.get(position);
        holder.bind(alarm);
    }

    class AlarmHolder extends RecyclerView.ViewHolder {
        Button alarmButton;

        AlarmHolder(View itemView) {
            super(itemView);

            alarmButton = itemView.findViewById(R.id.alarm_button);
        }

        void bind(Alarm alarm) {
            alarmButton.setText(alarm.getMedication()+": "+alarm.getOwner());
            alarmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String alarmOwner = alarm.getOwner();
                    Intent intent = new Intent(mContext, View_Alarm_Info_Activity.class);
                    intent.putExtra("Owner", alarmOwner);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
