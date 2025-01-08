package com.example.MilkySip.Models;

import java.util.Date;

public class MilkRecord {

    private int id;
    private Date dateTime;
    private Double amountOfMilk; // Bruger Double for at tillade decimaltal

    // Konstruktorer
    public MilkRecord(int id, Date dateTime) {
        this.id = id;
        this.dateTime = dateTime;
    }

    public MilkRecord(int id, Date dateTime, Double amountOfMilk) {
        this.id = id;
        this.dateTime = dateTime;
        this.amountOfMilk = amountOfMilk;
    }

    // Getters og setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
