package com.example.meetdoc.doctor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.meetdoc.LoginActivity;
import com.example.meetdoc.MainActivity;
import com.example.meetdoc.R;
import com.example.meetdoc.adapter.CloudinaryAdapter;
import com.example.meetdoc.helper.CometChatHelper;
import com.example.meetdoc.viewmodel.LoginViewModel;
import com.example.meetdoc.viewmodel.RegisterViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorRegister extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 101;
    private static final int IMAGE_PICK_CODE = 102;

    private String selectedImagePath = null;

    private String uploadedImageUrl = null;


    CircleImageView ivProfile;
    Button btnUpload;

    TextInputEditText etFullName, etEmail, etPassword, etPhone, etMedicalRegNumber, spinnerState,
            etSpecialization, etMedicalCollege, etYearRegistration, etHospitalName,
            etYearsExperience, etPatientsChecked, etAbout;

    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ivProfile = findViewById(R.id.iv_profile);
        btnUpload = findViewById(R.id.tv_upload_photo);

        // Upload button click
        btnUpload.setOnClickListener(v -> requestStoragePermission());

        // Input fields
        etFullName = findViewById(R.id.et_full_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etPhone = findViewById(R.id.et_phone);
        etMedicalRegNumber = findViewById(R.id.et_medical_reg_number);
        spinnerState = findViewById(R.id.spinner_state);
        etSpecialization = findViewById(R.id.et_specialization);
        etMedicalCollege = findViewById(R.id.et_medical_college);
        etYearRegistration = findViewById(R.id.et_year_registration);
        etHospitalName = findViewById(R.id.et_hospital_name);
        etYearsExperience = findViewById(R.id.et_years_experience);
        etPatientsChecked = findViewById(R.id.et_patients_checked);
        etAbout = findViewById(R.id.et_about);
        btnRegister = findViewById(R.id.btn_register);

        // Register button validation
        btnRegister.setOnClickListener(v -> {
            boolean isValid = true;

            if (isEmpty(etFullName)) {
                etFullName.setError("Full Name is required");
                isValid = false;
            }
            if (isEmpty(etEmail)) {
                etEmail.setError("Email is required");
                isValid = false;
            }
            if (isEmpty(etPassword)) {
                etPassword.setError("Password is required");
                isValid = false;
            }
            if (isEmpty(etPhone)) {
                etPhone.setError("Phone number is required");
                isValid = false;
            }
            if (isEmpty(etMedicalRegNumber)) {
                etMedicalRegNumber.setError("Medical Reg No is required");
                isValid = false;
            }
            if (isEmpty(spinnerState)) {
                spinnerState.setError("State is required");
                isValid = false;
            }
            if (isEmpty(etSpecialization)) {
                etSpecialization.setError("Specialization is required");
                isValid = false;
            }
            if (isEmpty(etMedicalCollege)) {
                etMedicalCollege.setError("Medical College is required");
                isValid = false;
            }
            if (isEmpty(etYearRegistration)) {
                etYearRegistration.setError("Year of Registration is required");
                isValid = false;
            }
            if (isEmpty(etHospitalName)) {
                etHospitalName.setError("Hospital Name is required");
                isValid = false;
            }
            if (isEmpty(etYearsExperience)) {
                etYearsExperience.setError("Years of Experience is required");
                isValid = false;
            }
            if (isEmpty(etPatientsChecked)) {
                etPatientsChecked.setError("Patients Checked is required");
                isValid = false;
            }
            if (isEmpty(etAbout)) {
                etAbout.setError("About is required");
                isValid = false;
            }

            if (!isValid) {
                Toast.makeText(this, "Please fix the above errors", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedImagePath != null) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    CloudinaryAdapter.init();
                    uploadedImageUrl = CloudinaryAdapter.uploadImage(getText(etEmail), selectedImagePath);

                    runOnUiThread(() -> {
                        if (uploadedImageUrl != null) {

                            Toast.makeText(this, "Image uploaded: " + uploadedImageUrl, Toast.LENGTH_LONG).show();
                            uploadDoctor();

                        } else {
                            Toast.makeText(this, "Image upload failed!", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            }
            else
            {
                Toast.makeText(this, "Please upload a profile image", Toast.LENGTH_SHORT).show();
                return;
            }



        });
    }


    private void uploadDoctor()
    {
        String fullName = getText(etFullName);
        String email = getText(etEmail);
        String password = getText(etPassword);
        String phone = getText(etPhone);
        String medicalRegNumber = getText(etMedicalRegNumber);
        String state = getText(spinnerState);
        String specialization = getText(etSpecialization);
        String medicalCollege = getText(etMedicalCollege);
        String yearRegistration = getText(etYearRegistration);
        String hospitalName = getText(etHospitalName);
        String yearsExperience = getText(etYearsExperience);
        String patientsChecked = getText(etPatientsChecked);
        String about = getText(etAbout);
        String imageUrl = uploadedImageUrl; // set this from your Cloudinary upload response

        JsonObject request = new JsonObject();
        request.addProperty("fullName", fullName);
        request.addProperty("email", email);
        request.addProperty("password", password);
        request.addProperty("phoneNumber", phone);
        request.addProperty("medicalRegNumber", medicalRegNumber);
        request.addProperty("stateMedicalCouncil", state);
        request.addProperty("specialization", specialization);
        request.addProperty("medicalCollege", medicalCollege);
        request.addProperty("yearOfRegistration", yearRegistration);
        request.addProperty("hospitalClinicName", hospitalName);
        request.addProperty("yearsOfExperience", yearsExperience);
        request.addProperty("patientsChecked", patientsChecked);
        request.addProperty("about", about);
        request.addProperty("imageUrl", imageUrl);

        RegisterViewModel viewModel = new ViewModelProvider(DoctorRegister.this).get(RegisterViewModel.class);

        Log.e("CLOUDINARY","HERE 1");
        viewModel.sendDoctorData(DoctorRegister.this,request).observe(DoctorRegister.this, response -> {
            if (response != null) {
                Log.e("CLOUDINARY","HERE 2"+response.get("_id"));
                CometChatHelper.initCometChat(DoctorRegister.this);
                CometChatHelper.registerUser(response.get("_id").getAsString(),fullName);
                Toast.makeText(this, "WELCOME "+response.get("fullName").getAsString(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DoctorRegister.this, LoginActivity.class));
            } else {
                Log.e("CLOUDINARY","HERE 3");
                Toast.makeText(this, "ERROR "+response.get("message").getAsString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Request correct permission based on Android version
    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        STORAGE_PERMISSION_CODE
                );
            } else {
                openGallery();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        STORAGE_PERMISSION_CODE
                );
            } else {
                openGallery();
            }
        }
    }

    // Handle permission result and show appropriate message or action
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                boolean showRationale = false;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    showRationale = shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES);
                } else {
                    showRationale = shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE);
                }

                if (!showRationale) {
                    Toast.makeText(this, "Permission denied permanently. Enable it in settings.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Storage permission is needed to upload photo.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // Open image picker
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    private String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        android.database.Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return null;
    }


    // Set picked image to profile view
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            ivProfile.setImageURI(imageUri);
            selectedImagePath = getRealPathFromURI(imageUri);
        }
    }

    private boolean isEmpty(TextInputEditText editText) {
        return getText(editText).isEmpty();
    }

    private String getText(TextInputEditText editText) {
        return editText.getText() != null ? editText.getText().toString().trim() : "";
    }
}
