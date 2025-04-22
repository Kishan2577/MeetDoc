package com.example.meetdoc.models;

public class getDoctorByIdModel {
    private boolean success;
    private DoctorByIdModel doctor;

    public boolean isSuccess() {
        return success;
    }

    public DoctorByIdModel getDoctorDetails() {
        return doctor;
    }
}
