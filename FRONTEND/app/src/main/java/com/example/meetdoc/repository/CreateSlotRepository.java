package com.example.meetdoc.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.meetdoc.models.createSlotModel;
import com.example.meetdoc.models.getDoctorByIdModel;
import com.example.meetdoc.models.reqCreateSlotModel;
import com.example.meetdoc.retrofit.ApiService;
import com.example.meetdoc.retrofit.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateSlotRepository {
    private ApiService apiService;
    SharedPreferences sharedPreferences;
    public CreateSlotRepository() {

        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public LiveData<createSlotModel> createSlot(Context context,String date,String start,String end,int limit,String notes) {
        Log.e("BTESTING","HERE 19 ");
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        MutableLiveData<createSlotModel> data = new MutableLiveData<>();
        String token = sharedPreferences.getString("doctor_token", null);
        String authHeader = "Bearer " + token;
        String id = sharedPreferences.getString("doctor_id",null);

        reqCreateSlotModel req = new reqCreateSlotModel();
        req.setDoctorId(id);
        req.setDate(date);
        req.setStartTime(start);
        req.setEndTime(end);
        req.setLimit(limit);
        req.setNotes(notes);
        Log.e("BTESTING","HERE  "+date);

        apiService.createSlot(authHeader,req).enqueue(new Callback<createSlotModel>() {
            @Override
            public void onResponse(Call<createSlotModel> call, Response<createSlotModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("BTESTING","HERE 20 ");
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<createSlotModel> call, Throwable t) {
                Log.e("BTESTING","HERE 21 "+t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
}
