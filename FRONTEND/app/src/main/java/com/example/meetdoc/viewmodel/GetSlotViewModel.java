package com.example.meetdoc.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.meetdoc.models.Doctor;
import com.example.meetdoc.models.slotByDateModel;
import com.example.meetdoc.repository.DoctorRepository;
import com.example.meetdoc.repository.getSlotRepository;

import java.util.List;

public class GetSlotViewModel extends ViewModel {
    private getSlotRepository repository;
    private LiveData<slotByDateModel> slots;

    public LiveData<slotByDateModel> getSlotsByDate(Context context) {
        repository = new getSlotRepository();
        slots = repository.getSlotsByDate(context);
        Log.e("BTESTING","HERE 10 ");
        return slots;
    }
}
