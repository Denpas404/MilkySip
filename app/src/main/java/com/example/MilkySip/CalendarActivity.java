package com.example.MilkySip;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.MilkySip.Models.MilkRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CalendarActivity extends AppCompatActivity {

    @Inject MilkRecordDatabase milkRecordDatabase;

    //region Variables
    List<MilkRecord> allLogs = new ArrayList<>();
    List<String> logDates = new ArrayList<>();  // Store the dates of logs to mark on the calendar
    // Ui elements
    private CalendarView calendarView;
    private androidx.recyclerview.widget.RecyclerView recyclerView;
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarView);
        recyclerView = findViewById(R.id.logListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch all logs in the background
        getAllMilkRecordsInBackground();

        // Set up date selection listener
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
            // You can filter logs by the selected date if needed
            filterLogsByDate(selectedDate);
        });
    }

    // Fetch all milk records from the database in the background
    private void getAllMilkRecordsInBackground() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(() -> {
            try {
                // Fetch all milk records sorted by oldest first
                allLogs = milkRecordDatabase.milkRecordDAO().getAllMilkRecordsSortedByOldest();

                // Extract unique dates from logs to mark on the calendar
                for (MilkRecord record : allLogs) {
                    String date = record.getTimeStamp().substring(0, 10); // Get the date part (YYYY-MM-DD)
                    if (!logDates.contains(date)) {
                        logDates.add(date);  // Add date to the list if not already present
                    }
                }

                // Update UI on the main thread
                handler.post(() -> {
                    // Mark the dates on the calendar
                    markCalendarDates();
                    // You can also update the RecyclerView here if needed
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Mark the dates that have logs on the calendar
    private void markCalendarDates() {
        for (String date : logDates) {
            String[] parts = date.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]) - 1; // CalendarView uses 0-based months
            int day = Integer.parseInt(parts[2]);

            try {
                // Parse the date and set it on the calendar
                long dateInMillis = new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime();
                calendarView.setDate(dateInMillis, true, true);
            } catch (ParseException e) {
                e.printStackTrace();
                // You can log or handle the error in another way if necessary
            }
        }
    }

    // Filter logs by date (if needed)
    private void filterLogsByDate(String selectedDate) {
        List<MilkRecord> filteredLogs = new ArrayList<>();
        for (MilkRecord log : allLogs) {
            if (log.getTimeStamp().startsWith(selectedDate)) {
                filteredLogs.add(log);
            }
        }
        // Update the RecyclerView with the filtered logs
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(filteredLogs);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    // Get current date as a string (for filtering or default display)
    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
}
