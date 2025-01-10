package com.example.MilkySip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MilkySip.Models.MilkRecord;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private List<MilkRecord> milkRecord;

    public RecyclerViewAdapter(List<MilkRecord> milkRecord) {
        this.milkRecord = milkRecord;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        MilkRecord log = milkRecord.get(position);
        holder.timestampTextView.setText(log.getTimeStamp());
        holder.amountTextView.setText(String.valueOf(log.getAmountOfMilk()) + " ml");
    }

    @Override
    public int getItemCount() {
        return milkRecord.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView timestampTextView;
        TextView amountTextView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            timestampTextView = itemView.findViewById(android.R.id.text1);
            amountTextView = itemView.findViewById(android.R.id.text2);
        }
    }
}
