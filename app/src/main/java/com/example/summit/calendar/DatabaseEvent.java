package com.example.summit.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Summit on 4/1/2016.
 */
public class DatabaseEvent extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Event.db";
    public static final String TABLE_NAME ="Event";

    public static final String KEY_ROWID = "ID";
    public static final String KEY_TITLE = "Title";
    public static final String KEY_DAY= "Day";
    public static final String KEY_MONTH ="Month";
    public static final String KEY_YEAR = "Year";
    public static final String KEY_HOUR = "Hour";
    public static final String KEY_MINUTE = "Minute";
    public static final String KEY_LOCATION = "Location";
    public static final String KEY_DURATION = "Duration";
    public static final String KEY_DESCRIPTION = "Description";

    public DatabaseEvent(Context context){

        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(" CREATE TABLE " + TABLE_NAME + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, DAY TEXT, MONTH TEXT, YEAR TEXT, HOUR TEXT, MINUTE TEXT, LOCATION TEXT, DURATION TEXT, DESCRIPTION TEXT );");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData( String title, int day, int month, int year, int hour, int minute, String location, String duration, String description)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_TITLE, title);
        contentValues.put(KEY_DAY,day);
        contentValues.put(KEY_MONTH,month);
        contentValues.put(KEY_YEAR,year);
        contentValues.put(KEY_HOUR,hour);
        contentValues.put(KEY_MINUTE,minute);
        contentValues.put(KEY_LOCATION,location);
        contentValues.put(KEY_DURATION,duration);
        contentValues.put(KEY_DESCRIPTION,description);
             //contentValues.put("Reminder",reminder);

        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result ==-1)
            return false;
        else
            return true;
    }

    public boolean updateData( int id, String title, int day, int month, int year, int hour, int minute, String location, String duration, String description)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_TITLE, title);
        contentValues.put(KEY_DAY,day);
        contentValues.put(KEY_MONTH,month);
        contentValues.put(KEY_YEAR,year);
        contentValues.put(KEY_HOUR,hour);
        contentValues.put(KEY_MINUTE,minute);
        contentValues.put(KEY_LOCATION,location);
        contentValues.put(KEY_DURATION,duration);
        contentValues.put(KEY_DESCRIPTION,description);
        long result = db.update(TABLE_NAME,contentValues,"ID ="+id,null);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean deleteRow(long rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("Event", "id" + "=?", new String[]{String.valueOf(rowId)});
        if (result == 0) {
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public Cursor getRow(long rowId){

        SQLiteDatabase db = this.getReadableDatabase();
        //String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.rawQuery(("SELECT * FROM Event WHERE ID ="+rowId),null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


}
