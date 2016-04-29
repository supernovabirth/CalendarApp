package com.example.summit.calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by Summit on 4/26/2016.
 */

public class dateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    EditText txtdate;

    public dateDialog()
    {
        txtdate= (EditText)getView();
    }

    public dateDialog(View view){
        txtdate=(EditText)view;
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the dialog
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);

    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //show to the selected date in the text box
        String date=(month+1)+"-"+day+"-"+year;
        txtdate.setText(date);
    }
}
