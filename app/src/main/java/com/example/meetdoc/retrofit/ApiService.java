package com.example.meetdoc.retrofit;

import com.example.meetdoc.models.Doctor;
import com.example.meetdoc.models.createSlotModel;
import com.example.meetdoc.models.getDoctorByIdModel;
import com.example.meetdoc.models.getSlotModel;
import com.example.meetdoc.models.listDoctorModel;
import com.example.meetdoc.models.loginModel;
import com.example.meetdoc.models.loginRequestModel;
import com.example.meetdoc.models.reqCreateSlotModel;
import com.example.meetdoc.models.slotByDateModel;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @GET("/api/v1/list")  // Replace with actual endpoint
    Call<listDoctorModel> getDoctors(@Header("Authorization") String token);

    @POST("api/v1/login")
    Call<loginModel> login(@Body loginRequestModel details);

    @POST("api/v1/by-date")
    Call<getSlotModel> getSlot(@Header("Authorization") String token,@Body JsonObject request);

    @POST("api/v1/getDoctors")
    Call<getDoctorByIdModel> getDoctorById(@Header("Authorization") String token, @Body JsonObject request);

    @POST("api/v1/createSlots")
    Call<createSlotModel> createSlot(@Header("Authorization") String token, @Body reqCreateSlotModel request);

    @POST("api/v1/get-by-patient")
    Call<slotByDateModel> getSlotsByDate(@Header("Authorization") String token, @Body JsonObject request);

    @POST("api/v1/createAppointment")
    Call<JsonObject> selectAppointment(@Header("Authorization") String token, @Body JsonObject request);

    @POST("api/v1/get-patient")
    Call<JsonObject> getPatient(@Header("Authorization") String token, @Body JsonObject request);

    @POST("api/v1/doctorRegister")
    Call<JsonObject> sendDoctorData(@Body JsonObject request);

    @POST("api/v1/patientRegister")
    Call<JsonObject> sendPatientData(@Body JsonObject request);

}
