package com.example.meetdoc.doctor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.meetdoc.R;
import com.example.meetdoc.models.createSlotModel;
import com.example.meetdoc.viewmodel.CreateSlotViewModel;
import com.example.meetdoc.viewmodel.DoctorByIdViewModel;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CreateSlots extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView tvSelectedDate;
    private TimePicker startTimePicker;
    private TimePicker endTimePicker;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private TextView tvDuration;
    private MaterialButton btnConfirm;

    private Calendar selectedDate;
    private Calendar startTime;
    private Calendar endTime;

    private CreateSlotViewModel createSlotViewModel;
    // Updated to required format
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault()); // 24-hour format

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_slots);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        selectedDate = Calendar.getInstance();
        startTime = Calendar.getInstance();
        endTime = Calendar.getInstance();
        endTime.add(Calendar.HOUR_OF_DAY, 1);

        initViews();
        setupCalendar();
        setupTimePickers();
        setupConfirmButton();

        updateDateDisplay();
        updateTimeDisplays();
        updateDurationDisplay();
    }

    private void initViews() {
        calendarView = findViewById(R.id.calendarView);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        startTimePicker = findViewById(R.id.startTimePicker);
        endTimePicker = findViewById(R.id.endTimePicker);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndTime = findViewById(R.id.tvEndTime);
        tvDuration = findViewById(R.id.tvDuration);
        btnConfirm = findViewById(R.id.btnConfirm);
    }

    private void setupCalendar() {
        calendarView.setMinDate(System.currentTimeMillis());

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate.set(year, month, dayOfMonth);
            updateDateDisplay();
        });
    }

    private void setupTimePickers() {
        startTimePicker.setIs24HourView(true);
        endTimePicker.setIs24HourView(true);

        startTimePicker.setHour(startTime.get(Calendar.HOUR_OF_DAY));
        startTimePicker.setMinute(startTime.get(Calendar.MINUTE));

        endTimePicker.setHour(endTime.get(Calendar.HOUR_OF_DAY));
        endTimePicker.setMinute(endTime.get(Calendar.MINUTE));

        startTimePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            startTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            startTime.set(Calendar.MINUTE, minute);
            updateTimeDisplays();
            updateDurationDisplay();

            if (startTime.after(endTime)) {
                endTime.setTimeInMillis(startTime.getTimeInMillis());
                endTime.add(Calendar.HOUR_OF_DAY, 1);
                endTimePicker.setHour(endTime.get(Calendar.HOUR_OF_DAY));
                endTimePicker.setMinute(endTime.get(Calendar.MINUTE));
            }
        });

        endTimePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            endTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            endTime.set(Calendar.MINUTE, minute);
            updateTimeDisplays();
            updateDurationDisplay();

            if (endTime.before(startTime)) {
                startTime.setTimeInMillis(endTime.getTimeInMillis());
                startTime.add(Calendar.HOUR_OF_DAY, -1);
                startTimePicker.setHour(startTime.get(Calendar.HOUR_OF_DAY));
                startTimePicker.setMinute(startTime.get(Calendar.MINUTE));
            }
        });
    }

    private void setupConfirmButton() {
        btnConfirm.setOnClickListener(v -> {
            Calendar startDateTime = (Calendar) selectedDate.clone();
            startDateTime.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));
            startDateTime.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));

            Calendar endDateTime = (Calendar) selectedDate.clone();
            endDateTime.set(Calendar.HOUR_OF_DAY, endTime.get(Calendar.HOUR_OF_DAY));
            endDateTime.set(Calendar.MINUTE, endTime.get(Calendar.MINUTE));

            String dateStr = dateFormat.format(startDateTime.getTime());
            String startTimeStr = timeFormat.format(startDateTime.getTime());
            String endTimeStr = timeFormat.format(endDateTime.getTime());

            String message = "Selected: " + dateStr + " from " + startTimeStr + " to " + endTimeStr;
            Toast.makeText(CreateSlots.this, message, Toast.LENGTH_LONG).show();
            createSlot(dateStr,startTimeStr,endTimeStr,5,"Appointment created");
        });
    }

    private void updateDateDisplay() {
        String formattedDate = dateFormat.format(selectedDate.getTime());

        Calendar today = Calendar.getInstance();
        if (selectedDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                selectedDate.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
            tvSelectedDate.setText("Today");
        } else {
            tvSelectedDate.setText(formattedDate);
        }
    }

    private void updateTimeDisplays() {
        tvStartTime.setText(timeFormat.format(startTime.getTime()));
        tvEndTime.setText(timeFormat.format(endTime.getTime()));
    }

    private void updateDurationDisplay() {
        long durationMillis = endTime.getTimeInMillis() - startTime.getTimeInMillis();
        long hours = TimeUnit.MILLISECONDS.toHours(durationMillis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis) % 60;

        String durationText;
        if (hours > 0 && minutes > 0) {
            durationText = hours + " hour" + (hours > 1 ? "s" : "") + " " + minutes + " min";
        } else if (hours > 0) {
            durationText = hours + " hour" + (hours > 1 ? "s" : "");
        } else {
            durationText = minutes + " minutes";
        }

        tvDuration.setText(durationText);
    }

    private void createSlot(String date,String start,String end,int limit,String notes)
    {
        createSlotViewModel = new ViewModelProvider(this).get(CreateSlotViewModel.class);
        // Observe the doctor list
        createSlotViewModel.createSlot(this,date.toString(),start,end,limit,notes).observe(this, slot -> {
            if (slot != null) {
                Toast.makeText(this, "SLOT CREATED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Log.e("CTESTING","HERE 6");
                Toast.makeText(this, "NO RESPONSE", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
