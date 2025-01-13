package com.example.MilkySip;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

//import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.MilkySip.Models.MilkRecord;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    //region Variables
    FloatingActionButton fab_addLog;
    MilkRecord milkRecord;
    String time, date, timeStamp, amountOfMilk_String;
    double amountOfMilk_Double;
    List<MilkRecord> milkRecords_List;
    MilkRecordDatabase timeAndMilk_db;

    androidx.recyclerview.widget.RecyclerView recyclerView;
    private MilkRecordAdapter adapter;
    //endregion


    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find TimePicker og DatePicker i layoutet
        TimePicker timePicker = findViewById(R.id.timePicker);
        DatePicker datePicker = findViewById(R.id.datePicker);

        // Indstil TimePicker til at bruge 24-timers visning (i stedet for AM/PM)
        timePicker.setIs24HourView(true);

        // Find EditText-komponenten for mælkemængde-input og knyt den til variablen
        EditText milkAmountInput = findViewById(R.id.milkAmountInput);

        // Find FloatingActionButton for at tilføje en log og knyt den til variablen
        fab_addLog = findViewById(R.id.addLogButton);

        // Opret en RoomDatabase.Callback for at håndtere specifik databaseadfærd
        RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                // Denne metode kaldes første gang databasen oprettes
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
                // Denne metode kaldes hver gang databasen åbnes
            }
        };

        timeAndMilk_db = Room.databaseBuilder(getApplicationContext(), MilkRecordDatabase.class, "timeAndMilk_db").addCallback(myCallBack).build();

        fab_addLog.setOnClickListener(view -> {

            // Get the selected date and time from the date and time pickers
            date = String.format("%04d-%02d-%02d", datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
            time = String.format("%02d:%02d:%02d", timePicker.getHour(), timePicker.getMinute(), 0);
            timeStamp = date + " " + time;

            // Get value from EditText
            amountOfMilk_String = milkAmountInput.getText().toString();

            // Convert the string to a double
            amountOfMilk_Double = amountOfMilk_String.isEmpty() ? 0.0 : Double.parseDouble(amountOfMilk_String);

            // Create a new MilkRecord object
            milkRecord = new MilkRecord();
            milkRecord.setTimeStamp(timeStamp);
            milkRecord.setAmountOfMilk(amountOfMilk_Double);

            // Call the method to add the milk record in the background
            addMilkRecordInBackground(milkRecord);
        });

        getAllMilkRecordsInBackground();

    }

    private void getAllMilkRecordsInBackground() {

        recyclerView = findViewById(R.id.recyclerView);

        // Set LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(() -> {
            // Perform the database operation on a background thread
            try {
                String currentDate = getCurrentDate();
                milkRecords_List = timeAndMilk_db.milkRecordDAO().getAllMilkRecordsForToday(currentDate);

                // Sort the list from GET ALL by timestamp in ascending order
//                milkRecords_List = timeAndMilk_db.milkRecordDAO().getAllMilkRecordsSortedByOldest();
            } catch (Exception e) {
                e.printStackTrace();
                String test = e.getMessage();
            }

            // On finish, update the UI
            handler.post(() -> {
                // Update the UI on the main thread

                adapter = new MilkRecordAdapter(milkRecords_List);
                recyclerView.setAdapter(adapter);

            });
        });
    }

    private void addMilkRecordInBackground(MilkRecord milkRecord) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(() -> {
            // Perform the database operation on a background thread
            try {
                timeAndMilk_db.milkRecordDAO().add_milkRecord(milkRecord);
            } catch (Exception e) {
                e.printStackTrace();
                String test = e.getMessage();
            }

            // On finish, update the UI
            handler.post(() -> {
                // Update the UI on the main thread
                Toast.makeText(MainActivity.this, "Milk record added", Toast.LENGTH_SHORT).show();
            });
        });
    }

    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
}

