package com.example.MilkySip.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "log_table")

public class MilkRecord {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "timeStamp")
    private String timeStamp;

    @ColumnInfo(name = "amount_of_milk")
    private Double amountOfMilk; // Bruger Double for at tillade decimaltal

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Double getAmountOfMilk() {
        return amountOfMilk;
    }

    public void setAmountOfMilk(Double amountOfMilk) {
        this.amountOfMilk = amountOfMilk;
    }
}
