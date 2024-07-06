package com.example.iamnotalcoholic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Calendar;

public class AddPage extends AppCompatActivity {

    private TextView dateTextView;
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);

        dateTextView = findViewById(R.id.dateTextView);
        calendar = Calendar.getInstance();
        String selectedDate = "  " + calendar.get(Calendar.DAY_OF_MONTH) + "." + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR);
        dateTextView.setText(selectedDate);
    }
    public void showDatePickerDialog(View v) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    // Обновляем выбранную дату в TextView
                    String selectedDate = "  " + selectedDayOfMonth + "." + (selectedMonth + 1) + "." + selectedYear;
                    dateTextView.setText(selectedDate);
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
    public void onCheckboxClicked(View view) {
        CheckBox checkBox = (CheckBox) view;
        if(checkBox.isChecked()) {
            dateTextView.setVisibility(View.INVISIBLE);
        }
        else {
            dateTextView.setVisibility(View.VISIBLE);
        }
    }
}