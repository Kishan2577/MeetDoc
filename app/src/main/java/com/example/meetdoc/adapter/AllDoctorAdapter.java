package com.example.meetdoc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.meetdoc.BookAppointmentActivity;
import com.example.meetdoc.models.Doctor;
import com.example.meetdoc.R;

import java.util.List;

public class AllDoctorAdapter extends RecyclerView.Adapter<AllDoctorAdapter.DoctorViewHolder> {
    private List<Doctor> doctorList;

    public void setDoctorList(List<Doctor> doctors) {
        this.doctorList = doctors;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_card2, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.nameTextView.setText(doctor.getFullName());
        holder.specializationTextView.setText(doctor.getSpecialization());
        holder.experienceTextView.setText("Experience: " + doctor.getYearsOfExperience() + " years");
        holder.ratingTextView.setText("Rating: " + doctor.getRating());

        Glide.with(holder.itemView.getContext())
                .load(doctor.getImageUrl())
                .placeholder(R.drawable.doctor_banner)
                .into(holder.doctorImageView);

        // Handle button click
        holder.bookButton.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, BookAppointmentActivity.class);
            intent.putExtra("doctor_id", doctor.get_id());  // assuming get_id() returns the ID
            intent.putExtra("doctor_img", doctor.getImageUrl());  // assuming get_id() returns the ID
            intent.putExtra("doctor_specs", doctor.getSpecialization());  // assuming get_id() returns the ID
            intent.putExtra("doctor_exp", String.valueOf(doctor.getYearsOfExperience()));  // assuming get_id() returns the ID
            intent.putExtra("doctor_checked", String.valueOf(doctor.getPatientsChecked()));  // assuming get_id() returns the ID
            intent.putExtra("doctor_rating", String.valueOf(doctor.getRating()));  // assuming get_id() returns the ID
            intent.putExtra("doctor_hospital", doctor.getHospitalClinicName());  // assuming get_id() returns the ID
            intent.putExtra("doctor_about", doctor.getAbout());  // assuming get_id() returns the ID
            intent.putExtra("doctor_name", doctor.getFullName());  // assuming get_id() returns the ID
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (doctorList != null) ? doctorList.size() : 0;
    }

    static class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, specializationTextView, experienceTextView, ratingTextView;
        ImageView doctorImageView;
        Button bookButton;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.doctor_name);
            specializationTextView = itemView.findViewById(R.id.doctor_specialization);
            experienceTextView = itemView.findViewById(R.id.doctor_experience);
            ratingTextView = itemView.findViewById(R.id.doctor_rating);
            doctorImageView = itemView.findViewById(R.id.doctor_image);
            bookButton = itemView.findViewById(R.id.actionButton);
        }
    }
}
