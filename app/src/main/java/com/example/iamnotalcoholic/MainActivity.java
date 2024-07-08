package com.example.iamnotalcoholic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;

public class MainActivity extends AppCompatActivity {
    private TextView tvThisWeekOrMonth;
    private boolean isLongClick = false;

    private boolean isWeek(int day) {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        LocalDate givenDate = LocalDate.of(today.getYear(), today.getMonth(), day);
        return !givenDate.isAfter(endOfWeek) && !givenDate.isBefore(startOfWeek);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateMainInfo();
        updateSubMenu();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Make sure you use the correct layout file name here

        tvThisWeekOrMonth = findViewById(R.id.tvThisWeekOrMonth);
        tvThisWeekOrMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = tvThisWeekOrMonth.getText().toString();
                if (currentText.equals("This week")) {
                    tvThisWeekOrMonth.setText("This month");
                } else if (currentText.equals("This month")) {
                    tvThisWeekOrMonth.setText("This year");
                }
                else tvThisWeekOrMonth.setText("This week");

                updateSubMenu();
            }
        });

        Button btnAddNewEntry = findViewById(R.id.btnAddNewEntry);
        btnAddNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLongClick) {
                    addPageClick(v);
                }
                isLongClick = false;
            }
        });
        btnAddNewEntry.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isLongClick = true;
                addLongClick(v);
                return false;
            }
        });
        ImageButton btnWeeklyEntry = findViewById(R.id.btnEntryLeft);
        btnWeeklyEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeeklyReportClick(v);
                // Handle the new entry addition here
            }
        });
        ImageButton btnLastEntry = findViewById(R.id.btnEntryRight);
        btnLastEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LastPartyClick(v);
                // Handle the new entry addition here
            }
        });
    }

    private void updateMainInfo() {
        TextView lastTime = findViewById(R.id.tvLastTime);
        TextView totalAll = findViewById(R.id.tvTotal);
        TextView lastValue = findViewById(R.id.tvLastTimeValue);
        TextView totalValue = findViewById(R.id.totalValue);
        SQLiteDatabase db = openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT date FROM drinks ORDER BY date DESC LIMIT 1;", null);
        if(cursor.moveToNext()){
            String date = cursor.getString(0);
            cursor.close();
            lastTime.setText(String.format("Last time: %s", date));
        }
        else lastTime.setText("Last time: Unknown");
        cursor.close();
        cursor = db.rawQuery("SELECT COUNT(type) FROM drinks;", null);
        if(cursor.moveToNext()){
            String count = cursor.getString(0);
            totalAll.setText(String.format("Total: %s", count));
        }
        cursor.close();
        cursor = db.rawQuery("SELECT value FROM drinks where date = (SELECT date FROM drinks ORDER BY date DESC LIMIT 1) ORDER BY rowid DESC LIMIT 1;", null);
        if(cursor.moveToNext()){
            String value = cursor.getString(0);
            cursor.close();
            lastValue.setText(String.format("Last time value: %s(мл)", value));
        }
        else lastValue.setText("Last time value: 0");
        cursor.close();
        cursor = db.rawQuery("SELECT SUM(value) from drinks", null);
        if(cursor.moveToNext()){
            String totalV = cursor.getString(0);
            if(totalV != null) totalValue.setText(String.format("Total value: %.2f(л)", Double.parseDouble(totalV)/1000));
            else totalValue.setText("Total value: 0");
        }
        else totalValue.setText("Total value: 0");
        cursor.close();
        db.close();
    }

    private void updateSubMenu() {
        TextView totalF = findViewById(R.id.tvTotalF);
        TextView valueF = findViewById(R.id.tvValueF);
        TextView mostF = findViewById(R.id.tvMostF);
        TextView mostQF = findViewById(R.id.tvMostQuan);
        tvThisWeekOrMonth = findViewById(R.id.tvThisWeekOrMonth);
        SQLiteDatabase db = openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        String currentText = tvThisWeekOrMonth.getText().toString();
        int count = 0;
        int value = 0;
        String most = "Unknown";
        int mostQ = 0;
        if (currentText.equals("This week")) {
            Cursor cursor = db.rawQuery("SELECT date, value FROM drinks ORDER BY date ASC;", null);
            while (cursor.moveToNext()){
                if(isWeek(Integer.parseInt(cursor.getString(0).split("\\.")[0]))) {
                    count++;
                    value += cursor.getInt(1);
                }
            }
            cursor.close();
            cursor = db.rawQuery("Select count(*) as num, date from drinks GROUP by date order by num Desc;", null);
            while(cursor.moveToNext()) {
                if(isWeek(Integer.parseInt(cursor.getString(1).split("\\.")[0]))) {
                    mostQ = cursor.getInt(0);
                    most = cursor.getString(1);
                    break;
                }
            }
            cursor.close();
        }
        else if (currentText.equals("This month")) {
            Cursor cursor = db.rawQuery("SELECT date, value FROM drinks ORDER BY date;", null);
            while (cursor.moveToNext()) {
                int month = Integer.parseInt(cursor.getString(0).split("\\.")[1]);
                if(month == LocalDate.now().getMonthValue()) {
                    count++;
                    value += cursor.getInt(1);

                }
            }
            cursor.close();
            cursor = db.rawQuery("Select count(*) as num, date from drinks GROUP by date order by num Desc;", null);
            while(cursor.moveToNext()) {
                if(Integer.parseInt(cursor.getString(1).split("\\.")[1]) == LocalDate.now().getMonthValue()
                        && Integer.parseInt(cursor.getString(1).split("\\.")[2]) == Year.now().getValue()) {
                    mostQ = cursor.getInt(0);
                    most = cursor.getString(1);
                    break;
                }
            }
            cursor.close();
        }
        else {
            String sql = "SELECT count(date), value from drinks where date like '%" + Year.now().getValue() + "%'";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()){
                count = cursor.getInt(0);
            }
            cursor.close();
            sql = "SELECT value from drinks where date like '%" + Year.now().getValue() + "%'";
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                value += cursor.getInt(0);
            }
            cursor.close();
            cursor = db.rawQuery("Select count(*) as num, date from drinks GROUP by date order by num Desc;", null);
            while(cursor.moveToNext()) {
                if(Integer.parseInt(cursor.getString(1).split("\\.")[2]) == Year.now().getValue()) {
                    mostQ = cursor.getInt(0);
                    most = cursor.getString(1);
                    break;
                }
            }
            cursor.close();
        }
        totalF.setText(String.format("Total: %d", count));
        valueF.setText(String.format("Value: %.2f(л)", Double.valueOf(value)/1000));
        mostF.setText(String.format("Most drinks on: %s", most));
        mostQF.setText(String.format("Drinks on that day: %d", mostQ));
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    private void showAboutDialog() {
        final boolean[] isD = {false};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.about_dialog, null);
        builder.setView(dialogView);

        ImageView developerImage = dialogView.findViewById(R.id.developerImage);
        TextView developerText = dialogView.findViewById(R.id.developerText);
        TextView insp = dialogView.findViewById(R.id.inspirationText);

        // Установка обработчика для кликов
        developerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                developerImage.setVisibility(View.GONE);
                developerText.setVisibility(View.VISIBLE);
            }
        });
        developerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                developerImage.setVisibility(View.VISIBLE);
                developerText.setVisibility(View.GONE);
            }
        });

        // Установка обработчика для долгих кликов
        developerImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(isD[0]) {
                    insp.setText("This app was developed by: ");
                    developerImage.setImageResource(R.drawable.nf);
                }
                else {
                    insp.setText("Under an inspiration of her >_< <3");
                    developerImage.setImageResource(R.drawable.daria);
                }
                isD[0] = !isD[0];
                return true;
            }
        });

        builder.setPositiveButton("OK", null);
        builder.create().show();
    }

    private void showDialog() {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "custom");
    }
    private void exportDataToCSV() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "WeeklyReport.csv");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "text/csv");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

            Uri uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);

            if (uri != null) {
                try (OutputStream outputStream = resolver.openOutputStream(uri)) {
                    writeDataToCSV(outputStream);
                    Toast.makeText(this, "Data exported successfully to Downloads/WeeklyReport.csv", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(this, "Error exporting data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            // Handle versions older than 10 or request WRITE_EXTERNAL_STORAGE permission
        }
    }

    private void writeDataToCSV(OutputStream outputStream) throws IOException {
        String header = "Type,Volume,Strength,Price,Date\n";
        outputStream.write(header.getBytes());
        SQLiteDatabase db = openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM drinks ORDER BY date ASC;", null);

        while (cursor.moveToNext()) {
            String type = cursor.getString(0);
            int volume = cursor.getInt(1);
            int strength = cursor.getInt(2);
            double price = cursor.getDouble(3);
            String date = cursor.getString(4);
            String row = type + "," + volume + "," + strength + "," +
                    price + "," + date + "\n";
            outputStream.write(row.getBytes());
        }
        cursor.close();
        db.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.delete:
                showDialog();
                return true;
            case R.id.export:
                exportDataToCSV();
                return true;
//            case R.id.edit:
//                return true;
            case R.id.about:
                showAboutDialog();
                return true;
        }
        //headerView.setText(item.getTitle());
        return super.onOptionsItemSelected(item);
    }

    public void addPageClick(View v) {
        Intent intent = new Intent(this, AddPage.class);
        startActivity(intent);
        updateMainInfo();
    }
    public void addLongClick(View v) {
        SQLiteDatabase db = openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS drinks (type TEXT, value INTEGER, price INTEGER, promile INTEGER, date TEXT)");
        String sql = "INSERT INTO drinks (type, value, price, promile, date) SELECT type, value, price, promile, date FROM drinks ORDER BY rowid DESC LIMIT 1;";
        db.execSQL(sql);
        db.close();
        updateMainInfo();
    }
    public void WeeklyReportClick(View v) {
        Intent intent = new Intent(this, WeeklyReport.class);
        startActivity(intent);
        overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
    }
    public void LastPartyClick(View v) {
        Intent intent = new Intent(this, LastParty.class);
        startActivity(intent);
        overridePendingTransition(R.anim.animation_enter_reverse, R.anim.animation_leave_reverse);
    }
}
