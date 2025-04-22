package com.example.meetdoc.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.meetdoc.models.Doctor;
import com.example.meetdoc.models.getSlotModel;
import com.example.meetdoc.models.listDoctorModel;
import com.example.meetdoc.retrofit.ApiService;
import com.example.meetdoc.retrofit.RetrofitClient;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlotRepository {
    private ApiService apiService;
    SharedPreferences sharedPreferences;
    public SlotRepository() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public LiveData<getSlotModel> getSlot(Context context,String id,String date) {
        Log.e("ETESTING","HERE 19 ");
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        MutableLiveData<getSlotModel> data = new MutableLiveData<>();
        String token = sharedPreferences.getString("user_token", null);
        String authHeader = "Bearer " + token;
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("doctorId", id);
        requestBody.addProperty("date", date);
        Log.e("ETESTING","HERE 19.5 "+id+date);
        apiService.getSlot(authHeader,requestBody).enqueue(new Callback<getSlotModel>() {
            @Override
            public void onResponse(Call<getSlotModel> call, Response<getSlotModel> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Log.e("ETESTING","HERE 20 ");
                    data.setValue(response.body());
                }
                else{
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<getSlotModel> call, Throwable t) {
                Log.e("ETESTING","HERE 21 "+t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
}
