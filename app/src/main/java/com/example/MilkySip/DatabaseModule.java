package com.example.MilkySip;

import android.app.Application;

import androidx.room.Room;

import com.example.MilkySip.MilkRecordDatabase;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import jakarta.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    public static MilkRecordDatabase provideMilkRecordDatabase(Application application) {
        return Room.databaseBuilder(application, MilkRecordDatabase.class, "timeAndMilk_db")
                .fallbackToDestructiveMigration()
                .build();
    }
}

