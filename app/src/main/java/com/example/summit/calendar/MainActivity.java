package com.example.summit.calendar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    DatabaseEvent eventDB;
    CalendarView myCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean check = checkDataBase();

        myCalendarView = (CalendarView)findViewById(R.id.calendarView);
        myCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year,int month, int dayOfMonth) {

                Toast.makeText(getApplicationContext(),"Selected Date:"+month+"/"+dayOfMonth+"/"+year,Toast.LENGTH_LONG).show();
                moveToAdd(dayOfMonth);
            }
        });

    }


    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase("Event.db", null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();

        }
        catch (SQLiteException e) {
            eventDB = new DatabaseEvent(this);
        }
        return checkDB != null;
    }

    public void moveToAdd (int dayOfMonth)
    {
       startActivity(new Intent(this, AddEvent.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_add:
                startActivity(new Intent(this, AddEvent.class));
                break;
            case R.id.action_Event:
                startActivity(new Intent(this, Events.class));
                break;

        }

        return super.onOptionsItemSelected(item);

    }

}
