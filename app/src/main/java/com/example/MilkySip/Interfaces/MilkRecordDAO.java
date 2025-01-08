package com.example.MilkySip.Interfaces;

import com.example.MilkySip.Models.MilkRecord;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface MilkRecordDAO {

    @Insert
    public void insert(MilkRecord milkRecord);

    @Update
    public void update(MilkRecord milkRecord);

    @Delete
    public void delete(MilkRecord milkRecord);

    @Query("SELECT * FROM log_table WHERE id = :id")
    public MilkRecord getMilkRecord(int id);

    @Query("SELECT * FROM log_table")
    public List<MilkRecord> getAll();

}
