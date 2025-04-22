package com.example.meetdoc.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.meetdoc.models.Doctor;
import com.example.meetdoc.models.getDoctorByIdModel;
import com.example.meetdoc.repository.DoctorByIdRepository;
import com.example.meetdoc.repository.DoctorRepository;

import java.util.List;

public class DoctorByIdViewModel extends ViewModel {
    private DoctorByIdRepository repository;
    private LiveData<getDoctorByIdModel> doctors;

    public LiveData<getDoctorByIdModel> getDoctorById(Context context) {
        repository = new DoctorByIdRepository();
        doctors = repository.getDoctorsById(context);
        Log.e("BTESTING","HERE 10 ");
        return doctors;
    }
}
