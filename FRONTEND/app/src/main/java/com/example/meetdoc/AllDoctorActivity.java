package com.example.meetdoc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetdoc.adapter.AllDoctorAdapter;
import com.example.meetdoc.viewmodel.DoctorViewModel;
import com.example.meetdoc.helper.CometChatHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AllDoctorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AllDoctorAdapter doctorAdapter;
    private DoctorViewModel doctorViewModel;
    private FloatingActionButton filterFab;
    private static final String TAG = "AllDoctorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_doctor);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        recyclerView = findViewById(R.id.allDoctorsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        filterFab = findViewById(R.id.filterFab);
        String speciality = getIntent().getStringExtra("speciality");
        doctorAdapter = new AllDoctorAdapter(speciality);
        recyclerView.setAdapter(doctorAdapter);

        doctorViewModel = new ViewModelProvider(this).get(DoctorViewModel.class);
        doctorViewModel.getDoctors(this).observe(this, doctors -> {
            if (doctors != null) {
                doctorAdapter.setDoctorList(doctors);
            } else {
                Toast.makeText(this, "No doctors found", Toast.LENGTH_SHORT).show();
            }
        });

        filterFab.setOnClickListener(v -> startActivity(new Intent(AllDoctorActivity.this, ConversationActivity.class)));
    }
}
