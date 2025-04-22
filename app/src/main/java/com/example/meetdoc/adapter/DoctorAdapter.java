package com.example.meetdoc.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.meetdoc.R;
import com.example.meetdoc.models.Doctor;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>{
    private List<Doctor> doctorList;

    public void setDoctorList(List<Doctor> doctors) {
        this.doctorList = doctors;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_card, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.nameTextView.setText(doctor.getFullName());
        holder.specializationTextView.setText(doctor.getSpecialization());

        // Load image using Glide
        Glide.with(holder.itemView.getContext())
                .load(doctor.getImageUrl())
                .placeholder(R.drawable.doctor_banner) // add a placeholder image in drawable
                .into(holder.doctorImage);
    }

    @Override
    public int getItemCount() {
        return (doctorList != null) ? doctorList.size() : 0;
    }

    static class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, specializationTextView;
        ImageView doctorImage;
        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.doctor_name);
            specializationTextView = itemView.findViewById(R.id.doctor_specialization);
            doctorImage = itemView.findViewById(R.id.doctorImage);
        }
    }
}
