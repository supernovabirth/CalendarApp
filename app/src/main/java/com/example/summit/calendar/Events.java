package com.example.summit.calendar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Events extends ActionBarActivity {

    DatabaseEvent Eventdb;
    String findByThisString, returnId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        boolean check=checkDataBase();
        populateListViewFromDB();
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

    public void populateListViewFromDB() {

        final ArrayList<String>data = new ArrayList<String>();
        final ArrayList<String>idInfo = new ArrayList<String>();
        //Declare an arrayList to Dynamically store the values in the cursor

        Cursor cursor = Eventdb.getAllData();

        if (cursor.moveToFirst())//Check if the cursor has any elements
        {
            do{
                data.add(cursor.getString(1)); //Add the title of the event to the arraylist
                idInfo.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        cursor.close();

        ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        ListView theListView = (ListView) findViewById(R.id.listViewFromDB);

        theListView.setAdapter(theAdapter);
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ////
                String itemSelected = "You Selected " + String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(Events.this, itemSelected, Toast.LENGTH_SHORT).show();


                Intent next = new Intent(getApplicationContext(),DisplayEvents.class);
                findByThisString = String.valueOf(parent.getItemAtPosition(position));
                int i;

                for (i=0;i<data.size();i++)
                {
                    if (findByThisString.equals(data.get(i)))
                    {
                        returnId = idInfo.get(i);
                    }

                }
                next.putExtra("id", returnId);
                //next.putExtra("id",String.valueOf(parent.getItemAtPosition(position)));
                startActivity(next);

            }
        });
    }

}
