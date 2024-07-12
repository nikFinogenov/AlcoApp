package com.example.iamnotalcoholic;

import static android.content.Context.MODE_PRIVATE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class AddPage extends AppCompatActivity {

    private TextView dateTextView;
    private Calendar calendar;
    private EditText type, amount, price, promile;
    private TextView err;
    private String date;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);

        dateTextView = findViewById(R.id.dateTextView);
        calendar = Calendar.getInstance();
        date = String.format("%04d-%02d-%02d",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
        String selectedDate = "  " + calendar.get(Calendar.DAY_OF_MONTH) + "." + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR);
        dateTextView.setText(selectedDate);

        type = findViewById(R.id.editType);
        amount = findViewById(R.id.editAmount);
        price = findViewById(R.id.editPrice);
        promile = findViewById(R.id.editPromile);
        err = findViewById(R.id.error);
    }
    public void showDatePickerDialog(View v) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    // Обновляем выбранную дату в TextView
                    date = String.format("%04d-%02d-%02d",
                            selectedYear,
                            selectedMonth,
                            selectedDayOfMonth);
                    String selectedDate = "  " + selectedDayOfMonth + "." + (selectedMonth + 1) + "." + selectedYear;
                    dateTextView.setText(selectedDate);
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
    public void onCheckboxClicked(View view) {
        checkBox = findViewById(R.id.enabled);
        if(checkBox.isChecked()) {
            dateTextView.setVisibility(View.INVISIBLE);
            date = String.format("%04d-%02d-%02d",
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH));
        }
        else {
            dateTextView.setVisibility(View.VISIBLE);
        }
    }
    public void onDoneClick(View view) {
        SQLiteDatabase db = openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        if(type.getText().toString().compareTo("") == 0 || amount.getText().toString().compareTo("") == 0) {
            err.setVisibility(View.VISIBLE);
            db.close();
        }
        else {
            String typeText = type.getText().toString();
            String valueText = amount.getText().toString();
            String priceText = price.getText().toString();
            String promileText = promile.getText().toString();
            String sql = "INSERT INTO drinks (type, value, price, promile, date) VALUES ('" + typeText + "', '" + valueText + "', '" + priceText + "', '" + promileText + "', '" + date + "');";
            db.execSQL(sql);
            db.close();
            finish();
        }
    }
}