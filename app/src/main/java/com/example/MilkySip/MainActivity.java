package com.example.MilkySip;

import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

//import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

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

        //TextView textView_dateTime = findViewById(R.id.logListHeader);
        TimePicker picker=(TimePicker)findViewById(R.id.timePicker);
        DatePicker datePicker=(DatePicker)findViewById(R.id.datePicker);

        picker.setIs24HourView(true);

        //Get Date and time as day/month/year - HH:MM:SS
        //textView_dateTime.setText(getDateTime());
    }

    private String getDateTime() {
        //Get Date and time as day/month/year - HH:MM:SS
        return new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(Calendar.getInstance().getTime());
    }
}