package com.example.meetdoc.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.meetdoc.models.loginModel;
import com.example.meetdoc.repository.LoginRepository;

public class LoginViewModel extends ViewModel {
    private LoginRepository repository;
    private LiveData<loginModel> patientRegisterResponse;

    public LoginViewModel() {

    }

    public LiveData<loginModel> login(String email,String pass,String role,Context context) {
        if (repository == null) {
            repository = new LoginRepository(context);
        }
        // always call login to get a fresh LiveData
        Log.e("ATESTING", "Calling login on repository");
        return repository.login(email, pass, role);
    }
}
