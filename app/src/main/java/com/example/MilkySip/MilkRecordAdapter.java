package com.example.MilkySip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MilkySip.Models.MilkRecord;

import java.util.List;

public class MilkRecordAdapter extends RecyclerView.Adapter<MilkRecordAdapter.MilkRecordViewHolder> {

    private List<MilkRecord> milkRecordList;

    public MilkRecordAdapter(List<MilkRecord> milkRecordList) {
        this.milkRecordList = milkRecordList;
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
        holder.textViewDate.setText(milkRecord.getTimeStamp());
        holder.textViewMilk.setText(String.valueOf(milkRecord.getAmountOfMilk()));
    }

    @Override
    public int getItemCount() {
        return milkRecordList.size();
    }

    public static class MilkRecordViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate;
        TextView textViewMilk;

        public MilkRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewMilk = itemView.findViewById(R.id.textViewMilk);
        }
    }
}