package com.example.meetdoc.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.meetdoc.models.Doctor;
import com.example.meetdoc.repository.DoctorRepository;

import java.util.List;

public class DoctorViewModel extends ViewModel {
    private DoctorRepository repository;
    private LiveData<List<Doctor>> doctors;

    public LiveData<List<Doctor>> getDoctors(Context context) {
        repository = new DoctorRepository();
        doctors = repository.getDoctors(context);
        Log.e("BTESTING","HERE 10 ");
        return doctors;
    }
}
