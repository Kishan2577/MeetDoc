package com.example.meetdoc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookAppointmentActivity extends AppCompatActivity {
    String doctorId, doctorImg, doctorSpecs, doctorExp, doctorChecked, doctorRating, doctorHospital, doctorAbout,doctorName;
    CircleImageView imgDoctor;
    TextView tvDoctorName,tvSpecialty,tvHospital,tvPatients,tvExperience,tvReviews,tvBio;
    Button btnBookAppointment,btnMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_appointment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgDoctor = findViewById(R.id.imgDoctor);
        tvDoctorName = findViewById(R.id.tvDoctorName);
        tvSpecialty = findViewById(R.id.tvSpecialty);
        tvHospital = findViewById(R.id.tvHospital);
        tvPatients = findViewById(R.id.tvPatients);
        tvExperience = findViewById(R.id.tvExperience);
        tvReviews = findViewById(R.id.tvReviews);
        tvBio = findViewById(R.id.tvBio);
        btnBookAppointment = findViewById(R.id.btnBookAppointment);
        btnMessage = findViewById(R.id.btnMessage);

        Intent intent = getIntent();
        doctorName = intent.getStringExtra("doctor_name");
        doctorId = intent.getStringExtra("doctor_id");
        doctorImg = intent.getStringExtra("doctor_img");
        doctorSpecs = intent.getStringExtra("doctor_specs");
        doctorExp = intent.getStringExtra("doctor_exp");
        doctorChecked = intent.getStringExtra("doctor_checked");
        doctorRating = intent.getStringExtra("doctor_rating");
        doctorHospital = intent.getStringExtra("doctor_hospital");
        doctorAbout = intent.getStringExtra("doctor_about");

        Log.e("DTESTING","HERE 1"+doctorRating+doctorExp+doctorChecked);
        Glide.with(this)
                .load(doctorImg)
                .placeholder(R.drawable.doctor_banner) // optional placeholder
                .error(R.drawable.doctor_banner) // optional fallback
                .into(imgDoctor);

        tvDoctorName.setText(doctorName);
        tvSpecialty.setText(doctorSpecs);
        tvHospital.setText(doctorHospital);
        tvPatients.setText(doctorChecked);
        tvExperience.setText(doctorExp);
        tvReviews.setText(doctorRating);
        tvBio.setText(doctorAbout);

        btnBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookAppointmentActivity.this, SelectSlot.class);
                intent.putExtra("id",doctorId);
                intent.putExtra("doctor_img", doctorImg);  // assuming get_id() returns the ID
                intent.putExtra("doctor_specs", doctorSpecs);  // assuming get_id() returns the ID
                intent.putExtra("doctor_exp", doctorExp);  // assuming get_id() returns the ID
                intent.putExtra("doctor_checked", doctorChecked);  // assuming get_id() returns the ID
                intent.putExtra("doctor_rating", doctorRating);  // assuming get_id() returns the ID
                intent.putExtra("doctor_hospital", doctorHospital);  // assuming get_id() returns the ID
                intent.putExtra("doctor_about", doctorAbout);  // assuming get_id() returns the ID
                intent.putExtra("doctor_name", doctorName);
                startActivity(intent);
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookAppointmentActivity.this,ChatActivity.class);
                intent.putExtra("uid",doctorId);
                startActivity(intent);
            }
        });
    }
}