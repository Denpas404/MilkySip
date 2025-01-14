package com.example.MilkySip;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

public class MainActivity extends AppCompatActivity implements MilkRecordAdapter.OnItemClickListener {

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

    @Override
    public void onEditClick(int position) {
        showEditRecordDialog(position);
    }

    @Override
    public void onDeleteClick(int position) {
        showDeleteConfirmationDialog(position);
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

                adapter = new MilkRecordAdapter(milkRecords_List, this);
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

            milkRecords_List.add(milkRecord);


            // On finish, update the UI
            handler.post(() -> {
                // Update the UI on the main thread
                adapter.notifyItemInserted(milkRecords_List.size() - 1);
                recyclerView.smoothScrollToPosition(milkRecords_List.size() - 1);
                Toast.makeText(MainActivity.this, "Milk record added", Toast.LENGTH_SHORT).show();
            });
        });
    }

    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }


    private void showEditRecordDialog(int position) {
        // Inflater layoutet
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_edit_record, null);

        // Find View-komponenterne i dialogboksen
        TimePicker timePicker = view.findViewById(R.id.timePicker);
        DatePicker datePicker = view.findViewById(R.id.datePicker);
        EditText editAmountMilk = view.findViewById(R.id.editAmountMilk);

        // Indstil TimePicker til 24-timers visning
        timePicker.setIs24HourView(true);

        // Hent det eksisterende MilkRecord objekt
        MilkRecord record = milkRecords_List.get(position);

        // Parse dato og tid fra TimeStamp
        String[] dateTimeParts = record.getTimeStamp().split(" ");
        String[] dateParts = dateTimeParts[0].split("-");
        String[] timeParts = dateTimeParts[1].split(":");

        // Indstil DatePicker og TimePicker med eksisterende værdier
        datePicker.updateDate(
                Integer.parseInt(dateParts[0]),  // År
                Integer.parseInt(dateParts[1]) - 1,  // Måned (0-baseret)
                Integer.parseInt(dateParts[2])   // Dag
        );
        timePicker.setHour(Integer.parseInt(timeParts[0]));
        timePicker.setMinute(Integer.parseInt(timeParts[1]));

        // Sæt den eksisterende mælkemængde i EditText
        editAmountMilk.setText(String.valueOf(record.getAmountOfMilk()));

        // Opret dialogen
        new AlertDialog.Builder(this)
                .setTitle("Edit Record")
                .setView(view)
                .setPositiveButton("Save", (dialog, which) -> {
                    // Hent de nye værdier fra DatePicker og TimePicker
                    String date = String.format("%04d-%02d-%02d", datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
                    String time = String.format("%02d:%02d:%02d", timePicker.getHour(), timePicker.getMinute(), 0);
                    String newTimeStamp = date + " " + time;

                    // Hent den nye mælkemængde fra EditText
                    double newAmountMilk = Double.parseDouble(editAmountMilk.getText().toString());

                    // Opdater MilkRecord objektet
                    record.setTimeStamp(newTimeStamp);
                    record.setAmountOfMilk(newAmountMilk);

                    // Udfør databaseopdatering i en baggrundstråd
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        timeAndMilk_db.milkRecordDAO().update_milkRecord(record);

                        // Opdater UI på hovedtråden
                        runOnUiThread(() -> {
                            adapter.notifyItemChanged(position);
                            Toast.makeText(this, "Record updated", Toast.LENGTH_SHORT).show();
                        });
                    });
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showDeleteConfirmationDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Record")
                .setMessage("Are you sure you want to delete this record?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Udfør sletningen i en baggrundstråd
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        MilkRecord record = milkRecords_List.get(position);
                        timeAndMilk_db.milkRecordDAO().delete_milkRecord(record);

                        // Fjern posten fra listen på hovedtråden
                        runOnUiThread(() -> {
                            milkRecords_List.remove(position);
                            adapter.notifyItemRemoved(position);
                            Toast.makeText(this, "Record deleted", Toast.LENGTH_SHORT).show();
                        });
                    });
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }


}

