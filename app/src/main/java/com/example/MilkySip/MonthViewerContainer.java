package com.example.MilkySip;

import android.view.View;
import android.widget.LinearLayout;

import com.kizitonwose.calendar.view.ViewContainer;

public class MonthViewerContainer extends ViewContainer {
    public LinearLayout titlesContainer;

    public MonthViewerContainer(View view) {
        super(view);
        titlesContainer = view.findViewById(R.id.weekday_titles_container); // ID fra dit layout
    }
}
