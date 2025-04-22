package com.example.meetdoc.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetdoc.R;
import com.example.meetdoc.models.slotByDateModel;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class GetSlotAdapter extends RecyclerView.Adapter<GetSlotAdapter.AppointmentViewHolder> {
    private slotByDateModel appointmentList;
    private Context context;
    public GetSlotAdapter(Context context,slotByDateModel appointmentList) {
        this.appointmentList = appointmentList;
        this.context = context;

    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appointment, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {

        slotByDateModel.Appointment appointment = appointmentList.getAppointments().get(position);
        Instant instant = Instant.parse(appointment.getDate_of_appointment());
        LocalDate date = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        holder.textDate.setText("Date: " + date);
        holder.textTime.setText("Time: " + appointment.getStart_time() + " - " + appointment.getEnd_time());
        holder.textUrl.setText("Video Call Link: " + appointment.getUrl());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(appointment.getUrl()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return appointmentList.getAppointments().size();
    }

    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView textDate, textTime, textUrl;
        CardView cardView;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            textDate = itemView.findViewById(R.id.text_date);
            textTime = itemView.findViewById(R.id.text_time);
            textUrl = itemView.findViewById(R.id.text_url);
        }
    }
}
