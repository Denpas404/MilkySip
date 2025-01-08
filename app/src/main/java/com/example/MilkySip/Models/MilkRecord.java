package com.example.MilkySip.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

import java.util.Date;

@Entity(tableName = "log_table")

public class MilkRecord {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "date_time")
    private Date dateTime;
    @ColumnInfo(name = "amount_of_milk")
    private Double amountOfMilk; // Bruger Double for at tillade decimaltal

    // Getters og setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        // Fjern setter for id - id bør kun sættes af Room
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Double getAmountOfMilk() {
        return amountOfMilk;
    }

    public void setAmountOfMilk(Double amountOfMilk) {
        this.amountOfMilk = amountOfMilk;
    }
}
