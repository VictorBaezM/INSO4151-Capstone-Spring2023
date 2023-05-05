
        package com.example.medcheck.Adapters;

        import android.content.Context;
        import android.content.Intent;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.medcheck.Chat_Activity;
        import com.example.medcheck.Group_Select;
        import com.example.medcheck.Home_Activity;
        import com.example.medcheck.R;
        import com.example.medcheck.View_Alarm_Info_Activity;

        import java.util.ArrayList;

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.AlarmHolder> {
    private ArrayList<String> mAlarmList;
    private Context mContext;

    public AlarmListAdapter(Context context, ArrayList<String> AlarmList) {
        mContext = context;
        mAlarmList = AlarmList;
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
        String group = mAlarmList.get(position);
        holder.bind(group);
    }

    class AlarmHolder extends RecyclerView.ViewHolder {
        Button AlarmButton;

        AlarmHolder(View itemView) {
            super(itemView);

            AlarmButton = itemView.findViewById(R.id.alarm_button);
        }

        void bind(String alarm) {
            int index = alarm.indexOf(" ");
            int number = Integer.parseInt(alarm.substring(0,index));
            AlarmButton.setText(alarm.substring(index+1));
            AlarmButton.setOnClickListener(view -> {
                Intent intent;
                intent = new Intent(mContext, View_Alarm_Info_Activity.class);
                View_Alarm_Info_Activity.alarm =Home_Activity.user.getAlarms().get(number);
                Log.println(Log.INFO, "debug", "The name for this button is " + view.getResources().getResourceEntryName(view.getId()));
                intent.putExtra("AlarmNumber",number);
                mContext.startActivity(intent);
                ((AppCompatActivity)mContext).finish();
            });
        }
    }
}