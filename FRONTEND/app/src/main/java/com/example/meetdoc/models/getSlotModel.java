package com.example.meetdoc.models;

import java.util.List;

public class getSlotModel {
    private boolean success;
    private List<Slot> slots;

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }
}
