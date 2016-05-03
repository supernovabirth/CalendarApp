package com.example.summit.calendar;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Search extends AppCompatActivity {

    DatabaseEvent db;
    EditText searchEntry;
    Button btnSearch;
    EditText dateEntry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        db = new DatabaseEvent(this);
        searchEntry = (EditText)findViewById(R.id.editText_searchItem);
        btnSearch = (Button)findViewById(R.id.button_search);
        dateEntry = (EditText)findViewById(R.id.editText_dateSearch);

        EditText txtDate=(EditText)findViewById(R.id.editText_dateSearch);
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View view, boolean hasfocus){
                if(hasfocus){
                    dateDialog dialog= new dateDialog(view);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
            }
        });
        Search();
    }

    public void Search(){
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String query = searchEntry.getText().toString();
                String dateQuery = dateEntry.getText().toString();

                if (!dateQuery.isEmpty())
                {
                    Cursor res = db.getInfoFromDate(dateQuery);
                    if (res != null && res.getCount() != 0) {
                        buildBufferEvent(res);
                        return;
                    } else {
                        showMessage("Search Results", "Your Search With Date ' " + dateEntry.getText().toString() + " ' did not match any information.");
                        return;
                    }
                }

                else {
                    Cursor results = db.getInfo(query);
                    if (results != null && results.getCount() != 0) {
                        buildBufferEvent(results);
                        return;
                    } else {
                        showMessage("Search Results", "Your Search ' " + searchEntry.getText().toString() + " ' did not match any information.");
                        return;
                    }
                }
            }
        });
    }

    public void buildBufferEvent(Cursor results)
    {
        StringBuffer buffer = new StringBuffer();
        while(results.moveToNext()) {
            buffer.append("Title: " + results.getString(1) + "\n");
            buffer.append("Date : " + results.getString(3) + "/" + results.getString(2) + "/" + results.getString(4) + "\n");
            buffer.append("Time : " + results.getString(5) + ":" + results.getString(6) + "\n");
            buffer.append("Location:" + results.getString(7) + "\n");
            buffer.append("Duration:" + results.getString(8) + "\n");
            buffer.append("Description:" + results.getString(9) + "\n"+"\n");
        }

        showMessage("Search Results ", buffer.toString());

    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
