package com.example.MilkySip;

import android.view.View;
import android.widget.TextView;

import com.kizitonwose.calendar.view.ViewContainer;

public class DayViewContainer extends ViewContainer {
    TextView textView;

    public DayViewContainer(View view) {
        super(view);
        textView = view.findViewById(R.id.calendarDayText);
    }
}
