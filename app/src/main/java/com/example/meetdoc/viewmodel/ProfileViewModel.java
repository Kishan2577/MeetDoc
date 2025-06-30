package com.example.meetdoc.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.meetdoc.repository.ProfileRepository;
import com.example.meetdoc.repository.SelectAppointment;
import com.google.gson.JsonObject;

public class ProfileViewModel extends ViewModel {

    private ProfileRepository repository;
    private LiveData<JsonObject> data;

    public LiveData<JsonObject> getPatient(Context context) {
        repository = new ProfileRepository();
        data = repository.getPatient(context);
        Log.e("CTESTING","HERE 10 ");
        return data;
    }
}
