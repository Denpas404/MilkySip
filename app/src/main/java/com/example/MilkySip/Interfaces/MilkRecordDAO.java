package com.example.MilkySip.Interfaces;

import com.example.MilkySip.Models.MilkRecord;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MilkRecordDAO {

    @Insert
    void add_milkRecord(MilkRecord milkRecord);

    @Update
    void update_milkRecord(MilkRecord milkRecord);

    @Delete
    void delete_milkRecord(MilkRecord milkRecord);

    @Query("SELECT * FROM log_table WHERE id = :id")
    MilkRecord getMilkRecord(int id);

    @Query("SELECT * FROM log_table")
    List<MilkRecord> getAll();

}
