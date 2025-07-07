package com.example.meetdoc;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.meetdoc.doctor.DoctorRegister;
import com.example.meetdoc.helper.CometChatHelper;
import com.example.meetdoc.viewmodel.PatientViewModel;
import com.example.meetdoc.viewmodel.RegisterViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

public class PatientRegister extends AppCompatActivity {
    TextInputEditText etName, etEmail, etPassword, etPhone, etAddress, etAge, etSex, etOccupation, etMaritalStatus;
    MaterialButton btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patient_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize views
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etPhone = findViewById(R.id.et_phone);
        etAddress = findViewById(R.id.et_address);
        etAge = findViewById(R.id.et_age);
        etSex = findViewById(R.id.et_sex);
        etOccupation = findViewById(R.id.et_occupation);
        etMaritalStatus = findViewById(R.id.et_marital_status);
        btnRegister = findViewById(R.id.btn_register);

        // Set click listener
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get input values
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String age = etAge.getText().toString().trim();
                String sex = etSex.getText().toString().trim();
                String occupation = etOccupation.getText().toString().trim();
                String maritalStatus = etMaritalStatus.getText().toString().trim();

                // Validate non-empty
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                        TextUtils.isEmpty(phone) || TextUtils.isEmpty(address) || TextUtils.isEmpty(age) ||
                        TextUtils.isEmpty(sex) || TextUtils.isEmpty(occupation) || TextUtils.isEmpty(maritalStatus)) {

                    Toast.makeText(PatientRegister.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                JsonObject request = new JsonObject();
                request.addProperty("name", name);
                request.addProperty("email", email);
                request.addProperty("password", password);
                request.addProperty("contactInfo", phone);
                request.addProperty("address", address);
                request.addProperty("age", age);
                request.addProperty("sex", sex);
                request.addProperty("occupation", occupation);
                request.addProperty("maritalStatus", maritalStatus);
                request.addProperty("role","patient");


                PatientViewModel viewModel = new ViewModelProvider(PatientRegister.this).get(PatientViewModel.class);

                Log.e("PATIENTDATA","HERE 1");
                viewModel.sendPatientData(PatientRegister.this,request).observe(PatientRegister.this, response -> {
                    if (response.get("_id") != null) {
                        CometChatHelper.initCometChat(PatientRegister.this);
                        CometChatHelper.registerUser(response.get("_id").getAsString(),name);
                        Log.e("PATIENTDATA","HERE 1.5"+response.get("_id"));
                        startActivity(new Intent(PatientRegister.this, LoginActivity.class));
                        Log.e("PATIENTDATA","HERE 2");
                    } else {
                        Toast.makeText(PatientRegister.this, "REGISTRATION FAILED", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}