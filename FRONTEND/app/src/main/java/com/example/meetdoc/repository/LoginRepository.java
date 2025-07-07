package com.example.meetdoc.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.meetdoc.models.loginModel;
import com.example.meetdoc.models.loginRequestModel;
import com.example.meetdoc.retrofit.ApiService;
import com.example.meetdoc.retrofit.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private ApiService apiService;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    public LoginRepository(Context context) {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public LiveData<loginModel> login(String email,String pass,String role) {
        Log.e("ATESTING","HERE 19 ");
        MutableLiveData<loginModel> data = new MutableLiveData<>();
//        JsonObject requestBody = new JsonObject();
//        requestBody.addProperty("email", email);
//        requestBody.addProperty("password", pass);
//        requestBody.addProperty("role", role);

        loginRequestModel requestModel= new loginRequestModel(email,pass,role);
        Log.e("ATESTING","HERE 20 ");
        apiService.login(requestModel).enqueue(new Callback<loginModel>() {
            @Override
            public void onResponse(Call<loginModel> call, Response<loginModel> response) {
                Log.e("ATESTING","HERE 20.5 "+response.body());
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("ATESTING","HERE 21 ");
                    editor.putString("user_token", response.body().getToken());
                    editor.putString("patient_id",response.body().getId());
                    editor.apply();
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<loginModel> call, Throwable t) {
                Log.e("ATESTING","HERE 22 "+t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
}
