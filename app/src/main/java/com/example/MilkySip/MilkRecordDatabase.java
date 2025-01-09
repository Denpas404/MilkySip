package com.example.MilkySip;

import androidx.room.Database;
import  androidx.room.RoomDatabase;

import com.example.MilkySip.Interfaces.MilkRecordDAO;
import com.example.MilkySip.Models.MilkRecord;

@Database(entities = {MilkRecord.class}, version = 1)
public abstract class MilkRecordDatabase extends RoomDatabase {

    public abstract MilkRecordDAO milkRecordDAO();
}
