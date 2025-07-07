package com.example.meetdoc.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.meetdoc.models.createSlotModel;
import com.example.meetdoc.models.getDoctorByIdModel;
import com.example.meetdoc.repository.CreateSlotRepository;
import com.example.meetdoc.repository.DoctorByIdRepository;

public class CreateSlotViewModel extends ViewModel {
    private CreateSlotRepository repository;
    private LiveData<createSlotModel> slot;

    public LiveData<createSlotModel> createSlot(Context context,String date,String start,String end,int limit,String notes) {
        repository = new CreateSlotRepository();
        slot = repository.createSlot(context,date,start,end,limit,notes);
        Log.e("BTESTING","HERE 10 ");
        return slot;
    }
}
