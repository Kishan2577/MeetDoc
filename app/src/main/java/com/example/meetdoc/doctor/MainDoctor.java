package com.example.meetdoc.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.meetdoc.R;
import com.example.meetdoc.viewmodel.DoctorByIdViewModel;
import com.example.meetdoc.viewmodel.DoctorViewModel;
import com.google.android.material.button.MaterialButton;

public class MainDoctor extends AppCompatActivity {
    DoctorByIdViewModel doctorByIdViewModel;
    ImageView docImg;

    MaterialButton btnBookAppointment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_dash_board);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvDoctorName = findViewById(R.id.tvDoctorName);
        TextView tvSpecialty = findViewById(R.id.tvSpecialty);
        TextView tvRating = findViewById(R.id.tvRating);
        TextView tvPatients = findViewById(R.id.PatientsNo);
        TextView tvExperience = findViewById(R.id.ExperienceNo);
        TextView tvReviews = findViewById(R.id.ReviewsNo);
        TextView tvAboutMeContent = findViewById(R.id.tvAboutMeContent);
        docImg = findViewById(R.id.docImg);
        btnBookAppointment = findViewById(R.id.btnBookAppointment);

        doctorByIdViewModel = new ViewModelProvider(this).get(DoctorByIdViewModel.class);
        // Observe the doctor list
        doctorByIdViewModel.getDoctorById(this).observe(this, doctors -> {
            if (doctors != null) {
                Log.e("CTESTING","HERE 5"+doctors.getDoctorDetails().getFullName());
                //Toast.makeText(this, "NAME = "+doctors.getDoctorDetails().getYearsOfExperience(), Toast.LENGTH_SHORT).show();
                //SHOW HERE
                tvTitle.setText(doctors.getDoctorDetails().getFullName());
                tvDoctorName.setText(doctors.getDoctorDetails().getFullName());
                tvSpecialty.setText(doctors.getDoctorDetails().getSpecialization());
                tvRating.setText(String.valueOf(doctors.getDoctorDetails().getRating()));
                tvAboutMeContent.setText(doctors.getDoctorDetails().getAbout());
                tvExperience.setText(String.valueOf(doctors.getDoctorDetails().getYearsOfExperience()));
                tvPatients.setText(String.valueOf(doctors.getDoctorDetails().getPatientsChecked()));
                tvReviews.setText(String.valueOf(doctors.getDoctorDetails().getRating()));
                Glide.with(MainDoctor.this)
                        .load(doctors.getDoctorDetails().getImageUrl())
                        .placeholder(R.drawable.doctor_banner) // optional placeholder
                        .error(R.drawable.doctor_banner) // optional fallback
                        .into(docImg);

            }
            else
            {
                Log.e("CTESTING","HERE 6");
                Toast.makeText(this, "NO RESPONSE", Toast.LENGTH_SHORT).show();
            }
        });

        btnBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainDoctor.this,CreateSlots.class));
            }
        });
    }
}