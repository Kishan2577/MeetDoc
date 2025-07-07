package com.example.meetdoc.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.meetdoc.models.Doctor;
import com.example.meetdoc.models.listDoctorModel;
import com.example.meetdoc.retrofit.ApiService;
import com.example.meetdoc.retrofit.RetrofitClient;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorRepository {
    private ApiService apiService;
    SharedPreferences sharedPreferences;
    public DoctorRepository() {

        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public LiveData<List<Doctor>> getDoctors(Context context) {
        Log.e("BTESTING","HERE 19 ");
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        MutableLiveData<List<Doctor>> data = new MutableLiveData<>();
        String token = sharedPreferences.getString("user_token", null);
        String authHeader = "Bearer " + token;
        apiService.getDoctors(authHeader).enqueue(new Callback<listDoctorModel>() {
            @Override
            public void onResponse(Call<listDoctorModel> call, Response<listDoctorModel> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Log.e("BTESTING","HERE 20 ");
                    data.setValue(response.body().getDoctors());
                }
            }

            @Override
            public void onFailure(Call<listDoctorModel> call, Throwable t) {
                Log.e("BTESTING","HERE 21 "+t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
}
