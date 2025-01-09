package com.example.MilkySip;

import android.app.Application;
import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MilkTrackerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialisering af globale ressourcer, DI osv.
    }
}

