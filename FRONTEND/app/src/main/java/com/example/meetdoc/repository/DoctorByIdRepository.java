package com.example.meetdoc.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.meetdoc.models.Doctor;
import com.example.meetdoc.models.getDoctorByIdModel;
import com.example.meetdoc.models.listDoctorModel;
import com.example.meetdoc.retrofit.ApiService;
import com.example.meetdoc.retrofit.RetrofitClient;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorByIdRepository {
    private ApiService apiService;
    SharedPreferences sharedPreferences;
    public DoctorByIdRepository() {

        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public LiveData<getDoctorByIdModel> getDoctorsById(Context context) {
        Log.e("BTESTING","HERE 19 ");
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        MutableLiveData<getDoctorByIdModel> data = new MutableLiveData<>();
        String token = sharedPreferences.getString("doctor_token", null);
        String authHeader = "Bearer " + token;
        String id = sharedPreferences.getString("doctor_id",null);
        JsonObject req = new JsonObject();
        req.addProperty("_id",id);
        apiService.getDoctorById(authHeader,req).enqueue(new Callback<getDoctorByIdModel>() {
            @Override
            public void onResponse(Call<getDoctorByIdModel> call, Response<getDoctorByIdModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("BTESTING","HERE 20 ");
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<getDoctorByIdModel> call, Throwable t) {
                Log.e("BTESTING","HERE 21 "+t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
}
