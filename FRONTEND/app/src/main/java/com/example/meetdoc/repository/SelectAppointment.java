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

public class SelectAppointment {
    private ApiService apiService;
    SharedPreferences sharedPreferences;
    public SelectAppointment() {

        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public LiveData<JsonObject> selectAppointment(Context context,String doctor_id,String date_of_appointment,String start,String end) {
        Log.e("BTESTING","HERE 19 ");
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        MutableLiveData<JsonObject> data = new MutableLiveData<>();
        String token = sharedPreferences.getString("user_token", null);
        String authHeader = "Bearer " + token;
        String id = sharedPreferences.getString("patient_id",null);
        JsonObject req = new JsonObject();
        req.addProperty("patient_id",id);
        req.addProperty("doctor_id",doctor_id);
        req.addProperty("date_of_appointment",date_of_appointment);
        req.addProperty("start_time",start);
        req.addProperty("end_time",end);
        Log.e("YTESTING","HERE 18 "+ req);

        apiService.selectAppointment(authHeader,req).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("BTESTING","HERE 20 ");
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("BTESTING","HERE 21 "+t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
}
