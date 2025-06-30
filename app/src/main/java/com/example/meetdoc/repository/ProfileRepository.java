package com.example.meetdoc.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.meetdoc.models.getDoctorByIdModel;
import com.example.meetdoc.retrofit.ApiService;
import com.example.meetdoc.retrofit.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {

    private ApiService apiService;
    SharedPreferences sharedPreferences;
    public ProfileRepository() {

        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public LiveData<JsonObject> getPatient(Context context) {
        Log.e("BTESTING","HERE 19 ");
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        MutableLiveData<JsonObject> data = new MutableLiveData<>();
        String token = sharedPreferences.getString("user_token", null);
        String authHeader = "Bearer " + token;
        String id = sharedPreferences.getString("patient_id",null);
        JsonObject req = new JsonObject();
        req.addProperty("patient_id",id);
        apiService.getPatient(authHeader,req).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("CTESTING","HERE 20 ");
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("CTESTING","HERE 21 "+t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
}
