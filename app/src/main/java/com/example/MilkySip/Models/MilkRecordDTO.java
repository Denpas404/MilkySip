package com.example.MilkySip.Models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MilkRecordDTO {

    private int id;
    private Date timeStamp;
    private double amountOfMilk;

    // Constructor
    public MilkRecordDTO(int id, String timeStamp, double amountOfMilk) {
        this.id = id;
        this.amountOfMilk = amountOfMilk;
        try {
            // Brug SimpleDateFormat til at konvertere String til Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.timeStamp = sdf.parse(timeStamp);
        } catch (Exception e) {
            e.printStackTrace();
            this.timeStamp = null;
        }
    }
    public MilkRecordDTO() {
    }

    // Getter og Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.timeStamp = sdf.parse(timeStamp);
        } catch (Exception e) {
            e.printStackTrace();
            this.timeStamp = null;
        }
    }

    public double getAmountOfMilk() {
        return amountOfMilk;
    }

    public void setAmountOfMilk(double amountOfMilk) {
        this.amountOfMilk = amountOfMilk;
    }
}
