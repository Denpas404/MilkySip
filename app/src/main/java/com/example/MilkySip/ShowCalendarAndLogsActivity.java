package com.example.MilkySip;



import static com.kizitonwose.calendar.core.ExtensionsKt.daysOfWeek;
import static com.kizitonwose.calendar.core.ExtensionsKt.firstDayOfWeekFromLocale;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.core.CalendarMonth;
import com.kizitonwose.calendar.view.CalendarView;
import com.kizitonwose.calendar.view.MonthDayBinder;
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder;

import java.time.DayOfWeek;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class ShowCalendarAndLogsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_calendar_and_logs);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        CalendarView calendarView = findViewById(R.id.calendarView);
        YearMonth currentMonth = YearMonth.now();
        YearMonth startMonth = currentMonth.minusMonths(100); // Juster efter behov
        YearMonth endMonth = currentMonth.plusMonths(100); // Juster efter behov

        calendarView.setup(startMonth, endMonth, firstDayOfWeekFromLocale());
        calendarView.scrollToMonth(currentMonth);


        calendarView.setDayBinder(new MonthDayBinder<DayViewContainer>() {
            @Override
            public DayViewContainer create(View view) {
                return new DayViewContainer(view);
            }

            @Override
            public void bind(DayViewContainer container, CalendarDay data) {
                container.textView.setText(String.valueOf(data.getDate().getDayOfMonth()));
            }
        });

        List<DayOfWeek> daysOfWeek = daysOfWeek();

        // Find og initialiser 'titlesContainer'
        ViewGroup titlesContainer = findViewById(R.id.titlesContainer);
        if (titlesContainer != null) {
            for (int i = 0; i < daysOfWeek.size(); i++) {
                TextView textView = (TextView) titlesContainer.getChildAt(i);
                if (textView != null) {
                    DayOfWeek dayOfWeek = daysOfWeek.get(i);
                    textView.setText(dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
                }
            }
        } else {
            Log.e("ShowCalendarAndLogsActivity", "titlesContainer is null!");
        }


        calendarView.setMonthHeaderBinder(new MonthHeaderFooterBinder<MonthViewerContainer>() {
            @Override
            public void bind(@NonNull MonthViewerContainer container, CalendarMonth calendarMonth) {

            }

            @Override
            public MonthViewerContainer create(View view) {
                return new MonthViewerContainer(view);
            }


        });



    }
}