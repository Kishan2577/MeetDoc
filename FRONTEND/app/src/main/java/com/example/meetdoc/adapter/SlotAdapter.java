package com.example.meetdoc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.meetdoc.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetdoc.models.Slot;

import java.util.List;

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.SlotViewHolder> {
    private List<Slot> slotList;
    private Context context;

    public static String start="";
    public static String end="";

    public SlotAdapter(List<Slot> slotList, Context context) {
        this.slotList = slotList;
        this.context = context;
    }

    public void updateData(List<Slot> newSlots) {
        if(newSlots!=null)
        {
            slotList.clear();              // Clear old data
            slotList.addAll(newSlots);     // Add new data
            notifyDataSetChanged();        // Notify adapter
        }
        else
        {
            slotList.clear();
            notifyDataSetChanged();
        }

    }

    @NonNull
    @Override
    public SlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_slot, parent, false);
        return new SlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlotViewHolder holder, int position) {
        Slot slot = slotList.get(position);
        holder.slotTime.setText(slot.getStartTime() + " - " + slot.getEndTime());
        holder.slotNote.setText(slot.getNotes());

        holder.itemView.setOnClickListener(v -> {
            String msg = "Selected Slot: " + slot.getStartTime() + " to " + slot.getEndTime();
            start = slot.getStartTime();
            end = slot.getEndTime();
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_bright));
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return slotList.size();
    }

    static class SlotViewHolder extends RecyclerView.ViewHolder {
        TextView slotTime, slotNote;

        public SlotViewHolder(@NonNull View itemView) {
            super(itemView);
            slotTime = itemView.findViewById(R.id.slotTime);
            slotNote = itemView.findViewById(R.id.slotNote);
        }
    }

    public static String getStartTime()
    {
        return start;
    }
    public static String getEnd()
    {
        return end;
    }
}
