package com.example.meetdoc.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.meetdoc.repository.ProfileRepository;
import com.example.meetdoc.repository.RegisterRepository;
import com.google.gson.JsonObject;

public class RegisterViewModel extends ViewModel {
    private RegisterRepository repository;
    private LiveData<JsonObject> data;

    public LiveData<JsonObject> sendDoctorData(Context context,JsonObject request) {
        Log.e("CLOUDINARY","HERE 10");
        repository = new RegisterRepository();
        data = repository.sendDoctorData(context,request);
        Log.e("CLOUDINARY","HERE 11");
        return data;
    }
}
