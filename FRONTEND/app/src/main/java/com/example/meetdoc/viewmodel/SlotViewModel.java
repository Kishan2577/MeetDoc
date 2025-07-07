package com.example.meetdoc.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.meetdoc.models.Doctor;
import com.example.meetdoc.models.getSlotModel;
import com.example.meetdoc.repository.DoctorRepository;
import com.example.meetdoc.repository.SlotRepository;

import java.util.List;

public class SlotViewModel extends ViewModel {
    private SlotRepository repository;
    private LiveData<getSlotModel> slots;

    public LiveData<getSlotModel> getSlot(Context context,String id,String date) {
        repository = new SlotRepository();
        slots = repository.getSlot(context,id,date);
        Log.e("ETESTING","HERE 10");
        return slots;
    }
}
