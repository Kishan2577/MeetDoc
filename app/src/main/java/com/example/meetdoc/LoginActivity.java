package com.example.meetdoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.meetdoc.doctor.DoctorRegister;
import com.example.meetdoc.doctor.MainDoctor;
import com.example.meetdoc.helper.CometChatHelper;
import com.example.meetdoc.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    EditText email,pass;
    Spinner userRoleSpinner;
    Button login;

    TextView signup_doctor,signup_patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        login = findViewById(R.id.login_button);
        signup_doctor = findViewById(R.id.signup_doctor);
        signup_patient = findViewById(R.id.signup_patient);
        Log.e("TESTING","HERE 1");
        userRoleSpinner = findViewById(R.id.user_role_spinner);
        String[] roles = {"patient", "doctor"};
        Log.e("TESTING","HERE 2");
        signup_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, DoctorRegister.class)) ;
            }
        });
        signup_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, PatientRegister.class)) ;
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roles);
        userRoleSpinner.setAdapter(adapter);
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString().trim();
                String userPass = pass.getText().toString().trim();
                String userRole = userRoleSpinner.getSelectedItem().toString();

                if (userEmail.isEmpty() || userPass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginViewModel viewModel = new ViewModelProvider(LoginActivity.this).get(LoginViewModel.class);

                viewModel.login(userEmail,userPass,userRole,LoginActivity.this).observe(LoginActivity.this, response -> {
                    if (response != null) {

                        if(response.getRole().equals("doctor"))
                        {
                            editor.putString("doctor_token", response.getToken());
                            editor.putString("doctor_id", response.getId());
                            editor.apply();
                            CometChatHelper.initCometChat(LoginActivity.this);
                            CometChatHelper.loginUser(response.getId());

                            startActivity(new Intent(LoginActivity.this, MainDoctor.class));
                        }
                        else if(response.getRole().equals("patient"))
                        {
                            // Initialize and login to CometChat
                            CometChatHelper.initCometChat(LoginActivity.this);
                            CometChatHelper.loginUser(response.getId());
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }

                    } else {
                        Toast.makeText(LoginActivity.this, "LOGIN UNSUCCESSFUL", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}