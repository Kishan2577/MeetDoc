package com.example.meetdoc.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.meetdoc.repository.PatientRepository;
import com.example.meetdoc.repository.RegisterRepository;
import com.google.gson.JsonObject;

public class PatientViewModel extends ViewModel {
    private PatientRepository repository;
    private LiveData<JsonObject> data;

    public LiveData<JsonObject> sendPatientData(Context context, JsonObject request) {
        Log.e("PATIENTDATA","HERE 10");
        repository = new PatientRepository();
        data = repository.sendPatientData(context,request);
        Log.e("PATIENTDATA","HERE 11");
        return data;
    }
}
