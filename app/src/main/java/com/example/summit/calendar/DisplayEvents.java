package com.example.summit.calendar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DisplayEvents extends AppCompatActivity {

    DatabaseEvent eventDB;
    EditText title, date, time, location, description, email;
    Button buttonShare;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_events);
        eventDB = new DatabaseEvent(this);

        title = (EditText)findViewById(R.id.editText_display_title);
        date = (EditText)findViewById(R.id.editText_display_date);
        time = (EditText)findViewById(R.id.editText_display_time);
        location = (EditText)findViewById(R.id.editText_display_location);
        //duration = (EditText)findViewById(R.id.editText_display_duration);
        description = (EditText)findViewById(R.id.editText_display_description);
        email = (EditText)findViewById(R.id.editText_dialog_email);
        buttonShare = (Button)findViewById(R.id.button_share);
        DisplayData();

    }

    private void DisplayData() {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = extras.getString("id");
        }

        Cursor cursor = eventDB.getRow(Integer.parseInt(id));

        if(cursor.moveToFirst()){
            do {
                title.setText(cursor.getString(1));
                date.setText(cursor.getString(3)+"/"+cursor.getString(2)+"/"+cursor.getString(4));
                time.setText(cursor.getString(5)+":"+cursor.getString(6));
                location.setText(cursor.getString(9));
                description.setText(cursor.getString(10));
            }while (cursor.moveToNext());
        }

    }


    private void share(){

        String eventDetail = "Event :"+ title.getText().toString()+"\n"+
                             "Date  :"+date.getText().toString()+"\n"+
                             "Time  :"+time.getText().toString()+"\n"+
                             "Location:"+location.getText().toString()+"\n"+

                             "Description:"+description.getText().toString()+"\n";

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "E-mail");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Share Event");
        emailIntent.putExtra(Intent.EXTRA_TEXT, eventDetail);
        emailIntent.setType("message/rfc822");

        try{
            startActivity(Intent.createChooser(emailIntent, "Send Email"));
        }
        catch(android.content.ActivityNotFoundException e){
            Toast.makeText(DisplayEvents.this, "Email Error", Toast.LENGTH_SHORT).show();
        }

    }

    public void delete(){

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            id = extras.getString("id");
        }
        boolean isDeleted = eventDB.deleteRow(Integer.parseInt(id));
        if(isDeleted == true) {
            //startActivity(new Intent(this, Medication.class));
            Toast.makeText(DisplayEvents.this, "Event Deleted", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Events.class));
        }
        else
            Toast.makeText(DisplayEvents.this, "Event Could Not Be Deleted", Toast.LENGTH_SHORT).show();

    }

    public void edit() {
        String[] dateToPass = new String[3];
        int i =0;
        for (String rev :date.getText().toString().split("/"))
        {
            dateToPass[i] = rev;
        }

        Bundle extras = getIntent().getExtras();
        id = extras.getString("id");
        Intent edit = new Intent(getApplicationContext(), AddEvent.class);
        edit.putExtra("idToEdit",id);
        edit.putExtra("passTitle", title.getText().toString());
        edit.putExtra("passLocation",location.getText().toString());
        //edit.putExtra("passDuration",duration.getText().toString());
        edit.putExtra("passDescription",description.getText().toString());
        startActivity(edit);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_events, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_share:
                share();
                break;
            case R.id.action_edit:
                edit();
                break;
            case R.id.action_delete:
                delete();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
