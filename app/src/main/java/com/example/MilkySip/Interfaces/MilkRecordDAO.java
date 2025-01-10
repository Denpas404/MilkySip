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

    @Query("SELECT * FROM log_table WHERE strftime('%Y-%m-%d', timestamp) = :currentDate ORDER BY timestamp ASC")
    List<MilkRecord> getAllMilkRecordsForToday(String currentDate);

    @Query("SELECT * FROM log_table ORDER BY timestamp ASC")
    List<MilkRecord> getAllMilkRecordsSortedByOldest();

    @Query("SELECT * FROM log_table WHERE timeStamp LIKE :selectedDate ORDER BY timeStamp ASC")
    List<MilkRecord> getMilkRecordsForDate(String selectedDate);

}
