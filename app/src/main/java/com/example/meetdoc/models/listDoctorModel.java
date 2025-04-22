package com.example.meetdoc.models;

import java.util.List;

public class listDoctorModel {
    private boolean success;
    private List<Doctor> doctors;

    public boolean isSuccess() {
        return success;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }
}
