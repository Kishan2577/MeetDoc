package com.example.meetdoc;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetdoc.adapter.GetSlotAdapter;
import com.example.meetdoc.viewmodel.GetSlotViewModel;

public class PatientAppointment extends AppCompatActivity {

    private GetSlotViewModel viewModel;
    //private GetSlotAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patient_appointment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        adapter = new GetSlotAdapter();
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(GetSlotViewModel.class);
        viewModel.getSlotsByDate(PatientAppointment.this).observe(this, appointments -> {
            if (appointments != null) {
                //adapter.setAppointments(appointments);
                Log.e("ZTESTING","HERE 1 "+appointments);
                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

// Assume you already have a List<slotByDateModel.Appointment> called appointmentList
                GetSlotAdapter adapter = new GetSlotAdapter(PatientAppointment.this,appointments);
                recyclerView.setAdapter(adapter);

            }
            else
            {
                Log.e("ZTESTING","HERE 2 ");
            }
        });
    }
}