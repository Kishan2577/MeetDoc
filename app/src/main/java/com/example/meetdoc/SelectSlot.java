package com.example.meetdoc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.meetdoc.adapter.GetSlotAdapter;
import com.example.meetdoc.adapter.SlotAdapter;
import com.example.meetdoc.viewmodel.DoctorViewModel;
import com.example.meetdoc.viewmodel.GetSlotViewModel;
import com.example.meetdoc.viewmodel.SelectAppointmentViewModel;
import com.example.meetdoc.viewmodel.SlotViewModel;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectSlot extends AppCompatActivity {
    CalendarView calendarView;
    Spinner timeSpinner;
    Button bookButton;

    String selectedDate = "";
    String selectedTime = "";

    SlotViewModel slotViewModel;

    Intent intent;
    RecyclerView slotRecyclerView;

    TextView doctorName,doctorSpecialty;
    SlotAdapter adapter;

    CircleImageView doc_img;
    RatingBar ratingBar;

    SelectAppointmentViewModel viewModel;

    ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_slot);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        doctorName = findViewById(R.id.doctorName);
        doctorSpecialty = findViewById(R.id.doctorSpecialty);
        calendarView = findViewById(R.id.calendarView);
        ratingBar = findViewById(R.id.doctorRating);
        doc_img = findViewById(R.id.doc_img);
        //timeSpinner = findViewById(R.id.timeSpinner);
        bookButton = findViewById(R.id.bookButton);




        // Set default date
        Calendar calendar = Calendar.getInstance();
        selectedDate = formatDate(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("doctor_name");
        String url = intent.getStringExtra("doctor_img");
        String specs = intent.getStringExtra("doctor_specs");
        String rating = intent.getStringExtra("doctor_rating");

        doctorName.setText(name);
        doctorSpecialty.setText(specs);
        ratingBar.setRating(Float.parseFloat(rating));


        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.doctor_banner) // optional placeholder
                .error(R.drawable.doctor_banner) // optional fallback
                .into(doc_img);

        slotRecyclerView = findViewById(R.id.slotRecyclerView);
        slotRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        slotViewModel = new ViewModelProvider(this).get(SlotViewModel.class);
        adapter = new SlotAdapter(new ArrayList<>(), this);
        slotRecyclerView.setAdapter(adapter);
        // Date selection
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = formatDate(year, month, dayOfMonth);

            //Toast.makeText(this, "Selected: " + selectedDate +"ID = "+id, Toast.LENGTH_SHORT).show();
            getCurrentSlots(id,selectedDate);

        });


        // Book button
        bookButton.setOnClickListener(v -> {


            viewModel = new ViewModelProvider(this).get(SelectAppointmentViewModel.class);
            viewModel.selectAppointment(SelectSlot.this,id,selectedDate,SlotAdapter.getStartTime(),SlotAdapter.getEnd()).observe(this, response -> {
                if (response != null) {
                    Log.e("ZTESTING","HERE 1 "+response);
                    Toast.makeText(this, "BOOKED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(this, "NOT BOOKED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    Log.e("ZTESTING","HERE 2 ");
                }
            });

        });

    }
    private String formatDate(int year, int month, int day) {
        return String.format("%04d-%02d-%02d", year, month + 1, day);
    }

    public void getCurrentSlots(String id,String date)
    {

        Log.e("ETESTING","HERE 4");
        // Observe the doctor list
        slotViewModel.getSlot(SelectSlot.this,id,date).observe(this, slots -> {
            if (slots != null) {
                Log.e("ETESTING","HERE 5"+slots.isSuccess());

                adapter.updateData(slots.getSlots());
            }
            else
            {
                Log.e("ETESTING","HERE 6");
                adapter.updateData(null);

                Toast.makeText(this, "NO RESPONSE", Toast.LENGTH_SHORT).show();
            }
        });
    }
}