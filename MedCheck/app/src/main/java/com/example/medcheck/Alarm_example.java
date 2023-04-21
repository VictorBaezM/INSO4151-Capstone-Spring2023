package com.example.medcheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Alarm_example extends AppCompatActivity {

    private TextView selectedTimeTextView;
    private Button btnSelectDate;
    private Button setTimeButton;
    private Button setAlarmButton;
    private CheckBox repeatCheckBox;
    private int hour, minute, year, month, day;
    private boolean isRepeat = false;
    private int mYear;
    private int mMonth;
    private int mDay;


    private static final String CHANNEL_ID = "alarm_channel";
    private static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_example);

        selectedTimeTextView = findViewById(R.id.textViewTime);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        setTimeButton = findViewById(R.id.btnSelectTime);
        setAlarmButton = findViewById(R.id.btnSetAlarm);
        repeatCheckBox = findViewById(R.id.checkBoxRepeat);

        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current time
                final Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);

                // Launch TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Alarm_example.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfDay) {
                                hour = hourOfDay;
                                minute = minuteOfDay;
                                selectedTimeTextView.setText(String.format("Selected Time: %02d:%02d", hour, minute));
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTimeTextView.getText().toString().isEmpty() || btnSelectDate.getText().toString().isEmpty()) {
                    Toast.makeText(Alarm_example.this, "Please select a date and time for the alarm", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get selected date and time
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day, hour, minute);

                // Set up alarm
                AlarmHelper.setAlarm(Alarm_example.this, 1, calendar.getTimeInMillis(), isRepeat);

                // Show notification
                showNotification();

                Toast.makeText(Alarm_example.this, "Alarm set successfully", Toast.LENGTH_SHORT).show();
            }
        });

        repeatCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRepeat = !isRepeat;
                if (isRepeat) {
                    repeatCheckBox.setText("Repeat: ON");
                } else {
                    repeatCheckBox.setText("Repeat: OFF");
                }
            }
        });
        // Add OnClickListener for selectedDateTextView
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

//        cancelAlarmButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Cancel alarm
//                AlarmHelper.cancelAlarm(Alarm_example.this);
//
//                Toast.makeText(Alarm_example.this, "Alarm cancelled", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    // Show DatePickerDialog for selecting date
    private void showDatePickerDialog() {
        // Create a new instance of DatePickerDialog and show it
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        String date = String.format("%02d/%02d/%04d", mMonth + 1, mDay, mYear);
                        btnSelectDate.setText("Date: " + date);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showNotification() {
        // Create notification channel
        NotificationHelper.createNotificationChannel(Alarm_example.this, CHANNEL_ID);

        // Build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(Alarm_example.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle("Alarm")
                .setContentText("Your alarm is ringing!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setAutoCancel(true);

        // Show notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(Alarm_example.this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}