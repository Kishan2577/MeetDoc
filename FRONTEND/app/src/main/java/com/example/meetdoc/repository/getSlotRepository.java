package com.example.meetdoc.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.meetdoc.models.getDoctorByIdModel;
import com.example.meetdoc.models.slotByDateModel;
import com.example.meetdoc.retrofit.ApiService;
import com.example.meetdoc.retrofit.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class getSlotRepository {
    private ApiService apiService;
    SharedPreferences sharedPreferences;
    public getSlotRepository() {

        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public LiveData<slotByDateModel> getSlotsByDate(Context context) {
        Log.e("ZTESTING","HERE 19 ");
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        MutableLiveData<slotByDateModel> data = new MutableLiveData<>();
        String token = sharedPreferences.getString("user_token", null);
        String authHeader = "Bearer " + token;
        String id = sharedPreferences.getString("patient_id",null);
        JsonObject req = new JsonObject();
        req.addProperty("patient_id",id);
        apiService.getSlotsByDate(authHeader,req).enqueue(new Callback<slotByDateModel>() {
            @Override
            public void onResponse(Call<slotByDateModel> call, Response<slotByDateModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("ZTESTING","HERE 20 "+response.body().getMessage());
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<slotByDateModel> call, Throwable t) {
                Log.e("ZTESTING","HERE 21 "+t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
}
