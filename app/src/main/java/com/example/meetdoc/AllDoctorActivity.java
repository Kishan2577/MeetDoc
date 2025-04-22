package com.example.meetdoc;

import android.os.Bundle;
import android.util.Log;
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
import com.example.meetdoc.adapter.DoctorAdapter;
import com.example.meetdoc.viewmodel.DoctorViewModel;

public class AllDoctorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AllDoctorAdapter doctorAdapter;
    private DoctorViewModel doctorViewModel;
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
        Log.e("CTESTING","HERE 1");
        recyclerView = findViewById(R.id.allDoctorsRecyclerView); // replace with your RecyclerView id
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.e("CTESTING","HERE 2");
        doctorAdapter = new AllDoctorAdapter();
        recyclerView.setAdapter(doctorAdapter);
        Log.e("CTESTING","HERE 3");
        doctorViewModel = new ViewModelProvider(this).get(DoctorViewModel.class);
        Log.e("CTESTING","HERE 4");
        // Observe the doctor list
        doctorViewModel.getDoctors(this).observe(this, doctors -> {
            if (doctors != null) {
                Log.e("CTESTING","HERE 5"+doctors.get(0).getFullName());
                doctorAdapter.setDoctorList(doctors);
            }
            else
            {
                Log.e("CTESTING","HERE 6");
                Toast.makeText(this, "NO RESPONSE", Toast.LENGTH_SHORT).show();
            }
        });
    }
}