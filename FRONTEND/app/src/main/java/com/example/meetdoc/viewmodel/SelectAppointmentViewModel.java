package com.example.meetdoc.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.meetdoc.models.getDoctorByIdModel;
import com.example.meetdoc.repository.DoctorByIdRepository;
import com.example.meetdoc.repository.SelectAppointment;
import com.google.gson.JsonObject;

public class SelectAppointmentViewModel extends ViewModel {
    private SelectAppointment repository;
    private LiveData<JsonObject> slot;

    public LiveData<JsonObject> selectAppointment(Context context,String doctor_id,String date_of_appointment,String start,String end) {
        repository = new SelectAppointment();
        slot = repository.selectAppointment(context,doctor_id,date_of_appointment,start,end);
        Log.e("BTESTING","HERE 10 ");
        return slot;
    }
}
