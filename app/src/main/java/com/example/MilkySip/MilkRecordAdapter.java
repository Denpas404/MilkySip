package com.example.MilkySip;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MilkySip.Models.MilkRecord;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class MilkRecordAdapter extends RecyclerView.Adapter<MilkRecordAdapter.MilkRecordViewHolder> {

    private final List<MilkRecord> milkRecordList;
    private final OnItemClickListener listener;

    // Constructor
    public MilkRecordAdapter(List<MilkRecord> milkRecordList, OnItemClickListener listener) {
        this.milkRecordList = milkRecordList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MilkRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewlayout, parent, false);
        return new MilkRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MilkRecordViewHolder holder, int position) {
        MilkRecord milkRecord = milkRecordList.get(position);

        // Format and set the timeStamp
        String formattedTimeStamp = formatTimeStamp(milkRecord.getTimeStamp());
        holder.textViewDate.setText(formattedTimeStamp);

//        holder.textViewDate.setText(milkRecord.getTimeStamp());
        holder.textViewMilk.setText(String.valueOf(milkRecord.getAmountOfMilk()));
    }

    @Override
    public int getItemCount() {
        return milkRecordList.size();
    }

    // ViewHolder class
    public class MilkRecordViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate;
        TextView textViewMilk;
        View fabEdit;
        View fabDelete;

        public MilkRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewMilk = itemView.findViewById(R.id.textViewMilk);
            fabEdit = itemView.findViewById(R.id.fabEdit);
            fabDelete = itemView.findViewById(R.id.fabDelete);

            // Edit Button Click
            fabEdit.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onEditClick(position);
                    }
                }
            });

            // Delete Button Click
            fabDelete.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position);
                    }
                }
            });
        }
    }

    private String formatTimeStamp(String timeStamp) {
        try {
            DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
            DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("dd-MM - HH:mm", Locale.US);
            LocalDateTime dateTime = LocalDateTime.parse(timeStamp, originalFormatter);
            return dateTime.format(newFormatter);
        } catch (Exception e) {
            Log.e("MilkRecordAdapter", "Error formatting timestamp: " + timeStamp, e);
            return timeStamp;
        }
    }


    // Interface for click events
    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }
}
