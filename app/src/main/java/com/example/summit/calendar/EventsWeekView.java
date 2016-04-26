package com.example.summit.calendar;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Pradipta on 4/25/2016.
 */
public class EventsWeekView extends ActionBarActivity implements com.alamkanak.weekview.WeekView.EventClickListener, com.alamkanak.weekview.WeekView.EventLongPressListener, com.alamkanak.weekview.WeekView.MonthChangeListener{
    private WeekView mWeekView;
    DatabaseEvent Eventdb;
    String findByThisString, returnId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        boolean check=checkDataBase();
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase("Event.db", null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            Eventdb = new DatabaseEvent(this);
        }
        return checkDB != null;
    }
    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {

    }

    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        return null;
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

    }

}
