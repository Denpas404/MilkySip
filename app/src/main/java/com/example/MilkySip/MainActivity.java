package com.example.MilkySip;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

//import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.MilkySip.Models.MilkRecord;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab_addLog;
    MilkRecord milkRecord;
    String time;
    String date;

    String timeStamp;

    String amountOfMilk_String;
    double amountOfMilk_Double;

    List<MilkRecord> milkRecords_List;

    MilkRecordDatabase timeAndMilk_db;

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


        TimePicker timePicker = findViewById(R.id.timePicker);
        DatePicker datePicker = findViewById(R.id.datePicker);

        timePicker.setIs24HourView(true);

        EditText milkAmountInput = findViewById(R.id.milkAmountInput);

        fab_addLog = findViewById(R.id.addLogButton);

        TextView record_id = findViewById(R.id.record_id);

        RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };

        timeAndMilk_db = Room.databaseBuilder(getApplicationContext(), MilkRecordDatabase.class , "timeAndMilk_db").addCallback(myCallBack).build();

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

            // Insert the MilkRecord into the database
            try {
                timeAndMilk_db.milkRecordDAO().add_milkRecord(milkRecord);
            }
            catch (Exception e) {
                e.printStackTrace();
                String test = e.getMessage();
            }

            milkRecords_List = getAllMilkRecords();

            record_id.setText(String.valueOf(milkRecords_List.size()));
        });



    }

    private List<MilkRecord> getAllMilkRecords() {
        return timeAndMilk_db.milkRecordDAO().getAll();
    }


}